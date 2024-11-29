
import java.io.*;
import java.util.Scanner;


public class Main{

    public static void commandLineInterface() throws IOException {
        System.out.println("Welcome the Main Menu!");
        while (true) {
            System.out.println("You can cancel any operation by \"cancel\" command.");
            System.out.println("Enter '1' to login as an admin.");
            System.out.println("Enter '2' to login as student.");
            System.out.println("Enter '3' to sign up a new student.");
            System.out.println("Enter \"exit\" to terminate the program.");

            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            if(!option.contentEquals("1") && !option.contentEquals("2") && !option.contentEquals("3") &&
                    !option.contentEquals("exit")){
                System.out.println("your option is not valid! please try again.");
                System.out.println("================");

            } if (option.contentEquals("1")){
                AdminInterface.adminLogin();
            } else if (option.contentEquals("2")) {
                StudentInterface.studentLogin();
            } else if (option.contentEquals("3")){
                StudentRegister.studentRegister();
            } else if (option.contentEquals("exit")) {
                break;

            } System.out.println("Welcome back to the Main Menu!");
        }
    }

    public static void main(String[] args) throws IOException {

        try {
            FileReader reader = new FileReader("usernames.txt");
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
                    Student.studentList.add(new Student(userData[0].trim(), userData[1].trim()));
                }
            }

        } catch (FileNotFoundException e){
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Course.readFile();

        for (Student i : Student.studentList){
            Student.readCourses(i);
        }

        commandLineInterface();


    }
}
