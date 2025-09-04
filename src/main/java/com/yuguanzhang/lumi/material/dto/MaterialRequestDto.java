package com.yuguanzhang.lumi.material.dto;

import com.yuguanzhang.lumi.material.entity.Material;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class MaterialRequestDto {

    private String title;

    private String content;

    //파일도 있어야 함 생성, 수정 할 때 파일도 같이 생성, 바뀌어야 해서

    public Material toMaterial() {
        return Material.builder().title(this.title).content(this.content).build();
    }
}
