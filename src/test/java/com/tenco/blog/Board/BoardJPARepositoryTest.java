package com.tenco.blog.Board;

import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardJPARepository;
import com.tenco.blog.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class BoardJPARepositoryTest {

    @Autowired
    private BoardJPARepository br;

    @Autowired
    private TestEntityManager em;


    @Test
    public void save_test() {
        // given
        User testUser = User.builder()
                .username("testUser")
                .password("1234")
                .email("a@naver.com")
                .build();

        em.persistAndFlush(testUser); // 즉시 사용자 저장

        Board board = Board.builder()
                .title("testTitle")
                .content("testContent")
                .user(testUser)
                .build();


        // 게시글 저장 테스트
        br.save(board);

        // then


    }
}