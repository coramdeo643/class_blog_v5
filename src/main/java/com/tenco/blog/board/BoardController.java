package com.tenco.blog.board;

import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    /*
    게시글 수정 화면 요청
     */
    @GetMapping("/board/{id}/update-form")
    public String updateForm(
            @PathVariable(name = "id") Long boardId,
            HttpServletRequest req, HttpSession hs) {
        // 1 인증검사 > interceptor
        // 2 게시물 조회/예외/권한 > service
        boardService.findById(boardId);
        req.setAttribute("board", boardService.findById(boardId));
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Long boardId,
                         BoardRequest.UpdateDTO reqDTO,
                         HttpSession hs) {
        // 1 인증 > interceptor
        // 2 유효성 검사
        // 3 수정 요청 > service
        // 4 redirect
        reqDTO.validate();
        User sessionUser = (User) hs.getAttribute("sessionUser");
        boardService.updateById(boardId, reqDTO, sessionUser);
        return "redirect:/board/" + boardId; // http://localhost:8080/board/1
    }

    /*
    게시글 삭제 요청
     */
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, HttpSession hs) {
        // 1 인증검사 interceptor
        // 2 세션 로그인 한 사용자 정보 추출
        // 3 service 위임
        // 4 redirect
        User sessionUser = (User) hs.getAttribute("sessionUser"); // object 로 떨어짐 > user로 다운캐스팅
        boardService.deleteById(id, sessionUser);
        return "redirect:/";
    }

    /*
    게시글 작성 화면 요청
     */
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO, HttpSession session) {
        // 1
        // 2
        reqDTO.validate();
        // 3 service
        boardService.save(reqDTO, (User) session.getAttribute("sessionUser")); // method 화
        // 4
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        // 1x
        // 2x
        // service
        model.addAttribute("boardList", boardService.findAll());
        // 4
        return "index";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id,
                         Model model) {
        // 3 service
        model.addAttribute("board", boardService.findById(id));
        // 4
        return "board/detail";
    }
}
