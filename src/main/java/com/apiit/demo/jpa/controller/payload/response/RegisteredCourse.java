package com.apiit.demo.jpa.controller.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisteredCourse {
    private long id;
    private String name;
    private List<RegisteredSubject> subjects;
}
