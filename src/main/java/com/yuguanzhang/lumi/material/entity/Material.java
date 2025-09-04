package com.yuguanzhang.lumi.material.entity;


import com.yuguanzhang.lumi.material.dto.MaterialRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Entity
@Table(name = "Materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) //자동 createAt 설정하려고 추가했음
public class Material {

    @Id //pk 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY 전략 → MariaDB AUTO_INCREMENT 사용
    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "title", nullable = false) //null 허용 안함
    private String title;

    @Column(name = "content", columnDefinition = "TEXT") //content를 TEXT타입으로
    private String content;

    @CreatedDate //생성될 때 자동으로 시간 입력
    @Column(name = "created_at", nullable = false, updatable = false) //null 허용 안함, 수정 불가
    private LocalDateTime createdAt;

    @LastModifiedDate //수정했을 때 자동으로 시간 업데이트
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "channel_user_id") //nullable = false
    //채널유저 엔티티가 아직 없어서 long으로 일단 만들었음
    // 어노테이션도 수정해야 함 ManyToOne, JoinColumn
    // 원래는 자료형은 ChannelUSerId
    private Long channelUserId;

    //파일 객체 필요함
    //아직 없어서 안만들었는데 객체 있어야 하고 서비스에서 주입도 받아야 함.
    //create, update에 필요함.

    //업데이트에서 안쓸거면 없어도 됨
    public void updateMaterial(MaterialRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }
}
