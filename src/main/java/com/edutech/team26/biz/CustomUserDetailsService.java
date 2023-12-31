package com.edutech.team26.biz;

import com.edutech.team26.constant.MemberCode;
import com.edutech.team26.domain.Member;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.getWithRoles(email);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        if(!result.get().getUserStatus().equals(MemberCode.MEMBER_STATUS_ING)) {
            throw new UsernameNotFoundException("No Authentication");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMno(),
                        member.getEmail(),
                        member.getPassword(),
                        member.getUserName(),
                        member.isEmailAuthYn(),
                        member.getRoleSet()
                                .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                                .collect(Collectors.toList())
                );

        return memberSecurityDTO;
    }
}