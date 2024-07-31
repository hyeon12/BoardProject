package org.hyeon.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMemberEntity extends BaseEntity{
    // 가입 날짜, 수정 날짜는 이미 구현된 BaseEntity 상속받아 구현

    @CreatedBy
    @Column(length = 65, updatable = false)
    private String createdBy; // 생성한 사용자 기록

    @LastModifiedBy
    @Column(length = 65, insertable = false)
    private String modifiedBy; // 수정한 사용자 기록

    //예시) 작업했던 사용자에 대한 정보 - 상담 기록한 사람 / 수정한 사람
}
