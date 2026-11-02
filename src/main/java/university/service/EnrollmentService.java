package university.service;

import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;

public class EnrollmentService {
    private final CourseRepository courseRepository;

    public EnrollmentService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Enrollment enroll(Student student, int courseId) {
        if (student == null) {
            System.out.println("[LOG] Отказ: студент не передан");
            throw new IllegalArgumentException("Student must not be null");
        }

        System.out.println("[LOG] Попытка записи: студент " + student.getName()
                + " (id=" + student.getId() + ") на курс id=" + courseId);

        Course course = courseRepository.findById(courseId);
        if (course == null) {
            System.out.println("[LOG] Отказ: курс id=" + courseId + " не найден");
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        if (courseRepository.existsEnrollment(student.getId(), courseId)) {
            System.out.println("[LOG] Отказ: студент " + student.getName()
                    + " уже записан на курс " + course.getTitle());
            throw new IllegalStateException(
                    "Student " + student.getId() + " is already enrolled in course " + courseId);
        }
        Enrollment enrollment = new Enrollment(student.getId(), courseId);
        courseRepository.saveEnrollment(enrollment);
        System.out.println("[LOG] Успех: студент " + student.getName()
                + " записан на курс " + course.getTitle());
        return enrollment;
    }
}
