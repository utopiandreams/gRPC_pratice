package com.kihong.chatserver.repository;

import com.kihong.chatserver.document.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLogRepository extends MongoRepository<ChatLog, String> {

    Page<ChatLog> findAll(Pageable pageable);

}
