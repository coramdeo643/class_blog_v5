//package com.tenco.blog.board;
//
//import com.tenco.blog._core.errors.exception.Exception404;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Repository
//public class BoardRepository {
//    private final EntityManager em;
//    private static final Logger log = LoggerFactory.getLogger(BoardRepository.class);
////    log.info("Post update form request - boardId : {}", boardId);
//
//
//    @Transactional
//    public Board updateById(Long id, BoardRequest.UpdateDTO reqDTO) {
//        log.info("Start to update the post - ID : {}", id);
//        Board board = findById(id);
//        board.setTitle(reqDTO.getTitle());
//        board.setContent(reqDTO.getContent());
//        return board;
//    }
//
//    // 게시글 삭제
//    @Transactional
//    public void deleteById(Long id) {
//        log.info("Start to delete the post - ID : {}", id);
//        String jpql = "delete from Board b where b.id = :id ";
//        Query q = em.createQuery(jpql);
//        q.setParameter("id", id);
//        int deletedCount = q.executeUpdate(); // Insert, Update, Delete
//        if (deletedCount == 0) {
//            throw new Exception404("There is no post to delete");
//        }
//        log.info("Complete to delete the post - number of deleted rows : {}", deletedCount);
//    }
//
//    @Transactional
//    public void deleteByIdSafely(Long id) {
//        Board board = em.find(Board.class, id);
//        if (board == null) {
//            throw new Exception404("There is no post to delete");
//        }
//        em.remove(board);
//    }
//
//    @Transactional
//    public Board save(Board board) {
//        log.info("Start to save the post - title : {}, author : {}", board.getTitle(), board.getUser().getUsername());
//        em.persist(board);
//        return board;
//    }
//
//    public List<Board> findByAll() {
//        log.info("Start to find all the posts");
//        String jpql = "select b from Board b order by b.id desc ";
//        return em.createQuery(jpql, Board.class).getResultList();
//    }
//
//    public Board findById(Long id) {
//        log.info("Start to find the selected post - ID : {}", id);
//        return em.find(Board.class, id);
//    }
//}
