package com.tenco.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 관련 DB 접근을 담당
 * 기본적인 CRUD를 제공한다
 *  > @Repository 생략 가능 > bc it is already declared in JPARepository
 */
public interface BoardJPARepository extends JpaRepository<Board, Long> {
    // 기본 CRUD 기능 외 추가적인 기능은 직접 선언해줘야한다

    // 게시글과 사용자 정보가 포함된 엔티티를 만들어 주어야 한다(List)
    // JOIN FETCH = 모든 Board entity 와 연관된 user 를 한방 쿼리로 가져온다
    // LAZY 전략이라서 N+1 방지 가능하다
    //  > 게시글 10개가 있다면 지연 로딩 1(Board 조회) + 10(User 조회) = 11번 쿼리가 발생
    @Query("select b from Board b join fetch b.user order by b.id desc ")
    List<Board> findAllJoinUser();

    // 게시글 ID 로 한방에 유저 정보도 가져오기 - JOIN FETCH
    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") Long id);
}
