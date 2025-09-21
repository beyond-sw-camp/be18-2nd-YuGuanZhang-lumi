package com.yuguanzhang.lumi.material.dto;

import com.yuguanzhang.lumi.material.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MaterialsResponseDto {

    private final Long materialId;

    private final String title;

    private final String content;

    private final Long channelUserId;

    public static MaterialsResponseDto fromEntity(Material material) {
        return MaterialsResponseDto.builder()
                                   .materialId(material.getMaterialId())
                                   .title(material.getTitle())
                                   .content(material.getContent())
                                   .channelUserId(material.getChannelUser()
                                                          .getChannelUserId())
                                   .build();
    }
}
