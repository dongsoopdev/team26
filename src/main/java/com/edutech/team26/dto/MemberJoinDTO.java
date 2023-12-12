package com.edutech.team26.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class MemberJoinDTO {

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    /*@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]$", message = "영문 대소문자와 특수문자를 합쳐서 입력해주세요.")*/
    private String password;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    /*@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]$", message = "영문 대소문자와 특수문자를 합쳐서 입력해주세요.")*/
    private String passwordConfirm = "";

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "010-XXXX-XXXX 형태로 입력해주세요.")
    private String phone;

    private boolean emailAuthYn;

    private LocalDateTime emailAuthTime;

    private String emailAuthKey;

    private String zipcode;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String addr;

    private String addrDetail;

}