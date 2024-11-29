import java.io.*;

import java.util.Scanner;

public interface StudentInterface{

    Scanner scanner = new Scanner(System.in);

    static void studentLogin() throws IOException {
        System.out.println("you are in the student log in menu.");
        System.out.println("please enter your studentID:");
        while (true) {
            String studentID = scanner.nextLine();
            if (studentID.contentEquals("cancel")){
                return;
            }
            for (Student i : Student.studentList){
                if (i.getStudentId().contentEquals(studentID)){
                    System.out.println("please enter your password:");
                    String password = scanner.nextLine();
                    if (password.contentEquals("cancel")){
                        return;
                    }
                    while (!i.getPassword().contentEquals(password)){
                        System.out.println("your password is not correct. please try again.");
                        System.out.println("enter your password:");
                        password = scanner.nextLine();
                        if (password.contentEquals("cancel")){
                            return;
                        }
                    }
                    System.out.println("you have successfully logged in as student");
                    System.out.println("================");
                    studentTools(i);
                    return;
                }

            }
            System.out.println("this username is not registered as a valid student ID");
            System.out.println("please enter another studentID:");
        }

    }

    static void studentTools(Student student) throws IOException {

        while (true) {
            System.out.println("Your studentID is "+ student.studentId);
            System.out.println("You currently  " + student.credits + " credits");
            System.out.println("Enter '1' to see registered courses of the student");
            System.out.println("Enter '2' to see all courses");
            System.out.println("Enter \"back\" to return to previous step");
            String option2 = scanner.nextLine();
            if (option2.contentEquals("1")){
                showRegisteredCourses(student);
            } else if (option2.contentEquals("2")) {
                printAllCourses(student);
            } else if (option2.contentEquals("back")) {
                System.out.println("================");
                break;
            } else {
                System.out.println("your input is not valid! Try again.");
            }
        }
    }

    //can do better
    static void printAllCourses(Student student) {
        while (true) {
            System.out.println("Enter department code to see their courses:");
            System.out.println("1.Chemistry");
            System.out.println("2.Math");
            System.out.println("3.Economics");
            System.out.println("4.Physics");
            System.out.println("And Enter \"back\" to return to the previous step");
            String department = scanner.nextLine();
            if(!department.contentEquals("1") && !department.contentEquals("2") && !department.contentEquals("3")
                    && !department.contentEquals("4") && !department.contentEquals("back")){
                System.out.println("your input is not valid! Try again.");
                printAllCourses(student);
                break;
            } if (department.contentEquals("1")) {
                department = "Chemistry";

            } else if (department.contentEquals("2")) {
                department = "Math";
            } else if (department.contentEquals("3")) {
                department = "Economics";

            } else if (department.contentEquals("4")) {
                department = "Physics";
            } else if (department.contentEquals("back")) {
                break;
            }
            Course.printCourses(department);
            addCourse(student);

            break;

        }
    }

