package com.yuguanzhang.lumi.channel.repository;

import com.yuguanzhang.lumi.channel.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    boolean existsByInvitationCode(String invitationCode);

    Optional<Invitation> findByInvitationCode(String invitationCode);
}
