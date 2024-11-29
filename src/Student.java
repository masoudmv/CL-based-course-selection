import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Student{

    final String studentId;
    final String password;
    static ArrayList<Student> studentList = new ArrayList<>();
    ArrayList<SpecializedCourse> studentSpecializedCourses = new ArrayList<>();
    ArrayList<GeneralCourse> studentGeneralCourses = new ArrayList<>();

    int credits;
    int generalCredits;



    Student(String studentId, String password){
        this.studentId = studentId;
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPassword() {
        return password;
    }



//    file handling


    public static void readCourses(Student student){
        try {
            FileReader reader = new FileReader(student.studentId + ".txt");
            int data = reader.read();
            StringBuilder inputData = new StringBuilder();

            while(data != -1){
                inputData.append((char) data);
                data = reader.read();
            }
            String input = inputData.toString();


            if (!input.isBlank()) {
                String[] users = input.split("\n");
                String[] userData;
                for (String cour : users) {
                    userData = cour.split("\\|");
                    if (userData[1].contentEquals("general")){
                        for(GeneralCourse i : GeneralCourse.generalCoursesList){
                            if(i.courseID.contentEquals(userData[0])){
                                student.studentGeneralCourses.add(i);
                                student.credits += i.credits;
                                student.generalCredits += i.credits;
                                i.studentList.add(student);
                                i.courseCurrentStudents ++;
                            }
                        }

                    } else if (userData[1].contentEquals("specialized")) {
                        for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                            if(i.courseID.contentEquals(userData[0].trim())){
                                student.studentSpecializedCourses.add(i);
                                student.credits += i.credits;
                                i.studentList.add(student);
                                i.courseCurrentStudents ++;
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e){
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//end file handling

}