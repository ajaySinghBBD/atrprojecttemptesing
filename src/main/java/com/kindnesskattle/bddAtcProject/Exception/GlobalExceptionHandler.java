package com.kindnesskattle.bddAtcProject.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,String>> entitynotfound(EntityNotFoundException exception){
        Map<String,String> map = new HashMap<>();
        map.put("Error",exception.getMessage());
        map.put("StatusCode","404");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(404));

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> IllgalArgument(IllegalArgumentException exception){
        Map<String,String> map = new HashMap<>();
        map.put("Error",exception.getMessage());
        map.put("StatusCode","404");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(404));

    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> runtimeExpection(RuntimeException exception){
        Map<String,String> map = new HashMap<>();
        map.put("Error",exception.getMessage());
        map.put("StatusCode","404");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(404));

    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<Map<String,String>> entityNotfound(EntityNotFoundException exception){
//        Map<String,String> map = new HashMap<>();
//        map.put("Error",exception.getMessage());
//        map.put("StatusCode","404");
//        return new ResponseEntity<>(map, HttpStatusCode.valueOf(404));
//
//    }


}
