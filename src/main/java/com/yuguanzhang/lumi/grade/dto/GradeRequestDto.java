package com.yuguanzhang.lumi.grade.dto;

import com.yuguanzhang.lumi.grade.Entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class GradeRequestDto {

    private String title;

    private String category;

    private int grades;
    
    private LocalDate date;

    public Grade toEntity() {

        return Grade.builder()
                    .title(this.title)
                    .category(this.category)
                    .grades(this.grades)
                    .date(this.date)
                    .build();
    }

}
