package com.kassymova.jwtsecurity.response;


import com.kassymova.jwtsecurity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
