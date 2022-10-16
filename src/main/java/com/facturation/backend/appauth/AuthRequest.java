package com.facturation.backend.appauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    public AuthRequest(){
        
    }

    private String username;
    private String password;
}
