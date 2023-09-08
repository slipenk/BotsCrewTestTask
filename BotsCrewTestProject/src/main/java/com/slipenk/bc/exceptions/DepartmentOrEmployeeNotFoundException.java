package com.slipenk.bc.exceptions;

public class DepartmentOrEmployeeNotFoundException extends RuntimeException {

    public DepartmentOrEmployeeNotFoundException(String message) {
        super(message);
    }
}
