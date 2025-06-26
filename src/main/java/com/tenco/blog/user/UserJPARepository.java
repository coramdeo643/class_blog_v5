package com.tenco.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
    // 사용자명과 비밀번호로 사용자 조회(로그인용)
    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> findByUsernameAndPassword(@Param("username") String username,
                                             @Param("password") String password);

    // 사용자 이름으로 사용자 조회(중복체크용)
    // @Query("select * from user_tb where username = :username", nativeQuery = true)
    // JPQL 명시적으로 입력해서 사용함
    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

}
