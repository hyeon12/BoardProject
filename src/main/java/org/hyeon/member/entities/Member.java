package org.hyeon.member.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hyeon.global.entities.BaseEntity;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long seq; //회원 번호 - 기본키
    @Column(length = 65, unique = true, nullable = false)
    private String email;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String userName;

    @Column(length = 15, nullable = false)
    private String mobile;

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Authorities> authorities;
}
