package com.example.crudTest.serviceTest;

import com.example.crudTest.models.Student;
import com.example.crudTest.repositories.StudentRepository;
import com.example.crudTest.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        when(studentRepository.save(student)).thenReturn(student);

        Student created = studentService.createStudent(student);
        assertEquals(student.getName(), created.getName());
        assertEquals(student.getSurname(), created.getSurname());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> found = studentService.getStudentById(1L);
        assertTrue(found.isPresent());
        assertEquals(student.getId(), found.get().getId());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");

        Student updatedStudent = new Student();
        updatedStudent.setName("Jane");
        updatedStudent.setSurname("Smith");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        Student updated = studentService.updateStudent(1L, updatedStudent);
        assertEquals("Jane", updated.getName());
        assertEquals("Smith", updated.getSurname());
    }

    @Test
    public void testUpdateWorkingStatus() {
        Student student = new Student();
        student.setId(1L);
        student.setWorking(false);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        Student updated = studentService.updateWorkingStatus(1L, true);
        assertTrue(updated.isWorking());
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    private <T> CrudRepository verify(StudentRepository studentRepository, VerificationMode times) {
        return null;
    }
}
