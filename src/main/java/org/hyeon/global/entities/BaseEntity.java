package org.hyeon.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false) // 초기에 입력된 값 수정x
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false) // 초기에 입력x
    private LocalDateTime modifiedAt;

    @Column(insertable = false) // 초기에 입력x
    private LocalDateTime deletedAt; //삭제 시, 업데이트
}
