package com.edutech.team26.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private Long mno;
    private String email;
    private String password;
    private String userName;
    private boolean emailAuthYn;

    public MemberSecurityDTO(Long mno, String email, String password, String userName, boolean emailAuthYn, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.mno =mno;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.emailAuthYn = emailAuthYn;
    }


    public Long getMno() {
        return this.mno;
    }

    public String getName() {
        return this.email;
    }

    public String getUserName() {
        return this.userName;
    }

}