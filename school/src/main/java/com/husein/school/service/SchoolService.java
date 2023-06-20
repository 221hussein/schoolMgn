package com.husein.school.service;

import com.husein.school.client.StudentClient;
import com.husein.school.model.School;
import com.husein.school.repository.SchoolRepository;
import com.husein.school.response.FullSchoolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    private final StudentClient studentClient;

    public void saveStudent(School student) {
        schoolRepository.save(student);
    }

    public List<School> findAllStudent() {
        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolWithStudents(Integer schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(
                School.builder()
                        .schoolName("NOT_FOUND")
                        .email("NOT_FOUND")
                        .build()
        );
        var students = studentClient.findAllStudentsBySchool(schoolId); // find all student from students micro-service
        return FullSchoolResponse.builder()
                .name(school.getSchoolName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
