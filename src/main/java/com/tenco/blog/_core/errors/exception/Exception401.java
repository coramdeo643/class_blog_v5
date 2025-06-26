package com.tenco.blog._core.errors.exception;

// 401 Unauthorized
public class Exception401 extends RuntimeException {

    public Exception401(String message) {
        super(message);
    }
    // 로그인이 필요한 상황, 세션이 만료되었을때 발생
}
