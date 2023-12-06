package com.edutech.team26.repository;

import com.edutech.team26.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.email = :email")
    Optional<Member> getWithRoles(String email);

    @EntityGraph(attributePaths = "roleSet")
    Member findByMno(Long mno);

    @Modifying
    @Transactional
    @Query("update Member m set m.password =:password where m.email = :email ")
    void updatePassword(@Param("password") String password, @Param("email") String email);

    @Query("select m from Member m where m.email = :email")
    Member findByUserId(@Param("email") String email);

    @Query("update Member m set m.emailAuthKey = :emailAuthKey, m.emailAuthYn = :emailAuthYn, m.emailAuthTime = CURRENT_TIMESTAMP() where m.mno = :mno")
    void updateEmailAuth(@Param("emailAuthKey") String emailAuthKey, @Param("emailAuthYn") boolean emailAuthYn);

    Optional<Member> findByEmailAuthKey(String emailAuthKey);

    Optional<Member> findByEmail(String email);
    
}