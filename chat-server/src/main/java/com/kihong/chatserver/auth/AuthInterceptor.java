package com.kihong.chatserver.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kihong.chatserver.config.ContextKeys;
import io.grpc.*;

public class AuthInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> AUTHORIZATION_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall, Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {

        String token = metadata.get(AUTHORIZATION_KEY);
        if (token == null) {
            serverCall.close(Status.UNAUTHENTICATED.withDescription("토큰을 찾을 수 없습니다"), metadata);
            return new ServerCall.Listener<>() {};
        }

        DecodedJWT decodedJWT = JwtUtil.verifyToken(token.replaceFirst("(?i)^bearer\\s+", ""));
        Context context = Context.current()
                .withValue(ContextKeys.ID_KEY, decodedJWT.getSubject())
                .withValue(ContextKeys.USERNAME_KEY, decodedJWT.getClaim("username").asString());

        return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
    }
}
