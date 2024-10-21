package com.kihong.chatserver.grpc;

import com.kihong.chat.ChatServiceGrpc;
import com.kihong.chatserver.config.ContextKeys;
import com.kihong.chatserver.document.ChatLog;
import com.kihong.chatserver.repository.ChatLogRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.kihong.chat.ChatProto.ChatMessageRequest;
import static com.kihong.chat.ChatProto.ChatMessageRequest.MessageType.*;
import static com.kihong.chat.ChatProto.ChatMessageResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardGrpcServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private final ChatLogRepository chatLogRepository;
    private final Map<String, StreamObserver<ChatMessageResponse>> clients = new ConcurrentHashMap<>();

    /**
     * 채팅 메세지를 처리하는 메서드입니다.
     * @param responseObserver 서버 > 클라이언트 방향의 응답 스트림
     * @return 클라이언트 > 서버 방향의 요청 스트림
     */
    @Override
    public StreamObserver<ChatMessageRequest> sendMessage(StreamObserver<ChatMessageResponse> responseObserver) {
        String id = ContextKeys.ID_KEY.get();
        String username = ContextKeys.USERNAME_KEY.get();

        return new StreamObserver<>() {
            @Override
            public void onNext(ChatMessageRequest chatMessage) {
                if (chatMessage.getType() == EXIT) {
                    clients.remove(id);
                } else {
                    if (!clients.containsKey(id)) {
                        clients.put(id, responseObserver);
                    }
                }

                chatLogRepository.save(
                        ChatLog.builder()
                                .userId(id)
                                .username(username)
                                .message(chatMessage.getMessage())
                                .timeStamp(chatMessage.getTimestamp())
                                .build()
                );
                broadcast(chatMessage);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
                clients.remove(id);
            }

            @Override
            public void onCompleted() {
                clients.remove(id);
            }
        };
    }

    private void broadcast(ChatMessageRequest chatMessage) {
        String username = ContextKeys.USERNAME_KEY.get();
        for (var streams : clients.values()) {
            streams.onNext(
                    ChatMessageResponse.newBuilder()
                            .setUsername(username)
                            .setMessage(chatMessage.getMessage())
                            .setType(ChatMessageResponse.MessageType.forNumber(chatMessage.getTypeValue()))
                            .setTimestamp(chatMessage.getTimestamp())
                            .build()
            );
        }
    }

}
