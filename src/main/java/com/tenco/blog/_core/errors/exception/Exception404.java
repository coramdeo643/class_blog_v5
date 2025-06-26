package com.tenco.blog._core.errors.exception;

public class Exception404 extends RuntimeException {

    public Exception404(String message) {
        super(message);
    }
    // 요청 리소스가 없을때
}
