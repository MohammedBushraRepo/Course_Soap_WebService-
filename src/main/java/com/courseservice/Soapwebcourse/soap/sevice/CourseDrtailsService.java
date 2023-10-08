package com.courseservice.Soapwebcourse.soap.sevice;

import com.courseservice.Soapwebcourse.soap.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CourseDrtailsService {
    /*Course  - findById()
    courses - List<Course> findAll()
    delete - deleteById()
    update - Course & new Course
    * */

    public enum Status{
        SUCCESS , FAILURE
    }
    private static List<Course> courses = new ArrayList<>();

    static {
        Course course1 = new Course(1,"Spring","best course");
        courses.add(course1);
        Course course2 = new Course(2,"angular","best angular course");
        courses.add(course2);
        Course course3 = new Course(3,"React","best React course");
        courses.add(course3);
        Course course4 = new Course(4,"ASP.NET","best .NET course");
        courses.add(course4);
    }
    // course
    public Course findById(int id){
        for (Course course:courses){
            if (course.getId()==id)
                return course;
            }
            return null;

    }
    // courses


    public List<Course> findAllCourses(){
        return courses;
    }
    public Status deleteById(int id){
        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()){
            Course course = iterator.next();
            if (course.getId()==id) {
                iterator.remove();
                return Status.SUCCESS;
            }

        }
        return Status.FAILURE;
    }
}
