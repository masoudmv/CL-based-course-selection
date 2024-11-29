import java.time.LocalDateTime;
import java.util.ArrayList;

public class GeneralCourse extends Course{

    static ArrayList<GeneralCourse> generalCoursesList = new ArrayList<>();
//    static ArrayList<GeneralCourse> newGeneralCoursesList = new ArrayList<>();
    final String type;
    GeneralCourse(String courseID, String courseName, int courseMaxStudents,
                  int CourseCurrentStudents, String instructorName, String courseDepartment,
                  int credits, LocalDateTime courseExamTime, String courseTime){

        super(courseID, courseName, courseMaxStudents, CourseCurrentStudents, instructorName,
                courseDepartment, credits, courseExamTime,  courseTime);
        this.type = "general";
    }
    public static void add(GeneralCourse course){
        GeneralCourse.generalCoursesList.add(course);
    }

}