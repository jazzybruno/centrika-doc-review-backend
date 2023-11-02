package rw.ac.rca.centrika.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse {
    public boolean success;
    public String message;

    public Object data;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}