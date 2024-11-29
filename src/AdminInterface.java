
import java.io.*;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;


import java.util.Scanner;

public interface AdminInterface {


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    Scanner scanner = new Scanner(System.in);
    static void showDepartments() throws IOException {
        while (true) {
            System.out.println("You are logged in as admin.");
            System.out.println("Enter department code to see their courses:");
            System.out.println("1.Chemistry");
            System.out.println("2.Math");
            System.out.println("3.Economics");
            System.out.println("4.Physics");
            System.out.println("5.import data");
            System.out.println("6.export data");
            System.out.println("7.log out");

            String department = scanner.nextLine();

            if (department.contentEquals("1")) {
                department = "Chemistry";
                Course.printCourses("Chemistry");
                adminTools(department);
            } else if (department.contentEquals("2")) {
                department = "Math";
                Course.printCourses("Math");
                adminTools(department);
            } else if (department.contentEquals("3")) {
                department = "Economics";
                Course.printCourses("Economics");
                adminTools(department);
            } else if (department.contentEquals("4")) {
                department = "Physics";
                Course.printCourses("Physics");
                adminTools(department);

//                file handling
            } else if (department.contentEquals("5")){
                System.out.println("Enter an address to read its courses:");
                String path = scanner.nextLine();

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    FileReader reader = new FileReader(path);

                    readCourse(formatter, reader);
                    System.out.println("the courses are successfully added.");
                    System.out.println("================");
                    showDepartments();
                    break;

                } catch (FileNotFoundException e){
                    System.out.println("File not found.");
                    System.out.println("================");
                    showDepartments();
                    break;


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            } else if (department.contentEquals("6")) {
                System.out.println("Enter file location");
                String location = scanner.nextLine();
                File myObj = new File(location);
                FileWriter myWriter = new FileWriter(myObj, false);
                FileWriter myAppender = new FileWriter(myObj, true);
                BufferedWriter appender = new BufferedWriter(myAppender);
                try {


//                    myWriter.write("");

                    for (SpecializedCourse j : SpecializedCourse.specializedCoursesList){
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String formattedDateTime = j.courseExamTime.format(dateTimeFormatter);

//                        System.out.println(j.courseID);
//                        System.out.println(j.courseName);
//                        System.out.println(j.courseMaxStudents);
//                        System.out.println(j.instructorName);
//                        System.out.println(j.type);

                        appender.append(j.courseID).append("|").append(j.courseName).append("|")
                                .append(Integer.toString(j.courseMaxStudents)).append("|").append(Integer.toString(0)).append("|")
                                .append(j.instructorName).append("|").append(j.courseDepartment).append("|")
                                .append(Integer.toString(j.credits)).append("|").append(formattedDateTime)
                                .append("|").append(j.courseTime).append("|").append(j.type).append("\n");
                        myWriter.close();
                        appender.close();
                    }

                    for (GeneralCourse i : GeneralCourse.generalCoursesList){
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String formattedDateTime = i.courseExamTime.format(dateTimeFormatter);
                        appender.append(i.courseID).append("|").append(i.courseName).append("|")
                                .append(Integer.toString(i.courseMaxStudents)).append("|").append(Integer.toString(0)).append("|")
                                .append(i.instructorName).append("|").append(i.courseDepartment).append("|")
                                .append(Integer.toString(i.credits)).append("|").append(formattedDateTime)
                                .append("|").append(i.courseTime).append("|").append(i.type).append("\n");
                    }
//                    System.out.println("exported courses successfully.");
                    System.out.println("exported courses successfully.");
                    System.out.println("================");


                    showDepartments();
                    return;

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    showDepartments();
                    return;
                }
//                end of file handling
            } else if (department.contentEquals("7")) {
                System.out.println("================");
                break;
            } System.out.println("your input is not valid! Try again.");
            System.out.println("================");

        }
    }

    static void readCourse(DateTimeFormatter formatter, FileReader reader) throws IOException {
        int data = reader.read();
        StringBuilder inputData = new StringBuilder();

        while(data != -1){
            inputData.append((char) data);
            data = reader.read();
        }
        String input = inputData.toString();
        String[] courses = input.split("\n");
        String[] courseData;

        for (String cour : courses) {
            courseData = cour.split("\\|");
            if (courseData[9].trim().contentEquals("general")){
                GeneralCourse.add(new GeneralCourse(courseData[0], courseData[1], Integer.parseInt(courseData[2].trim()),
                        Integer.parseInt(courseData[3].trim()), courseData[4], courseData[5],
                        Integer.parseInt(courseData[6]), LocalDateTime.parse(courseData[7] , formatter), courseData[8]));
            } else if (courseData[9].trim().contentEquals("specialized")){
                SpecializedCourse.add(new SpecializedCourse(courseData[0], courseData[1], Integer.parseInt(courseData[2].trim()),
                        Integer.parseInt(courseData[3].trim()), courseData[4], courseData[5],
                        Integer.parseInt(courseData[6]), LocalDateTime.parse(courseData[7] , formatter), courseData[8]));
            }
        }
    }

