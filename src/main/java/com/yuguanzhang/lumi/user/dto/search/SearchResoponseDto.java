package com.yuguanzhang.lumi.user.dto.search;

import com.yuguanzhang.lumi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SearchResoponseDto {
    private final UUID userId;
    private final String email;
    private final String name;

    public static SearchResoponseDto searchResoponseDto(User user) {
        return new SearchResoponseDto(user.getUserId(), user.getEmail(), user.getName());
    }
}
