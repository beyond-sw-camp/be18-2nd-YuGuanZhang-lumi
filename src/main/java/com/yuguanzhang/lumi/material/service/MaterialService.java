package com.yuguanzhang.lumi.material.service;

import com.yuguanzhang.lumi.material.entity.Material;
import com.yuguanzhang.lumi.material.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/*
1️⃣ 일반적인 관례

Service 레이어

createMaterial(), updateMaterial()처럼 무슨 엔티티인지 명시하는 경우가 많음

이유: Service에는 여러 엔티티 CRUD가 섞여 있을 수 있으므로 명확하게 구분

Controller 레이어

메서드명이 URL과 HTTP 메서드로 의미가 드러나므로

create(), update()처럼 간단하게 쓰는 경우도 많음

REST API 관점에서 요청 → URL과 HTTP 메서드만 보면 충분히 의미 전달 가능

 */


@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    // 수업 자료 생성
    // 메소드 명을 create로 쓸지 createMaterial로 쓸지 정해야 할 듯? 관례는 create 라고 합니다
    public Material create(Material material) {

        return materialRepository.save(material);
    }

    // 수업 자료 리스트 조회
    //채널이랑 채널유저 테이블이 구현이 안되어있어서 조인을 못 함 나중에 해아할 듯
    public Page<Material> getAll(int page, int numOfRows) {

        //페이지 번호, 페이지당 갯수, 정렬을 지정할 수 있음
        Pageable pageable = PageRequest.of(page, numOfRows, Sort.by("CreatedAt").descending());

        //        return materialRepository.findAll();
        return null;
    }

    // 수업 자료 상세 조회
    public Material getById(Long id) {

        return materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    // 수업 자료 수정
    // setUpdateAt 안해도 @LastModifiedDate 있으면 알아서 들어감
    public Material update(Material material) {
        Material existing = getById(material.getMaterialId());
        existing.setTitle(material.getTitle());
        existing.setContent(material.getContent());
        //        existing.setFile(material.getFile()); 파일 객체 생기면 있어야 되는 코드

        return materialRepository.save(existing);
    }

    // 수업 자료 삭제
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }

}
