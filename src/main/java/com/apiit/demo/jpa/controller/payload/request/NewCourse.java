package com.apiit.demo.jpa.controller.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class NewCourse {
    private String name;
    private List<String> subjectNames;
}
