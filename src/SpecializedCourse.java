import java.time.LocalDateTime;
import java.util.ArrayList;

public class SpecializedCourse extends Course{
    static ArrayList<SpecializedCourse> specializedCoursesList= new ArrayList<>();
//    static ArrayList<SpecializedCourse> newSpecializedCoursesList= new ArrayList<>();
    final String type;

    SpecializedCourse(String courseID, String courseName, int courseMaxStudents,
                      int CourseCurrentStudents, String instructorName, String courseDepartment,
                      int credits, LocalDateTime courseExamTime, String courseTime){

        super(courseID, courseName, courseMaxStudents, CourseCurrentStudents, instructorName,
                courseDepartment, credits, courseExamTime,  courseTime);
        this.type = "specialized";
    }
    public static void add(SpecializedCourse course){
        SpecializedCourse.specializedCoursesList.add(course);
    }
}
