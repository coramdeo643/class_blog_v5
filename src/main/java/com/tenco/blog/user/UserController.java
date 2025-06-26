package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService us;

    /*
    회원정보수정 화면요청
     */
    @GetMapping("/user/update-form")
    public String updateForm(Model mod, HttpSession hs) {
        // 1 // 2 service
        User sUser = (User) hs.getAttribute("sessionUser");
        User user = us.findById(sUser.getId());
        mod.addAttribute("user", user);
        return "user/update-form";
    }

    /*
    회원정보수정 기능요청
     */
    @PostMapping("/user/update")
    public String update(
            UserRequest.UpdateDTO reqDTO, HttpSession hs) {
        // 1 인증 intercept
        // 2 유효성
        reqDTO.validate();
        // 3 service
        User user = (User) hs.getAttribute("sessionUser");
        User updatedUser = us.updateById(user.getId(), reqDTO);
        // 4 session sync
        hs.setAttribute("sessionUser", updatedUser);
        // 5 redirect
        return "redirect:/user/update-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        log.info("Request to sign up");
        return "user/join-form";
    }

    /*
    회원가입 기능 요청
     */
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        joinDTO.validate();
        us.join(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    /*
    login 화면 요청
     */
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession s) {
        loginDTO.validate();
        User user = us.login(loginDTO);
        s.setAttribute(Define.SESSION_USER, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession s) {
        s.invalidate();
        return "redirect:/";
    }
}
