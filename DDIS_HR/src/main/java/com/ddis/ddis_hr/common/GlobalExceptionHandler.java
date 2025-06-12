package com.ddis.ddis_hr.common;

import com.ddis.ddis_hr.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(CommonResponse.failure(e.getMessage()));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        return ResponseEntity.internalServerError().body(CommonResponse.failure("서버 오류가 발생했습니다."));
//    }
}