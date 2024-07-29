package org.hyeon.member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyeon.member.constants.Authority;

@Data
@Entity
@Builder
@IdClass(AuthoritiesId.class)
@NoArgsConstructor @AllArgsConstructor
public class Authorities {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Authority authority;
}
