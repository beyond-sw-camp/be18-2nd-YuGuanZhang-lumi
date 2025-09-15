package com.yuguanzhang.lumi.file.repository;

import com.yuguanzhang.lumi.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByFileId(Long fileId);

    List<File> findByDeletedTrue();
}
