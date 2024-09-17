package com.mas.school.jwtSwagger;

import lombok.Data;

@Data
public class PasswordRequest {
	private String password ;
    private  String newPassword ;
    private String confirmation ;
    
}
