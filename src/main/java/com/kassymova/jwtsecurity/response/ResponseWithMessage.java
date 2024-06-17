package com.kassymova.jwtsecurity.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWithMessage {
    private String message;
    private boolean success;
    private String token;
}
