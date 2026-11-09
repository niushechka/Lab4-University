package university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;

public class EnrollmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentService.class);

    private final CourseRepository courseRepository;

    public EnrollmentService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Enrollment enroll(Student student, int courseId) {
        if (student == null) {
            LOGGER.error("Enrollment failed: student must not be null, courseId={}", courseId);
            throw new IllegalArgumentException("Student must not be null");
        }

        LOGGER.info("Enrollment request: student {} (id={}) to course id={}",
                student.getName(), student.getId(), courseId);

        Course course = courseRepository.findById(courseId);
        if (course == null) {
            LOGGER.error("Enrollment failed: course id={} not found for student id={}",
                    courseId, student.getId());
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        if (courseRepository.existsEnrollment(student.getId(), courseId)) {
            LOGGER.warn("Student {} is already enrolled in course {}",
                    student.getName(), course.getTitle());
            throw new IllegalStateException(
                    "Student " + student.getId() + " is already enrolled in course " + courseId);
        }
        Enrollment enrollment = new Enrollment(student.getId(), courseId);
        courseRepository.saveEnrollment(enrollment);
        LOGGER.info("Student {} enrolled in course {}",
                student.getName(), course.getTitle());
        return enrollment;
    }
}
