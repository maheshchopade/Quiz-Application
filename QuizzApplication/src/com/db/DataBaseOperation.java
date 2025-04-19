package com.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.module.Question;
import com.module.Student;

public class DataBaseOperation {
	static Scanner sc = new Scanner(System.in);
	public static void insertStudent(Student student) throws SQLException {
		

		try {
			Connection con = DBConnection.getConnection();
			String query = "INSERT INTO students (first_name, last_name, username, password, city, email, mobile) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getLastName());
			ps.setString(3, student.getUsername());
			ps.setString(4, student.getPassword());
			ps.setString(5, student.getCity());
			ps.setString(6, student.getEmail());
			ps.setString(7, student.getMobile());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Method to update student information
	public static void updateStudent(Student student) throws SQLException {
		Connection con;
		try {
			con = DBConnection.getConnection();
			String query = "UPDATE students SET first_name = ?, last_name = ?, username = ?, password = ?, city = ?, email = ?, mobile = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getLastName());
			ps.setString(3, student.getUsername());
			ps.setString(4, student.getPassword());
			ps.setString(5, student.getCity());
			ps.setString(6, student.getEmail());
			ps.setString(7, student.getMobile());
			ps.setInt(8, student.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 //Method to insert new question
     public static void insertQuestion(Question question) throws SQLException {
		Connection con;
	try {
			con = DBConnection.getConnection();
			String query = "INSERT INTO questions (question_text, option1, option2, option3, option4, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, question.getQuestion());
			ps.setString(2, question.getOption1());
			ps.setString(3, question.getOption2());
		ps.setString(4, question.getOption3());
			ps.setString(5, question.getOption4());
			ps.setInt(6, question.getCorrectAnswer());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}

}

	public static void insertResult(int studentId, int score) throws SQLException {
		Connection con;
		try {
			con = DBConnection.getConnection();
			String query = "INSERT INTO results (id, score) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, studentId);
			ps.setInt(2, score);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Method to fetch score by student ID
	public static int getScoreByStudentId(int studentId) throws SQLException {
		Connection con;
		try {
			con = DBConnection.getConnection();
			String query = "SELECT score FROM results WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("score");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	public static void storeResult(int studentId, int score) throws SQLException {
		// Establish connection to the database
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBConnection.getConnection();
			String query = "INSERT INTO results (student_id, score) VALUES (?, ?)";
			ps = con.prepareStatement(query);
			ps.setInt(1, studentId);
			ps.setInt(2, score);
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Result stored successfully.");
			} else {
				System.out.println("Failed to store result.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ps.close();
		con.close();
	}
	public static void displayResult() throws SQLException {
	    System.out.print("Enter Username: ");
	    String username = sc.nextLine();
	    
	    System.out.print("Enter Password: ");
	    String password = sc.nextLine();

	    Connection con;
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT id FROM students WHERE username = ? AND password = ?");
		    ps.setString(1, username);
		    ps.setString(2, password);

		    ResultSet rs = ps.executeQuery();

		    if (rs.next()) {
		        int studentId = rs.getInt("id");

		        PreparedStatement ps2 = con.prepareStatement("SELECT score FROM results WHERE id = ?");
		        ps2.setInt(1, studentId);
		        ResultSet rs2 = ps2.executeQuery();

		        if (rs2.next()) {
		            int score = rs2.getInt("score");
		            System.out.println("Your score is: " + score);
		        } else {
		            System.out.println("No result found for this student.");
		        }
		    } else {
		        System.out.println("Invalid username or password.");
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	
	//Admin Operations
	
	public static void addQuestion() throws Exception {
        System.out.println("Enter the question:");
        String question = sc.nextLine();
        System.out.println("Enter option 1:");
        String option1 = sc.nextLine();
        System.out.println("Enter option 2:");
        String option2 = sc.nextLine();
        System.out.println("Enter option 3:");
        String option3 = sc.nextLine();
        System.out.println("Enter option 4:");
        String option4 = sc.nextLine();
        System.out.println("Enter correct answer number (1-4):");
        int correctAnswer = sc.nextInt();
        sc.nextLine();  // Consume newline character

        Question newQuestion = new Question(question, option1, option2, option3, option4, correctAnswer);
        insertQuestion(newQuestion);  // You would need to uncomment and implement this method in DataBaseOperation
        System.out.println("Question added successfully!");
    }
	
	
	
	// Method to fetch score by student ID
    public static void fetchScoreById() throws Exception {
        System.out.println("Enter student id:");
        int id = sc.nextInt();
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT score FROM results WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("Score is: " + rs.getInt(1));
        } else {
            System.out.println("No score found for given ID.");
        }
    }

    
    // Method to display all student scores
    public static void displayAllScores() throws Exception {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT s.first_name, s.last_name, r.score FROM students s JOIN results r ON s.id = r.id ORDER BY r.score ASC");
        while (rs.next()) {
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " - Score: " + rs.getInt(3));
        }
    }
    
	

}
