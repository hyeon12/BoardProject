package org.hyeon.member.repositories;

import org.hyeon.member.entities.Member;
import org.hyeon.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    //회원 조회 - 회원이 없으면 추가/ 있으면 수정 => Optional
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);
    //1)권한 설정 2)시큐리티 3)조회

    default boolean exists(String email){
        QMember member = QMember.member;

        return exists(member.email.eq(email));
    }
}
