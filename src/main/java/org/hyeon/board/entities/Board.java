package org.hyeon.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyeon.global.entities.BaseMemberEntity;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Board extends BaseMemberEntity { // 게시판 설정

    @Id
    @Column(length = 30)
    private String bId;

    @Column(length = 60, nullable = false)
    private String bName;

}
