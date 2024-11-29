import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.*;


public class Course{


    String courseID;
    String courseName;
    int courseMaxStudents;
    int courseCurrentStudents;
    int credits;
    String courseTime;
    LocalDateTime courseExamTime;
    ArrayList<Student> studentList = new ArrayList<>();
    String instructorName;
    String courseDepartment;

    Course(String courseID, String courseName, int courseMaxStudents, int CourseCurrentStudents,
           String instructorName, String courseDepartment,
           int credits, LocalDateTime courseExamTime, String courseTime){
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseMaxStudents = courseMaxStudents;
        this.courseCurrentStudents = CourseCurrentStudents;
        this.instructorName = instructorName;
        this.courseDepartment = courseDepartment;
        this.credits = credits;
        this.courseExamTime = courseExamTime;
        this.courseTime = courseTime;
    }

    public static void printCourses(String courseDepartment){
        if(courseDepartment.contentEquals("Chemistry")){
            for(GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseDepartment.contentEquals("Chemistry")) {
                    printGeneral(i);
                }
            } for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseDepartment.contentEquals("Chemistry")) {
                    printSpecialized(i);
                }
            }
        } else if (courseDepartment.contentEquals("Math")) {
            for(GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseDepartment.contentEquals("Math")) {
                    printGeneral(i);
                }
            } for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseDepartment.contentEquals("Math")) {
                    printSpecialized(i);
                }
            }
        } else if (courseDepartment.contentEquals("Economics")) {
            for(GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseDepartment.contentEquals("Economics")) {
                    printGeneral(i);
                }
            } for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseDepartment.contentEquals("Economics")) {
                    printSpecialized(i);
                }
            }
        } else if (courseDepartment.contentEquals("Physics")) {
            for(GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseDepartment.contentEquals("Physics")) {
                    printGeneral(i);
                }
            } for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseDepartment.contentEquals("Physics")) {
                    printSpecialized(i);
                }
            }
        }
    }


//    public static v

    public static void printGeneral(GeneralCourse i) {
        System.out.println("Course: " + i.courseName + "\n" + "Course ID: " + i.courseID + "\n" + "course type: " + i.type
                + "\n" + "Maximum # of Students: " + i.courseMaxStudents + "\n"  +
                "Current # of Students: " + i.courseCurrentStudents + "\n" + "credits:" + i.credits +
                "\n" + "Instructor: " + i.instructorName + "\n" + "Department: " + i.courseDepartment
                + "\n"+ "courseExam:"+ i.courseExamTime + "\n" + "Class Time: "+ i.courseTime);
        System.out.println("================");
    }

    public static void printSpecialized(SpecializedCourse i) {
        System.out.println("Course: " + i.courseName + "\n" + "Course ID: " + i.courseID + "\n" + "course type: " + i.type
                + "\n" + "Maximum # of Students: " + i.courseMaxStudents + "\n" +
                "Current # of Students: " + i.courseCurrentStudents + "\n" + "credits:" + i.credits +
                "\n" + "Instructor: " + i.instructorName + "\n" + "Department: " + i.courseDepartment
                + "\n"+ "courseExam:"+ i.courseExamTime + "\n" + "Class Time: "+ i.courseTime);
        System.out.println("================");
    }

    public void setCourseMaxStudents(int courseMaxStudents) { this.courseMaxStudents = courseMaxStudents; }


//    file handling

    public static void readFile() {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            FileReader reader = new FileReader("courses.txt");

            AdminInterface.readCourse(formatter, reader);

        } catch (FileNotFoundException e){
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




//    end file handling
}