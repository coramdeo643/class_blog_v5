package com.tenco.blog._core.errors;

import com.tenco.blog._core.errors.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 모든 컨트롤러에서 발생하는 예외처리를 이 클래스에서 처리하겠다
// RuntimeException 발생하면 해당 파일로 예외처리가 집중됨
@ControllerAdvice // 에러 페이지로 연결처리
// @RestControllerAdvice // 예외를 데이터로 반환할때
public class MyExceptionHandler {

    // slf4j logger obj - 로깅 사용시 sout 대신 활용하는 것이 좋다
    private  static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request) {
        log.warn("=== 400 Bad request 에러 발생 === ");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-agent : {}", request.getHeader("User-Agent"));
        request.setAttribute("msg", e.getMessage());
        return "/err/400";
    }

//    @ExceptionHandler(Exception401.class)
//    public String ex401(Exception401 e, HttpServletRequest request) {
//        log.warn("=== 401 Unauthorized 에러 발생 === ");
//        log.warn("요청 URL : {}", request.getRequestURL());
//        log.warn("인증 오류 : {}", e.getMessage());
//        log.warn("User-agent : {}", request.getHeader("User-Agent"));
//        request.setAttribute("msg", e.getMessage());
//        return "/err/401";
//    }
    @ExceptionHandler(Exception401.class)
    @ResponseBody // not mustache file, but data return
    public ResponseEntity<String> ex401ByData(Exception401 e, HttpServletRequest request) {
        // location.href = '/login-form'
        // String script = "<script> alert('"+ e.getMessage() +"'); history.back(); </script>";
        String script = "<script> alert('"+ e.getMessage() +"'); location.href = '/login-form'; </script>";
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.TEXT_HTML)
                .body(script);
    }

    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e, HttpServletRequest request) {
        log.warn("=== 403 Forbidden 에러 발생 === ");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-agent : {}", request.getHeader("User-Agent"));
        request.setAttribute("msg", e.getMessage());
        return "/err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, HttpServletRequest request) {
        log.warn("=== 404 Not Found 에러 발생 === ");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-agent : {}", request.getHeader("User-Agent"));
        request.setAttribute("msg", e.getMessage());
        return "/err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request) {
        log.warn("=== 500 Internal server 에러 발생 === ");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-agent : {}", request.getHeader("User-Agent"));
        request.setAttribute("msg", e.getMessage());
        return "/err/500";
    }

    // 기타 모든 RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.warn("=== Unexpected Runtime 에러 발생 === ");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("인증 오류 : {}", e.getMessage());
        log.warn("User-agent : {}", request.getHeader("User-Agent"));
        request.setAttribute("msg", "System error! Ask to admin");
        return "/err/err";
    }

}
