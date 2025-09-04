package com.yuguanzhang.lumi.common.model.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@ToString
public class ItemsResponseDto<T> extends BaseResponseDto<T> {

    //페이지네이션에 필요한 변수들
    private final int page;

    private final int numOfRows;

    private final int totalCount;

    public ItemsResponseDto(HttpStatus status, List<T> items, int page, int numOfRows,
            int totalCount) {
        super(status, items);
        this.page = page;
        this.numOfRows = numOfRows;
        this.totalCount = totalCount;
    }

    public static <T> ItemsResponseDto<T> of(HttpStatus status, List<T> items, int page,
            int numOfRows, int totalCount) {

        return new ItemsResponseDto<>(status, items, page, numOfRows, totalCount);
    }

}
