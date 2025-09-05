package com.yuguanzhang.lumi.chat.repository;

import com.yuguanzhang.lumi.chat.entity.RoomUser;
import com.yuguanzhang.lumi.chat.entity.RoomUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, RoomUserId> {
    // 내가 참여한 방
    List<RoomUser> findByRoomUserId_User_UserId(Long userId);

    // 특정 Room에서의 상대방
    Optional<RoomUser> findByRoomUserId_Room_RoomIdAndRoomUserId_User_UserIdNot(Long roomId,
            Long userId);

    // 특정 Room에서의 나
    Optional<RoomUser> findByRoomUserId_Room_RoomIdAndRoomUserId_User_UserId(Long roomId,
            Long userId);
}
