package com.tenco.blog.board;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final BoardRepository boardRepository;

    @GetMapping("/board/{id}/update-form")
    public String updateForm(
            @PathVariable(name = "id") Long boardId,
            HttpServletRequest req, HttpSession hs) {
        log.info("Request to update the post form - boardId : {}", boardId);
        User sessionUser = (User) hs.getAttribute("sessionUser");
        Board b = boardRepository.findById(boardId);
        if (b == null) {
            throw new Exception404("There is no post to update");
        }
        if (!b.isOwner(sessionUser.getId())) {
            throw new Exception403("Not eligible to update the post");
        }
        req.setAttribute("board", b);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Long boardId,
                         BoardRequest.UpdateDTO reqDTO,
                         HttpSession hs) {
        log.info("Request to update the post form - boardId : {}, new title : {}", boardId, reqDTO.getTitle());
        User sessionUser = (User) hs.getAttribute("sessionUser");
        reqDTO.validate();
        Board board = boardRepository.findById(boardId);
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("Not eligible to update the post"); // define... method
        }
        boardRepository.updateById(boardId, reqDTO);
        return "redirect:/board/" + boardId; // http://localhost:8080/board/1
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, HttpSession hs) {
        User sessionUser = (User) hs.getAttribute("sessionUser"); // object 로 떨어짐 > user로 다운캐스팅
        log.info("Request to delete the post - ID : {}", id);
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new Exception404("There is no post to delete");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("Not eligible to delete");
        }
        boardRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/board/save-form")
    public String saveForm(HttpSession session) {
        log.info("Request the new post");
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO, HttpSession session) {
        log.info("Request the new post(POST) - title : {}", reqDTO.getTitle());
        User sessionUser = (User) session.getAttribute("sessionUser");
        reqDTO.validate();
        boardRepository.save(reqDTO.toEntity(sessionUser));
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        log.info("Request the main page");
        List<Board> boardList = boardRepository.findByAll();
        log.info("Total numbers of the posts : {}", boardList.size());
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id,
                         HttpServletRequest request) {
        log.info("Request the detail of the post - ID : {}", id);
        Board board = boardRepository.findById(id);
        log.info("Complete the detail of the post - title : {}, author : {}", board.getTitle(), board.getUser().getUsername());
        request.setAttribute("board", board);
        return "board/detail";
    }
}
