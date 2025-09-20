package com.yuguanzhang.lumi.file.repository;

import com.yuguanzhang.lumi.file.entity.FileAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAssociationRepository extends JpaRepository<FileAssociation, Long> {
}
