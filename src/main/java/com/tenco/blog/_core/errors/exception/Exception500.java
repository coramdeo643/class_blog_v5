package com.tenco.blog._core.errors.exception;

// 500 Internal server error
public class Exception500 extends RuntimeException {

    public Exception500(String message) {
        super(message);
    }
    // DB 오류, 연결 실패, 파일 처리 중 오류
}
