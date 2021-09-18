package com.apiit.demo.jpa.controller;

import com.apiit.demo.jpa.controller.payload.request.NewCourse;
import com.apiit.demo.jpa.controller.payload.response.RegisteredCourse;
import com.apiit.demo.jpa.controller.payload.response.RegisteredSubject;
import com.apiit.demo.jpa.model.Course;
import com.apiit.demo.jpa.model.Subject;
import com.apiit.demo.jpa.repo.CourseRepository;
import com.apiit.demo.jpa.repo.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    @PostMapping("/new")
    public ResponseEntity<RegisteredCourse> registerNewCourse(@RequestBody NewCourse course) {
        final List<Subject> subjects = new ArrayList<>();

        for (String subjectName : course.getSubjectNames()) {
            subjects.add(new Subject(subjectName));
        }

        final Course courseEntity = courseRepository.save(
                Course.builder()
                        .name(course.getName())
                        .subjects(subjects)
                        .build());

        return ResponseEntity.ok(generateRegisterCourseSuccessResponse(courseEntity));
    }

    @PostMapping("/subjects/new")
    public ResponseEntity<Subject> addNewSubject(@RequestBody Subject subject) {
        return ResponseEntity.ok(subjectRepository.save(subject));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    private RegisteredCourse generateRegisterCourseSuccessResponse(Course entity) {
        final List<RegisteredSubject> subjectResponses = new ArrayList<>();

        for (Subject subject : entity.getSubjects()) {
            subjectResponses.add(
                    RegisteredSubject.builder()
                            .id(subject.getId())
                            .name(subject.getName())
                            .build()
            );
        }

        return RegisteredCourse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .subjects(subjectResponses)
                .build();
    }
}
