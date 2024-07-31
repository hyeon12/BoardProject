package org.hyeon.global.config;

import lombok.RequiredArgsConstructor;
import org.hyeon.member.MemberUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    //email - <String> / seq 로 하게 되면? <Long>

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String email = memberUtil.isLogin()? memberUtil.getMember().getEmail() : null;
        // 삼항 조건 - 로그인 상태일 때 이메일 가져오기 / 미로그인 시 null 대입

        return Optional.ofNullable(email); // null 대비
    }
}
