package com.yuguanzhang.lumi.file.dto;

import com.yuguanzhang.lumi.file.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FileUploadResponseDto {
    private final Long fileId;
    private final String fileName;


    public static FileUploadResponseDto fromEntity(FileEntity entity) {
        return FileUploadResponseDto.builder().fileId(entity.getFileId())
                .fileName(entity.getFileName()).build();
    }
}
