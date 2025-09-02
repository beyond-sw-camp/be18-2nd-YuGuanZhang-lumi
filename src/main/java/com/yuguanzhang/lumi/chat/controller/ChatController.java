package com.yuguanzhang.lumi.chat.controller;

import com.yuguanzhang.lumi.chat.dto.ChatRoomsResponseDto;
import com.yuguanzhang.lumi.chat.service.ChatService;
import com.yuguanzhang.lumi.common.dto.ResponseDto;
import com.yuguanzhang.lumi.user.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("chatrooms")
    public ResponseEntity<ResponseDto<List<ChatRoomsResponseDto>>> getChatRooms(@AuthenticationPrincipal UserDetailsDto user) {
        List<ChatRoomsResponseDto> rooms = chatService.getChatRooms(user.getUser().getUserId());

        ResponseDto<List<ChatRoomsResponseDto>> response = new ResponseDto<>(HttpStatus.OK, rooms);

        return ResponseEntity.ok(response);
    }
}
