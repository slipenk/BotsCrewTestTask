package com.slipenk.bc.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomErrorResponse {

    private int status;
    private String message;
    private long timeStamp;
}
