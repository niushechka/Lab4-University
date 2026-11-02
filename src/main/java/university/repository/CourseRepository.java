package university.repository;

import university.model.Course;
import university.model.Enrollment;

public interface CourseRepository {
    void save(Course course);

    Course findById(int id);

    void saveEnrollment(Enrollment enrollment);

    boolean existsEnrollment(int studentId, int courseId);
}
