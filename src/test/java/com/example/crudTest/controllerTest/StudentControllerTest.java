package com.example.crudTest.controllerTest;

import com.example.crudTest.controller.StudentController;
import com.example.crudTest.models.Student;
import com.example.crudTest.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        when(studentService.createStudent(student)).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"surname\": \"Doe\", \"working\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("John")));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is("John")));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        Student updatedStudent = new Student();
        updatedStudent.setName("Jane");
        updatedStudent.setSurname("Smith");

        when(studentService.updateStudent(1L, updatedStudent)).thenReturn(updatedStudent);

        mockMvc.perform((RequestBuilder) put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf("{\"name\": \"Jane\", \"surname\": \"Smith\", \"working\": false}")))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Jane")));
    }

    @Test
    public void testUpdateWorkingStatus() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setWorking(false);

        when(studentService.updateWorkingStatus(1L, true)).thenReturn(student);

        mockMvc.perform(patch("/students/1/working")
                        .param("working", "true"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.working", is(true)));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform((RequestBuilder) delete("/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ContentRequestMatchers delete(String path) {
        return null;
    }
}