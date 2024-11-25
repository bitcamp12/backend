package com.example.demo.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdCheckDTO {

    private String id;
    private String email;
    private String code;
    private String name;
    
}
