package service;

import model.Course;
import java.util.List;
import java.util.Map;
import reponsitory.CourseRepository;

public class CourseService implements ICourseService {

    private List<Course> listCourse;
    private Map<String, List<Course>> listRegistering;
    private CourseRepository courseRepository;

    public CourseService() {
        courseRepository = new CourseRepository();
        listCourse = courseRepository.readFile();
    }

    public Map<String, List<Course>> getListRegistering() {
        return listRegistering;
    }

    public List<Course> getListCourse() {
        return listCourse;
    }

    public void update(Course e) {
        for (int i = 0; i < listCourse.size(); i++) {
            if (listCourse.get(i).getCourseId().equalsIgnoreCase(e.getCourseId())) {
                listCourse.set(i, e);
                new CourseRepository().writeFile(listCourse);
            }
        }
    }

    public void delete(String e) {
        listCourse.removeIf(p -> p.getCourseId().equalsIgnoreCase(e));
        new CourseRepository().writeFile(listCourse);
    }

    public void createCourse(Course e) {
        listCourse.add(e);
        new CourseRepository().writeFile(listCourse);
    }
    
    @Override
    public void display() {

    }

    @Override
    public Course findByID(String id) {
        return listCourse.stream().filter(p -> p.getCourseId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
