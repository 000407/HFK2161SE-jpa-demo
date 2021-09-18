package com.apiit.demo.jpa.controller.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisteredSubject {
    private long id;
    private String name;
}
