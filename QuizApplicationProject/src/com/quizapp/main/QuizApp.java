package com.quizzapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.db.DBConnection;



import com.db.*;
import com.module.*;


import java.sql.*;
import java.util.Scanner;

public class QuizApp {
    static Scanner sc = new Scanner(System.in);
    static int currentStudentId = -1;

    public static void main(String[] args) throws Exception {
    	System.out.println("----------------------------------");
    	System.out.println("Quiz Application ");
    	System.out.println("-----------------------------------");
        while (true) {
        	
            System.out.println("1. User Registration\n2. User Login\n3. Admin Login\n4. Take quizz\n5. Display My Result\n6. Exit");
            System.out.println("Enter Your Choice>>");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    adminLogin();  // Admin login choice
                    break;
                case 4:
                	
                    if (currentStudentId != -1) {
                    	 System.out.println("__Quizz__");
                        takeQuiz(); // Start the quiz if logged in as a student
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 5:
                   DataBaseOperation.displayResult();  // Call displayResult to show the student's score
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public static void registerUser() throws Exception {
    	System.out.println("____User Registration____");
        System.out.println("Enter First Name:");
        String fname = sc.nextLine();
        System.out.println("Enter Last Name:");
        String lname = sc.nextLine();
        System.out.println("Enter Username:");
        String uname = sc.nextLine();
        System.out.println("Enter Password:");
        String pass = sc.nextLine();
        System.out.println("Enter City:");
        String city = sc.nextLine();
        System.out.println("Enter Email:");
        String email = sc.nextLine();
        System.out.println("Enter Mobile:");
        String mobile = sc.nextLine();

        Student student = new Student(fname, lname, uname, pass, city, email, mobile);
        DataBaseOperation.insertStudent(student);
        System.out.println("Registration Successful");
    }

    static void loginUser() throws Exception {
            System.out.println("__Login__");
        System.out.println("Enter Username:");
        String uname = sc.nextLine();
        System.out.println("Enter Password:");
        String pass = sc.nextLine();

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT id FROM students WHERE username=? AND password=?");
        ps.setString(1, uname);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            currentStudentId = rs.getInt(1);
            System.out.println("Login Successful");
        } else {
            System.out.println("Invalid credentials");
        }
    }

    private static void takeQuiz() throws SQLException {
       
        Connection con;
		try {
			con = DBConnection.getConnection();
			String query = "SELECT * FROM questions";
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        int score = 0;
	        int questionNumber = 1;

	        while (rs.next()) {
	            System.out.println("Question " + questionNumber + ": " + rs.getString("question_text"));
	            System.out.println("1. " + rs.getString("option1"));
	            System.out.println("2. " + rs.getString("option2"));
	            System.out.println("3. " + rs.getString("option3"));
	            System.out.println("4. " + rs.getString("option4"));
	            System.out.print("Enter your answer (1-4): ");
	            int answer = sc.nextInt();

	            if (answer == rs.getInt("correct_answer")) {
	                score++;
	            }
	            questionNumber++;
	        }
	        
	        DataBaseOperation.insertResult(currentStudentId, score);
	        System.out.println("Your score: " + score);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
   
    
    
  
    static void adminLogin() throws Exception {
    	System.out.println("__Admin Login__");
        System.out.println("Enter Admin Username:");
        String adminUsername = sc.nextLine();
        System.out.println("Enter Admin Password:");
        String adminPassword = sc.nextLine();

        Admin admin = new Admin();
        if (admin.authenticate(adminUsername, adminPassword)) {
            System.out.println("Admin Login Successful!");
            adminMenu(); // Show the admin menu after successful login
        } else {
            System.out.println("Invalid Admin credentials!");
        }
    }

    static void adminMenu() throws Exception {
        while (true) {
        	
            System.out.println("_________Admin Menu:_________\n1. Display All Scores\n2. Fetch Score by ID\n3. Add Question\n4. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                	DataBaseOperation.displayAllScores(); // Display all student scores
                    break;
                case 2:
                	DataBaseOperation.fetchScoreById(); // Fetch score for a particular student by ID
                    break;
                case 3:
                    DataBaseOperation.addQuestion(); // Add a new question to the quiz
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return; // Logout and return to main menu
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

   

    
    
}
