package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception401;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository ur;
    private final HttpSession hs;

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request, HttpSession hs) {
        log.info("Request to update the user info(FORM)");
        User sUser = (User) hs.getAttribute("sessionUser");
        request.setAttribute("user", sUser);
        return "user/update-form";
    }

    @PostMapping("/user/update")
    public String update(
            UserRequest.UpdateDTO reqDTO, HttpSession hs, HttpServletRequest request) {
        log.info("Request to update the user info");
        User sUser = (User) hs.getAttribute("sessionUser");
        reqDTO.validate();
        User updateUser = ur.updateById(sUser.getId(), reqDTO);
        hs.setAttribute("sessionUser", updateUser);
        return "redirect:/user/update-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        log.info("Request to sign up");
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO, HttpServletRequest request) {
        log.info("Request to sign up the new user - username : {}, email : {}",
                joinDTO.getUsername(), joinDTO.getEmail());
        joinDTO.validate(); // defensive code
        User existUser = ur.findByUsername(joinDTO.getUsername());
        if (existUser != null) {
            throw new Exception401("Username already exists;" + joinDTO.getUsername());
        }
        User user = joinDTO.toEntity();
        ur.save(user);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        log.info("Request to log in(FORM)");
        return "user/login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        log.info("= = Log in = =");
        log.info("USERNAME = {}", loginDTO.getUsername());
        loginDTO.validate();
        User user = ur.findByUsernameAndPassword(
                loginDTO.getUsername(), loginDTO.getPassword());
        if (user == null) {
            throw new Exception400("Invalid username or password");
        }
        hs.setAttribute("sessionUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        log.info("= = Log out = =");
        hs.invalidate();
        return "redirect:/";
    }
}
