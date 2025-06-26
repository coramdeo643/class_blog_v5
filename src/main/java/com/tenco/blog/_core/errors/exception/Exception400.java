package com.tenco.blog._core.errors.exception;

/**
 * 400 Bad request 상황에서 사용할 Custom exception class
 * Runtime Exception extended
 */
public class Exception400 extends RuntimeException {

    // Error message 로 사용할 String 을 super class 에게 전달
    public Exception400(String message) {
        super(message);
    }

    // 예시 - throw new Exception400("잘못된 요청이야");
    // 필수입력항목이 누락, 올바르지 않은 Data type 입력시 400!
}
