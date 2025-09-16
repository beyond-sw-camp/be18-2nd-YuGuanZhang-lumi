package com.yuguanzhang.lumi.user.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SearchRequestDto {
    private final String userId;
}
// 클라이언트에서 오는 값은 항상 문자열이고 DTO에서 String으로 받으면 생성자에서 final로 안전하게 초기화 가능
// 그래서 String을 사용해야함
