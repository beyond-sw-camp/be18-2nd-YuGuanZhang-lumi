package com.yuguanzhang.lumi.file.repository;

import com.yuguanzhang.lumi.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByFileId(Long fileId);

    List<FileEntity> findByIsDeletedTrue();
}
