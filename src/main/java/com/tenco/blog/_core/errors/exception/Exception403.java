package com.tenco.blog._core.errors.exception;

// 403 Forbidden
public class Exception403 extends RuntimeException {

    public Exception403(String message) {
        super(message);
    }
    // 권한없음(본인이 작성한 게시글이 아님)
    // 관리자만 접근 가능한 페이지일때
}
