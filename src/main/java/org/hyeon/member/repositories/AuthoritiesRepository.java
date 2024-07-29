package org.hyeon.member.repositories;

import org.hyeon.member.entities.Authorities;
import org.hyeon.member.entities.AuthoritiesId;
import org.hyeon.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesId>, QuerydslPredicateExecutor<Authorities> {
    List<Authorities> findByMember(Member member);

}
