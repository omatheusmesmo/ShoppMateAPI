package com.omatheusmesmo.Lista.de.Compras.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseUtil {

    public static <T> ResponseEntity<T> ok(T body){
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> created(T body){
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public static ResponseEntity<Void> noContent(){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<Void> badRequest(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Void> notFound(){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Void> internalServerError(){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
