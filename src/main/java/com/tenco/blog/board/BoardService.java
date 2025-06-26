package com.tenco.blog.board;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Board 관련 비즈니스 로직을 처리하는 Service 계층
 */
// IoC
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
// 모든 method를 읽기 전용 transaction 으로 실행(findAll, findById 성능 최적화 / 데이터 수정 방지)
// DB lock 최소화 > 동시성 성능 개선
public class BoardService {
    // No member variables, but final
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    private final BoardJPARepository br;

    /*
      게시글 저장
     */
    @Transactional // method level transaction / 데이터 수정이 필요하는 읽기 전용 설정을 해제하고 쓰기 전용으로 변환
    public Board save(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        // 1. log - 게시글 저장 요청 정보
        // 2. DTO 를 entity 로 변환(작성자 정보 포함하기 위해서)
        // 3. 데이터 베이스에 게시글 저장
        // 4. 저장 완료 로그 기록
        // 5. 저장된 Board 를 Controller 로 반환.
        //1
        log.info("Start the service to save the post - title : {}, author : {}",
                saveDTO.getTitle(), sessionUser.getUsername());
        //2
        Board board = saveDTO.toEntity(sessionUser);
        //3
        br.save(board);
        //4
        log.info("Save completed the post - ID {}, title {}", board.getId(), board.getTitle());
        //5
        return board;
    }

    public List<Board> findAll() {
        // 1. log
        // 2. DB 게시글 조회
        // 3. log 기록
        // 4. 조회된 게시글 목록 반환
        log.info("Start to find all");
        List<Board> boardList = br.findAllJoinUser();
        log.info("게시글 목록 조회 완료 - 총 {} 개)", boardList.size());
        return boardList;
    }

    public Board findById(Long id) {
        // 1 log
        // 2 DB 해당 board Id - where
        // 3 게시글 없다면 404 error
        // 4 조회 성공시 로그 기룩
        // 5 조회된 게시글 반환
        log.info("Start to find by id - ID : {}", id);
        Board board = br.findByIdJoinUser(id).orElseThrow(() -> {
            log.warn("There is no post - ID {}", id);
            return new Exception404("Not found the post");
        });
        log.info("Complete to find - title {}", board.getTitle());
        return board;
    }

    /*
     * 게시글 수정(권한 체크)
     */
    @Transactional
    public Board updateById(Long id, BoardRequest.UpdateDTO updateDTO,
                            User sessionUser) {
        // 1 log
        // 2 수정하려는 게시글 조회
        // 3 권한 체크
        // 4 권한이 없다면 403 forbidden 예외 발생
        // 5 Board entity 상태값 변경(dirty checking)
        // 6 log - 수정 완료
        // 7 수정된 게시글 반환
        log.info("Start the service to update the post - 게시글 ID {}", id);
        Board board = br.findById(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - ID {}", id);
            return new Exception404("Not found the post");
        });

        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("Not eligible to update the post");
        }
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());
        // TODO - board 엔티티 update() 만들어주기
        // dirty checking
        log.info("Completed to update the post - 게시글 ID {}, title {}", id, board.getTitle());
        return board;

    }

    /*
     * 게시글 삭제(권한 체크)
     */
    @Transactional
    public void deleteById(Long id, User sessionUser) {
        // 1 log
        // 2 삭제하려는 게시글 조회
        // 3 권한 체크
        // 4 권한이 없다면 403 forbidden 예외
        // 5 DB 게시글 삭제
        // 6 log - 삭제 완료
        log.info("Start the service to delete the post - 게시글 ID {}", id);
        Board board = br.findById(id).orElseThrow(() -> {
            log.warn("Not found the post - ID {}", id);
            return new Exception404("Not found the post");
        });
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("Not eligible to delete the post");
        }
        br.deleteById(id);
    }

}