    static void showRegisteredCourses(Student student) throws IOException {
        for (SpecializedCourse i : student.studentSpecializedCourses){
            Course.printSpecialized(i);
        } for (GeneralCourse i : student.studentGeneralCourses){
            Course.printGeneral(i);
        }
        if (student.studentGeneralCourses.isEmpty() && student.studentSpecializedCourses.isEmpty()){
            System.out.println("You have not been registered in any course!");
            System.out.println("================");
            return;
        }
        System.out.println("Enter a courseID to remove the selected course");
        String courseID = scanner.nextLine();
        System.out.println(courseID);

        if (courseID.contentEquals("cancel")){
            System.out.println("================");
            return;
        }
        while (true) {

            for (SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseID.equals(courseID)){
                    student.studentSpecializedCourses.remove(i);
                    student.credits -= i.credits;
                    i.studentList.remove(student);
                    i.courseCurrentStudents --;
                    removeStudentCourseFromFile(student);

                    System.out.println("You have been successfully removed from " + i.courseName);
                    System.out.println("================");
                    return;
                }
            }
            for (GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseID.equals(courseID)){
                    student.studentGeneralCourses.remove(i);
                    student.credits -= i.credits;
                    student.generalCredits -= i.credits;
                    i.studentList.remove(student);
                    i.courseCurrentStudents --;
                    removeStudentCourseFromFile(student);
                    System.out.println("You have been successfully removed from "+ i.courseName);
                    System.out.println("================");
                    return;
                }
            }
            System.out.println("Your input is not a valid courseID. please try again.");
            System.out.println("Enter a valid courseID:");
            courseID = scanner.nextLine();
            if (courseID.contentEquals("cancel")){
                System.out.println("================");
                return;
            }
        }
    }

    static void addCourse(Student student){
        System.out.println("Enter a courseID to register to the course");
        String courseID = scanner.nextLine();
        if (courseID.contentEquals("cancel")){
            System.out.println("================");
            return;
        }
        while (true) {
            for (SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    if (i.courseMaxStudents == i.courseCurrentStudents){
                        System.out.println("This course is already full.");
                        System.out.println("================");
                        addCourse(student);
                        return;
                    } if (student.credits + i.credits > 20){
                        System.out.println("You can't register to more than 20 credits");
                        System.out.println("================");
                        addCourse(student);
                        return;
                    } for (SpecializedCourse j : student.studentSpecializedCourses){
                        if (i.courseExamTime.isEqual(j.courseExamTime)){
                            System.out.println("the selected course exam time has interfere with course "
                                    + j.courseName+" exam time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        } else if (i.courseTime.contentEquals(j.courseTime)) {
                            System.out.println("the selected class time has interfere with course "
                                    + j.courseName+" class time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        }
                    } for (GeneralCourse j : student.studentGeneralCourses){
                        if (i.courseExamTime.isEqual(j.courseExamTime)){
                            System.out.println("the selected course exam time has interfere with course "
                                    + j.courseName+" exam time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        } else if (i.courseTime.contentEquals(j.courseTime)) {
                            System.out.println("the selected class time has interfere with course "
                                    + j.courseName+" class time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        }
                    }
                    student.studentSpecializedCourses.add(i);
                    student.credits += i.credits;
                    i.studentList.add(student);
                    i.courseCurrentStudents ++;


//    file handling part

                    addStudentCourseToFile(student, i, "specialized");

//     end of file handling part

                    System.out.println("You have been successfully registered to "+ i.courseName);
                    addCourse(student);
                    return;
                }
            }
            for (GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    if (i.courseMaxStudents == i.courseCurrentStudents){
                        System.out.println("This course is already full.");
                        System.out.println("================");
                        addCourse(student);
                        return;
                    } if (student.generalCredits + i.credits > 5){
                        System.out.println("Your general credits can't exceed 5");
                        System.out.println("================");
                        addCourse(student);
                        return;
                    } if (student.credits + i.credits > 20){
                        System.out.println("You can't register to more than 20 credits");
                        System.out.println("================");
                        addCourse(student);
                        return;
                    } for (GeneralCourse j : student.studentGeneralCourses){
                        if (i.courseExamTime.isEqual(j.courseExamTime)){
                            System.out.println("the selected course exam time has interfere with course "
                                    + j.courseName+" exam time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        } else if (i.courseTime.contentEquals(j.courseTime)) {
                            System.out.println("the selected class time has interfere with course "
                                    + j.courseName+" class time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        }
                    } for (SpecializedCourse j : student.studentSpecializedCourses){
                        if (i.courseExamTime.isEqual(j.courseExamTime)){
                            System.out.println("the selected course exam time has interfere with course "
                                    + j.courseName+" exam time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        } else if (i.courseTime.contentEquals(j.courseTime)) {
                            System.out.println("the selected class time has interfere with course "
                                    + j.courseName+" class time");
                            System.out.println("================");
                            addCourse(student);
                            return;
                        }
                    }
                    student.studentGeneralCourses.add(i);
                    student.credits += i.credits;
                    student.generalCredits += i.credits;
                    i.studentList.add(student);
                    i.courseCurrentStudents ++;

//    file handling part
                    addStudentCourseToFile(student, i, "general");
//     end of file handling part

                    System.out.println("You have been successfully registered to "+ i.courseName);
                    System.out.println("================");
                    addCourse(student);


                    return;
                }
            }
            System.out.println("Your input is not a valid courseID. please try again.");
            System.out.println("Enter a valid courseID:");
            courseID = scanner.nextLine();
            if (courseID.contentEquals("cancel")){
                System.out.println("================");
                return;
            }
        }
    }



    //file handling
    static void addStudentCourseToFile(Student student, Course i, String type){
        File file = new File(student.studentId+".txt");
        try {

            FileWriter myWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(myWriter);
            writer.append(i.courseID).append("|").append(type).append("\n");
            writer.close();

        } catch (FileNotFoundException e){
//

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static void removeStudentCourseFromFile(Student student) throws IOException {
        File file = new File(student.studentId+".txt");
        FileWriter myWriter = new FileWriter(file, false);
        FileWriter myAppender = new FileWriter(file, true);
        BufferedWriter appender = new BufferedWriter(myAppender);
        try {
            myWriter.write("");
            for(GeneralCourse i : student.studentGeneralCourses){
                appender.append(i.courseID).append("|").append("general").append("\n");
            } for(SpecializedCourse i : student.studentSpecializedCourses){
                appender.append(i.courseID).append("|").append("specialized").append("\n");
            }
            myWriter.close();
            appender.close();

        } catch (FileNotFoundException e){
//

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
