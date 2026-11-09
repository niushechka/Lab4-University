package university;

import org.junit.Test;
import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;
import university.repository.InMemoryCourseRepository;
import university.service.EnrollmentService;

import static org.junit.Assert.*;

public class EnrollmentServiceTest {

    @Test
    public void testSuccessfulEnrollment() {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);
        Student student = new Student(1, "Ivan");
        repository.save(new Course(1, "Algebra"));

        Enrollment enrollment = service.enroll(student, 1);
        // WARN
        // service.enroll(student, 1); 

        // ERROR
        //service.enroll(null, 1);

        assertNotNull(enrollment);
        assertEquals(1, enrollment.getStudentId());
        assertEquals(1, enrollment.getCourseId());
        assertTrue(repository.existsEnrollment(1, 1));
    }

    @Test
    public void testCourseNotFound() {
        EnrollmentService service = new EnrollmentService(new InMemoryCourseRepository());
        Student student = new Student(1, "Ivan");

        try {
            service.enroll(student, 99);
            fail("Ожидалась ошибка: курс не найден");
        } catch (IllegalArgumentException e) {
            assertEquals("Course not found: 99", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Test
    public void testNullStudentForbidden() {
        EnrollmentService service = new EnrollmentService(new InMemoryCourseRepository());

        try {
            service.enroll(null, 1);
            fail("Ожидалась ошибка: студент не передан");
        } catch (IllegalArgumentException e) {
            assertEquals("Student must not be null", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Test
    public void testDuplicateEnrollmentForbidden() {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);
        Student student = new Student(1, "Ivan");
        repository.save(new Course(1, "Algebra"));

        service.enroll(student, 1);

        try {
            service.enroll(student, 1);
            fail("Ожидалась ошибка: студент уже записан на курс");
        } catch (IllegalStateException e) {
            assertEquals("Student 1 is already enrolled in course 1", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
