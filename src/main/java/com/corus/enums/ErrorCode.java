package com.corus.enums;

/**
 * Created by dmitrigu on 24/09/2019.
 */
public enum ErrorCode {
    Success(0),
    Error(1);
    ErrorCode(int cod){
        code = cod;
    }
    private int code;
}