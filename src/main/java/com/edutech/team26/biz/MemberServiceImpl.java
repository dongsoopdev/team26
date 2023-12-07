package com.edutech.team26.biz;

import com.edutech.team26.component.CaptchaSetting;
import com.edutech.team26.component.MailComponent;
import com.edutech.team26.constant.MemberCode;
import com.edutech.team26.constant.MemberRole;
import com.edutech.team26.domain.Member;
import com.edutech.team26.dto.MemberJoinDTO;
import com.edutech.team26.dto.MemberSecurityDTO;
import com.edutech.team26.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailComponent mailComponent;

    private final CaptchaSetting captchaSetting;

    @Override
    public boolean join(MemberJoinDTO memberJoinDTO) throws Exception {
        String email = memberJoinDTO.getEmail();

        Optional<Member> checkMember = memberRepository.findByEmail(email);
        if(checkMember.isPresent()){
            return false;
        }

        String uuid = UUID.randomUUID().toString();

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword()));

        // 사용자 등록시 아래 사용
        member.changeEmailAuthYn(false);
        member.changeEmailAuthKey(uuid);
        member.addRole(MemberRole.USER);
        member.changeUserStatus(MemberCode.MEMBER_STATUS_REQ);

        // 관리자 등록시 아래 사용
        /*member.changeEmailAuthYn(true);
        member.changeEmailAuthKey("");
        member.addRole(MemberRole.ADMIN);
        member.changeUserStatus(MemberCode.MEMBER_STATUS_ING);*/

        memberRepository.save(member);

        // 회원가입 후 키 인증 메일 보내기
        /*String subject = "[LMS] 회원이 되신 것을 환영합니다.";
        String text = "<h2>LMS 회원가입을 축하드립니다.</h2><br /><hr /><br />";
        text += "<p>" + memberJoinDTO.getUserName() + "님의 아래 링크를 클릭하셔서 가입을 완료 하세요.</p>";
        text += "<div><a target='_blank' href='http://localhost:8080/member/email-auth/" + uuid + "'>가입 완료</a></div>";
        mailComponent.sendMail(email, subject, text);*/

        return true;

    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) {
            return false;
        }

        member.changeUserStatus(MemberCode.MEMBER_STATUS_ING);
        member.changeEmailAuthYn(true);
        member.changeEmailAuthKey("");
        member.changeEmailAuthTime(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean verifyRecaptcha(String recaptcha) {

        final String SECRET_KEY = captchaSetting.getSecret(); // 비밀키 호출
        final String RE_URL = captchaSetting.getUrl(); // 인증할 URL

        try {
            URL obj = new URL(RE_URL);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            String postParams = "secret=" + SECRET_KEY + "&response=" + recaptcha;
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            return jsonObject.getBoolean("success"); //최종 Return 값 : true or false

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateLoginDate() throws Exception {
        MemberSecurityDTO member = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberRepository.updateLoginDate(member.getMno());
        return true;
    }

    /*@Override
    public MemberJoinDTO myinfo(String email) {
        Member member = memberRepository.findByEmail(email);
        MemberJoinDTO memberDto = modelMapper.map(member, MemberJoinDTO.class);
        return memberDto;
    }*/

}