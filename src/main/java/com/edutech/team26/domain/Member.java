package com.edutech.team26.domain;

import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.dto.MemberDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(nullable = false, unique=true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column
    private String phone;

    @Column
    private boolean emailAuthYn;

    @Column
    private LocalDateTime emailAuthTime;

    @Column
    private String emailAuthKey;

    @Column
    private LocalDateTime resetPasswordLimitTime;

    @Column
    private String resetPasswordKey;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String addr;

    @Column
    private String addrDetail;

    @Column
    private LocalDateTime lastLoginAt;

    @Column
    private String userStatus;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String password){
        this.password = password;
    }

    public void changeResetPwKey(String resetPasswordKey){
        this.resetPasswordKey = resetPasswordKey;
    }

    public void changeEmailAuthYn(boolean emailAuthYn) {
        this.emailAuthYn = emailAuthYn;
    }

    public void changeEmailAuthKey(String emailAuthKey) {
        this.emailAuthKey = emailAuthKey;
    }

    public void changeEmailAuthTime(LocalDateTime emailAuthTime) {
        this.emailAuthTime = emailAuthTime;
    }

    public void changeModify(MemberDTO memberDTO) {
        this.phone = memberDTO.getPhone();
        this.addr = memberDTO.getAddr();
        this.addrDetail = memberDTO.getAddrDetail();
        this.zipcode = memberDTO.getZipcode();
    }

    public void changeUserStatus(String userStatus){
        this.userStatus = userStatus;
    }

    public void changeResetPw(String resetPasswordKey, LocalDateTime resetPasswordLimitTime) {
        this.resetPasswordKey = resetPasswordKey;
        this.resetPasswordLimitTime = resetPasswordLimitTime;
    }

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void updateRole(MemberRole memberRole){
        this.roleSet.clear();
        this.roleSet.add(memberRole);
    }

}