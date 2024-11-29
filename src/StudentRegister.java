import java.io.*;
import java.util.Scanner;

public interface StudentRegister {
    Scanner scanner = new Scanner(System.in);

    static void studentRegister() throws IOException {
        System.out.println("please enter your student ID:");
        String studentId = scanner.nextLine();
        if (studentId.contentEquals("cancel")){
            System.out.println("================");
            return;
        }
        for (Student student : Student.studentList){
            if (studentId.contentEquals(student.studentId)){
                System.out.println("this studentID is already registered.");
                System.out.println("================");
                return;
            }
        }
        System.out.println("please enter password:");
        String password = scanner.nextLine();
        if (password.contentEquals("cancel")){
            System.out.println("================");
            return;
        }

        Student.studentList.add(new Student(studentId, password));

//        file handling
        writeToFile(studentId, password);
        File file = new File(studentId+".txt");
        file.createNewFile();

//        end file handling

        System.out.println("Student "+studentId+" was created successfully.");
        System.out.println("================");
    }


    // file handling
    static void writeToFile(String id, String pass) {

        File file = new File("usernames.txt");


        try {

            FileWriter myWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(myWriter);


            writer.append(id).append("|").append(pass).append("\n");
            writer.close();

        } catch (FileNotFoundException e){
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //  end of file handling

}
