package com.yuguanzhang.lumi.file.repository;

import com.yuguanzhang.lumi.file.entity.FileAssociation;
import com.yuguanzhang.lumi.file.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FileAssociationRepository extends JpaRepository<FileAssociation, Long> {

    List<FileAssociation> findByEntityIdAndEntityType(Long entityId, EntityType entityType);

    void deleteByEntityIdAndEntityType(Long materialId, EntityType entityType);
}
