package university;

import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;
import university.repository.InMemoryCourseRepository;
import university.service.EnrollmentService;

public class Application {
    public static void main(String[] args) {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService enrollmentService = new EnrollmentService(repository);

        repository.save(new Course(1, "Algebra"));
        Student student = new Student(1, "Ivan Ivanov");

        Enrollment enrollment = enrollmentService.enroll(student, 1);
        System.out.println("Enrolled: student " + enrollment.getStudentId()
                + " -> course " + enrollment.getCourseId());

        if (true) System.out.println(2);

        try { 
            enrollmentService.enroll(student, 1);
        } catch (IllegalStateException e) {
            System.out.println("Duplicate enrollment forbidden: " + e.getMessage());
        }

        try {
            enrollmentService.enroll(student, 99);
        } catch (IllegalArgumentException e) {
            System.out.println("Missing course handled: " + e.getMessage());
        }
    }
}
