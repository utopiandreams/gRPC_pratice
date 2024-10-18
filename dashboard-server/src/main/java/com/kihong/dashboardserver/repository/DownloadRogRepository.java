package com.kihong.dashboardserver.repository;

import com.kihong.dashboardserver.document.DownloadLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRogRepository extends MongoRepository<DownloadLog, String> {

    Page<DownloadLog> findAll(Pageable pageable);

}
