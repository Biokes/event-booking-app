package com.eventBooker.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse <T>{
    private T data;
    private boolean status;
    private int code;
}
