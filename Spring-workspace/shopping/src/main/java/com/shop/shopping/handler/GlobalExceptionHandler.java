package com.shop.shopping.handler;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // 예외 처리 로직을 작성합니다.
        // 여기에서는 예외 클래스의 이름을 반환하도록 예시를 작성하겠습니다.
        String errorMessage = e.getClass().getSimpleName();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        // BadRequestException이 발생한 경우에 대한 처리 로직을 작성합니다.
        // 여기에서는 예외 클래스의 이름을 반환하도록 예시를 작성하겠습니다.
        String errorMessage = e.getClass().getSimpleName();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    // 다른 예외에 대한 처리 로직을 추가할 수 있습니다.
}

