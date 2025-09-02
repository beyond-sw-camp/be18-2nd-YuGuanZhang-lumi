package com.yuguanzhang.lumi.chat.repository;

import com.yuguanzhang.lumi.chat.entity.RoomUser;
import com.yuguanzhang.lumi.chat.entity.RoomUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomUserRepository extends JpaRepository<RoomUser, RoomUserId> {

    // 내가 참여한 방
    List<RoomUser> findByRoomUserId_User_UserId(Long userId);

    // 특정 Room에서 나를 제와한 상대방 찾기
    List<RoomUser> findByRoomUserId_Room_RoomIdAndRoomUserId_User_UserIdNot(Long roomId, Long userId);


}