    static void adminTools(String department) throws IOException {
        String option2;
        while (true) {
            System.out.println("Enter '1' to see registered students of a course");
            System.out.println("Enter '2' to add a course");
            System.out.println("Enter '3' to remove a course");
            System.out.println("Enter '4' to edit a course");
            System.out.println("Enter \"back\" to return to previous step");
            option2 = scanner.nextLine();
            if (option2.contentEquals("1")){
                showStudentsOfACourse();
            } else if (option2.contentEquals("2")) {
                createCourse(department);
            } else if (option2.contentEquals("3")) {
                removeCourse();
            } else if (option2.contentEquals("4")) {
                editCourse();
                // can do better
            } else if (option2.contentEquals("back")) {
                System.out.println("================");
                break;
            } else {
                System.out.println("your input is not valid! Try again.");
                System.out.println("================");
            }
        }
    }

    static void removeCourse() throws IOException {
        while (true){
            System.out.println("Enter a courseID to remove the course.");
            String courseID = scanner.nextLine();
            for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    SpecializedCourse.specializedCoursesList.remove(i);
                    for (Student student:i.studentList){
                        student.credits -= i.credits;
                        student.studentSpecializedCourses.remove(i);
                        StudentInterface.removeStudentCourseFromFile(student);
                    }
                    System.out.println("the course was successfully removed.");
                    System.out.println("================");
                    return;
                }
            } for (GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    GeneralCourse.generalCoursesList.remove(i);
                    for (Student student:i.studentList){
                        student.generalCredits -= i.credits;
                        student.credits -= i.credits;
                        student.studentGeneralCourses.remove(i);
                        StudentInterface.removeStudentCourseFromFile(student);
                    }
                    System.out.println("the course was successfully removed.");
                    System.out.println("================");
                    return;
                }
            }
            if(courseID.contentEquals("cancel")){
                System.out.println("================");
                break;
            } System.out.println("your input is not a valid course ID! please Try again.");
        }
    }
    static void adminLogin() throws IOException {

        System.out.println("Enter admin username:");
        String adminUsername = scanner.nextLine();
        if (adminUsername.contentEquals("cancel")) {
            System.out.println("================");
            return;
        } while (!adminUsername.contentEquals(Admin.getUsername())) {
            System.out.println("your admin username is not correct. please try again.");
            System.out.println("Enter admin username:");
            adminUsername = scanner.nextLine();
            if (adminUsername.contentEquals("cancel")) {
                System.out.println("================");
                return;
            }
        }
        System.out.println("Enter admin password:");
        String adminPassword = scanner.nextLine();
        if (adminPassword.contentEquals("cancel")) {
            System.out.println("================");
            return;
        } while (!adminPassword.contentEquals(Admin.getPassword())) {
            if (adminPassword.contentEquals("cancel")) {
                System.out.println("================");
                return;
            }
            System.out.println("your admin password is not correct. please try again.");
            System.out.println("Enter admin password:");
            adminPassword = scanner.nextLine();
        } System.out.println("You have successfully logged in as admin.");
        System.out.println("================");
        showDepartments();
    }


    static void showStudentsOfACourse() throws IOException {
        while (true){
            System.out.println("Enter a courseID to see the registered students.");
            String courseID = scanner.nextLine();
            for(GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    if (i.studentList.isEmpty()){
                        System.out.println("Selected course does not have any registered student");
                        addStudentToGeneralCourse(i);
                    }
                    else {
                        System.out.println("registered students of " + i.courseName + ":");
                        for (Student j : i.studentList){
                            System.out.println(j.studentId);
                        }
                        System.out.println("================");
                        addRemoveGeneral(i);
                    }
                    return;
                }
            } for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    if (i.studentList.isEmpty()){
                        System.out.println("Selected course does not have any registered student");
                        addStudentToSpecializedCourse(i);
                    }
                    else {
                        System.out.println("registered students of " + i.courseName + ":");
                        for (Student j : i.studentList){
                            System.out.println(j.studentId);
                        }
                        System.out.println("================");
                        addRemoveSpecialized(i);
                    }
                    return;
                }
            } if (courseID.contentEquals("cancel")){
                System.out.println("================");
                break;
            } System.out.println("your input is not a valid course ID! please Try again.");
        }
    }

    static void editCourse(){
        while (true){
            System.out.println("Enter a courseID to edit the course.");
            String courseID = scanner.nextLine();

            for(GeneralCourse i : GeneralCourse.generalCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    System.out.println("Enter new max # students:");
                    String max = scanner.nextLine();
                    int maxStudent = Integer.parseInt(max);
                    i.setCourseMaxStudents(maxStudent);
                    System.out.println("the course was successfully edited.");
                    System.out.println("================");
                    return;
                }
            } for(SpecializedCourse i : SpecializedCourse.specializedCoursesList){
                if (i.courseID.contentEquals(courseID)){
                    System.out.println("Enter new max # students:");
                    String max = scanner.nextLine();
                    int maxStudent = Integer.parseInt(max);
                    i.setCourseMaxStudents(maxStudent);
                    System.out.println("the course was successfully edited.");
                    System.out.println("================");
                    return;
                }
            }

            if(courseID.contentEquals("cancel")){
                System.out.println("================");
                break;
            }
            System.out.println("your input is not a valid course ID! please Try again.");
        }
    }
    static void createCourse(String department){

        System.out.println("Enter a courseID to creat a new course.");
        String courseID = scanner.nextLine();
        if (courseID.contentEquals("cancel")){
            System.out.println("================");
            return;
        }


        System.out.println("Enter the course name:");
        String courseName = scanner.nextLine();
        if (courseName.contentEquals("cancel")){
            System.out.println("================");
            return;
        }
        System.out.println("""
                       Enter course type:\s
                       1.general
                       2.specialized""");
        String type = scanner.nextLine();
        if (type.contentEquals("cancel")){
            System.out.println("================");
            return;
        }
        while (!type.contentEquals("1") && !type.contentEquals("2")){
            System.out.println("""
                        Enter a valid course type:\s
                        1.general
                        2.specialized""");
            type = scanner.nextLine();
            if (type.contentEquals("cancel")){
                System.out.println("================");
                return;
            }

        }

        int maxStudent = 0;
        while (true) {
            try {
                System.out.println("Enter course max # of students");
                String max = scanner.nextLine();
                if (max.contentEquals("cancel")){
                    System.out.println("================");
                    return;
                }
                maxStudent = Integer.parseInt(max);
                break;
            } catch (NumberFormatException e) {
                System.out.println("you must enter an integer.");
            }
        }


        System.out.println("Enter instructor name:");
        String instructorName = scanner.nextLine();
        if (instructorName.contentEquals("cancel")){
            System.out.println("================");
            return;
        }


        int credit = 0;
        while (true) {
            try {
                System.out.println("Enter # of credits of the course:");
                String stringCredit = scanner.nextLine();
                if (stringCredit.contentEquals("cancel")){
                    System.out.println("================");
                    break;
                }
                credit = Integer.parseInt(stringCredit);
                break;
            } catch (NumberFormatException e) {
                System.out.println("you must enter an integer.");
            }
        }

        System.out.println("Enter course Time:");
        String courseTime = scanner.nextLine();
        if (courseTime.contentEquals("cancel")) {
            System.out.println("================");
            return;
        }


        while (true) {
            try {
                System.out.println("Enter course exam date and time:");
                System.out.println("input time should be in this form \"yyyy-mm-dd hh:mm\" ");
                String str = scanner.nextLine();
                if (str.contentEquals("cancel")){
                    System.out.println("================");
                    return;
                }

                LocalDateTime examTime = LocalDateTime.parse(str, formatter);
                if (type.contentEquals("1")) {
                    GeneralCourse.add(new GeneralCourse(courseID, courseName, maxStudent,
                            0, instructorName, department, credit, examTime, courseTime));

//                    GeneralCourse.newGeneralCoursesList.add(new GeneralCourse(courseID, courseName, maxStudent,
//                                    0, instructorName, department, credit, examTime, courseTime));

                    System.out.println("course " + courseName + " was created successfully.");
                    System.out.println("================");
                    break;

                } else if (type.contentEquals("2")) {
                    SpecializedCourse.add(new SpecializedCourse(courseID, courseName, maxStudent,
                            0, instructorName, department, credit, examTime, courseTime));

//                    SpecializedCourse.newSpecializedCoursesList.add(new SpecializedCourse(courseID, courseName, maxStudent,
//                            0, instructorName, department, credit, examTime, courseTime));

                    System.out.println("course " + courseName + " was created successfully.");
                    System.out.println("================");
                    return;
                }
            } catch (Exception ignored) {

            }
        }
    }

    private static void addStudentToGeneralCourse(GeneralCourse course){
        while (true) {
            System.out.println("Enter a studentID to add to the selected course");
            String studentID = scanner.nextLine();
            if (studentID.contentEquals("cancel")){
                System.out.println("================");
                return;
            }
            for (Student student : Student.studentList){
                if (student.studentId.contentEquals(studentID)){
                    if (course.courseID.contentEquals(course.courseID)){
                        if (course.courseMaxStudents == course.courseCurrentStudents){
                            System.out.println("This course is already full.");
                            System.out.println("================");
                            addStudentToGeneralCourse(course);
                            return;
                        } if (student.generalCredits + course.credits > 5){
                            System.out.println("student's general credits can't exceed 5");
                            System.out.println("================");
                            addStudentToGeneralCourse(course);
                            return;
                        } if (student.credits + course.credits > 20){
                            System.out.println("student can't register to more than 20 credits");
                            System.out.println("================");
                            addStudentToGeneralCourse(course);
                            return;
                        } for (GeneralCourse j : student.studentGeneralCourses){
                            if (course.courseExamTime.isEqual(j.courseExamTime)){
                                System.out.println("the selected course exam time has interfere with course "
                                        + j.courseName+" exam time");
                                System.out.println("================");
                                addStudentToGeneralCourse(course);
                                return;
                            } else if (course.courseTime.contentEquals(j.courseTime)) {
                                System.out.println("the selected class time has interfere with course "
                                        + j.courseName+" class time");
                                System.out.println("================");
                                addStudentToGeneralCourse(course);
                                return;
                            }
                        } for (SpecializedCourse j : student.studentSpecializedCourses){
                            if (course.courseExamTime.isEqual(j.courseExamTime)){
                                System.out.println("the selected course exam time has interfere with course "
                                        + j.courseName+" exam time");
                                System.out.println("================");
                                addStudentToGeneralCourse(course);
                                return;
                            } else if (course.courseTime.contentEquals(j.courseTime)) {
                                System.out.println("the selected class time has interfere with course "
                                        + j.courseName+" class time");
                                System.out.println("================");
                                addStudentToGeneralCourse(course);
                                return;
                            }
                        }
                        student.studentGeneralCourses.add(course);
                        student.credits += course.credits;
                        student.generalCredits += course.credits;
                        course.studentList.add(student);
                        course.courseCurrentStudents ++;
                        System.out.println("student " + studentID + " was successfully added to " + course.courseName);
                        System.out.println("================");
//    file handling part
                        StudentInterface.addStudentCourseToFile(student, course, "general");
//    end of file handling part
                        return;
                    }
                }
            }
        }
    }


    private static void addStudentToSpecializedCourse(SpecializedCourse course){
        while (true) {
            System.out.println("Enter a studentID to add to the selected course");
            String studentID = scanner.nextLine();
            if (studentID.contentEquals("cancel")){
                System.out.println("================");
                return;
            } for (Student student : Student.studentList){
                if (student.studentId.contentEquals(studentID)){
                    if (course.courseID.contentEquals(course.courseID)){
                        if (course.courseMaxStudents == course.courseCurrentStudents){
                            System.out.println("This course is already full.");
                            System.out.println("================");
                            addStudentToSpecializedCourse(course);
                            return;
                        }  if (student.credits + course.credits > 20){
                            System.out.println("student can't register to more than 20 credits");
                            System.out.println("================");
                            addStudentToSpecializedCourse(course);
                            return;
                        } for (GeneralCourse j : student.studentGeneralCourses){
                            if (course.courseExamTime.isEqual(j.courseExamTime)){
                                System.out.println("the selected course exam time has interfere with course "
                                        + j.courseName+" exam time");
                                System.out.println("================");
                                addStudentToSpecializedCourse(course);
                                return;
                            } else if (course.courseTime.contentEquals(j.courseTime)) {
                                System.out.println("the selected class time has interfere with course "
                                        + j.courseName+" class time");
                                System.out.println("================");
                                addStudentToSpecializedCourse(course);
                                return;
                            }
                        } for (SpecializedCourse j : student.studentSpecializedCourses){
                            if (course.courseExamTime.isEqual(j.courseExamTime)){
                                System.out.println("the selected course exam time has interfere with course "
                                        + j.courseName+" exam time");
                                System.out.println("================");
                                addStudentToSpecializedCourse(course);
                                return;
                            } else if (course.courseTime.contentEquals(j.courseTime)) {
                                System.out.println("the selected class time has interfere with course "
                                        + j.courseName+" class time");
                                System.out.println("================");
                                addStudentToSpecializedCourse(course);
                                return;
                            }
                        } student.studentSpecializedCourses.add(course);
                        student.credits += course.credits;
                        student.generalCredits += course.credits;
                        course.studentList.add(student);
                        course.courseCurrentStudents ++;
                        System.out.println("student " + studentID + " was successfully added to " + course.courseName);
                        System.out.println("================");
//    file handling part
                        StudentInterface.addStudentCourseToFile(student, course, "specialized");
//    end of file handling part
                        return;
                    }
                }
            }
        }
    }

    private static void removeStudentFromGeneralCourse(GeneralCourse course) throws IOException {
        while (true) {
            System.out.println("Enter a studentID to remove from the selected course");
            String studentID = scanner.nextLine();
            if (studentID.contentEquals("cancel")){
                System.out.println("================");
                return;
            }
            for (Student student : course.studentList){
                if (student.studentId.contentEquals(studentID)){
                    course.studentList.remove(student);
                    course.courseCurrentStudents --;
                    student.studentGeneralCourses.remove(course);
                    student.credits -= course.credits;
                    student.generalCredits -= course.credits;

                    StudentInterface.removeStudentCourseFromFile(student);


                    System.out.println("student " + studentID + " was successfully removed from " + course.courseName);
                    System.out.println("================");
                    return;
                }
            } System.out.println("The student was not registered in the course. try another again.");
            System.out.println("================");
        }
    }

    private static void removeStudentFromSpecializedCourse(SpecializedCourse course) throws IOException {
        while (true) {
            System.out.println("Enter a studentID to remove from the selected course");
            String studentID = scanner.nextLine();
            if (studentID.contentEquals("cancel")){
                System.out.println("================");
                return;
            }
            for (Student student : course.studentList){
                if (student.studentId.contentEquals(studentID)){
                    course.studentList.remove(student);
                    course.courseCurrentStudents --;
                    student.studentSpecializedCourses.remove(course);
                    student.credits -= course.credits;

                    StudentInterface.removeStudentCourseFromFile(student);



                    System.out.println("student " + studentID + " was successfully removed from " + course.courseName);
                    System.out.println("================");
                    return;
                }
            } System.out.println("The student was not registered in the course. try another again.");
            System.out.println("================");
        }
    }



    private static void addRemoveSpecialized(SpecializedCourse i) throws IOException {
        System.out.println("Enter '1' to Add a student to the selected course ");
        System.out.println("Enter '2' to Remove a student from the selected course");
        System.out.println("Enter \"back\" to return");
        String option = scanner.nextLine();
        while (!option.contentEquals("1") && !option.contentEquals("2") && !option.contentEquals("back")){
            System.out.println("Your input is not valid. Try again:");
            System.out.println("================");
            System.out.println("Enter '1' to Add a student to the selected course ");
            System.out.println("Enter '2' to Remove a student from the selected course");
            System.out.println("Enter \"back\" to return");
            option = scanner.nextLine();
        } if (option.contentEquals("1")){
            addStudentToSpecializedCourse(i);
        } else if ((option.contentEquals("2"))){
            removeStudentFromSpecializedCourse(i);
        }
    }

    private static void addRemoveGeneral(GeneralCourse i) throws IOException {
        System.out.println("Enter '1' to Add a student to the selected course ");
        System.out.println("Enter '2' to Remove a student from the selected course");
        System.out.println("Enter \"back\" to return");
        String option = scanner.nextLine();
        while (!option.contentEquals("1") && !option.contentEquals("2") && !option.contentEquals("back")){
            System.out.println("Your input is not valid. Try again:");
            option = scanner.nextLine();
        } if (option.contentEquals("1")){
            addStudentToGeneralCourse(i);
        } else if ((option.contentEquals("2"))){
            removeStudentFromGeneralCourse(i);

        }
    }

}


