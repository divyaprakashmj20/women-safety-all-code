package com.tus.sosmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityResponseDTO {

    public Response response;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        Long id;
        String name;
        String email;
        String phoneNumber;
        String password;
        String role;
    }

}
