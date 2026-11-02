package university.repository;

import university.model.Course;
import university.model.Enrollment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCourseRepository implements CourseRepository {
    private final Map<Integer, Course> courses = new HashMap<Integer, Course>();
    private final List<Enrollment> enrollments = new ArrayList<Enrollment>();

    public void save(Course course) {
        courses.put(course.getId(), course);
    }

    public Course findById(int id) {
        return courses.get(id);
    }

    public void saveEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public boolean existsEnrollment(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && enrollment.getCourseId() == courseId) {
                return true;
            }
        }
        return false;
    }
}
