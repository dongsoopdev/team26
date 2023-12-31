package com.edutech.team26.repository;

import com.edutech.team26.domain.Member;
import com.edutech.team26.dto.MemberDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.email = :email")
    Optional<Member> getWithRoles(@Param("email") String email);

    @EntityGraph(attributePaths = "roleSet")
    Member findByMno(@Param("mno") Long mno);

    @Query("select m from Member m where m.mno = :mno")
    Member getMemberByMno(@Param("mno") Long mno);

    @Modifying
    @Transactional
    @Query("update Member m set m.password =:password where m.email = :email ")
    void updatePassword(@Param("password") String password, @Param("email") String email);

    @Query("select m from Member m where m.email = :email")
    Member findByUserId(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM member m LEFT JOIN member_role_set mr ON m.mno = mr.member_mno where email NOT IN ('admin') ORDER BY mr.role_set desc, m.user_status asc")
    List<Member> getAllMember();

    @Modifying
    @Transactional
    @Query("update Member m set m.lastLoginAt = CURRENT_TIMESTAMP() where m.mno = :mno")
    void updateLoginDate(@Param("mno") Long mno) throws Exception;

    Optional<Member> findByEmailAuthKey(@Param("emailAuthKey") String emailAuthKey);

    Optional<Member> findByEmail(@Param("email") String email);

    Optional<Member> findByUserNameAndPhone(@Param("username") String username, @Param("phone") String phone);

    Optional<Member> findByEmailAndPhoneAndUserName(@Param("email") String email, @Param("phone") String phone, @Param("username") String username);

    Optional<Member> findByMnoAndResetPasswordKey(@Param("mno") Long mno, @Param("key") String resetPasswordKey);
    
}