package com.yuguanzhang.lumi.channel.entity;

import com.yuguanzhang.lumi.common.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) //자동 createAt 설정하려고 추가했음
public class Channel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY 전략 → MariaDB AUTO_INCREMENT 사용
    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "subject")
    private String subject;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelUser> channelUsers = new ArrayList<>();

    //    @CreatedDate //생성될 때 자동으로 시간 입력
    //    @Column(name = "created_at", nullable = false, updatable = false) //null 허용 안함, 수정 불가
    //    private LocalDateTime createdAt;
    //
    //    @LastModifiedDate //수정했을 때 자동으로 시간 업데이트
    //    @Column(name = "updated_at")
    //    private LocalDateTime updatedAt;


    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateSubject(String newSubject) {
        this.subject = newSubject;
    }
}
