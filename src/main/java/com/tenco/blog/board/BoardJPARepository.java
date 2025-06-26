package com.tenco.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시글 관련 DB 접근을 담당
 * 기본적인 CRUD를 제공한다
 *  > @Repository 생략 가능 > bc it is already declared in JPARepository
 */
public interface BoardJPARepository extends JpaRepository<Board, Long> {
    // 기본 CRUD 기능 외 추가적인 기능은 직접 선언해줘야한다

    // 게시글과 사용자 정보가 포함된 엔티티를 만들어 주어야 한다


}
