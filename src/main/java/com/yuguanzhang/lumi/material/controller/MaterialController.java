package com.yuguanzhang.lumi.material.controller;

import com.yuguanzhang.lumi.common.model.dto.BaseResponseDto;
import com.yuguanzhang.lumi.common.model.dto.ItemsResponseDto;
import com.yuguanzhang.lumi.material.dto.MaterialRequestDto;
import com.yuguanzhang.lumi.material.entity.Material;
import com.yuguanzhang.lumi.material.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/channels/{channel_id}")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    // 생성 API
    @PostMapping("/materials")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponseDto<Material> create(@PathVariable("channel_id") Long channelId,
            @RequestBody MaterialRequestDto requestDto) {

        //DTO -> Entity 변환
        Material material = requestDto.toMaterial();

        // PathVariable로 받은 channelUserId 세팅
        //        material.setChannelUserId(channelId);

        //서비스 호출 DB에 저장
        Material savedMaterial = materialService.create(material);

        //공용 dto로 감싸서 반환. static 메소드 사용
        return BaseResponseDto.of(HttpStatus.CREATED, savedMaterial);

    }

    // 리스트 조회 API
    //채널이랑 채널유저 테이블이 구현이 안되어있어서 조인을 못 함 나중에 해아할 듯
    @GetMapping("/materials/")
    @ResponseStatus(HttpStatus.OK)
    public ItemsResponseDto<Material> getAll(@RequestParam int page, @RequestParam int numOfRows) {

        //        int totalCount = materialService.getAll().size();

        return null;
    }

    // 상세 조회 API
    @GetMapping("/materials/{material_id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<Material> getById(@PathVariable("material_id") Long materialId) {

        Material material = materialService.getById(materialId);

        return BaseResponseDto.of(HttpStatus.OK, material);
    }

    // 수정 API
    @PutMapping("/materials/{material_id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<Material> update(@PathVariable("material_id") Long materialId,
            @RequestBody MaterialRequestDto requestDto) {
        Material material = requestDto.toMaterial();
        material.setMaterialId(materialId);
        Material updatedMaterial = materialService.update(material);

        return BaseResponseDto.of(HttpStatus.OK, updatedMaterial);
    }


    @DeleteMapping("/materials/{material_id}")
    //204 아니고 200으로 할거면 말해주세요
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("material_id") Long materialId) {
        materialService.delete(materialId);
    }
}
