import java.sql.*;
import java.util.Scanner;

public class AlumniDatabase {
    private Connection connection;
    private Statement statement;

    public AlumniDatabase() {
        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/alumni_database", "username", "password");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAlumniTable() {
        try {
            // Create the Alumni table if it doesn't exist
            String query = "CREATE TABLE IF NOT EXISTS alumni (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50)," +
                    "batch VARCHAR(10)," +
                    "branch VARCHAR(50)," +
                    "email VARCHAR(100)," +
                    "phone VARCHAR(15)" +
                    ")";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAlumni(String name, String batch, String branch, String email, String phone) {
        try {
            // Insert a new alumni record into the table
            String query = "INSERT INTO alumni (name, batch, branch, email, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, batch);
            preparedStatement.setString(3, branch);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);
            preparedStatement.executeUpdate();
            System.out.println("Alumni record inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAlumni() {
        try {
            // Retrieve all alumni records from the table
            String query = "SELECT * FROM alumni";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Alumni Records:");
            System.out.println("ID\tName\tBatch\tBranch\tEmail\tPhone");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String batch = resultSet.getString("batch");
                String branch = resultSet.getString("branch");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                System.out.println(id + "\t" + name + "\t" + batch + "\t" + branch + "\t" + email + "\t" + phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AlumniDatabase alumniDatabase = new AlumniDatabase();
        alumniDatabase.createAlumniTable();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Alumni Database Menu:");
            System.out.println("1. Add Alumni");
            System.out.println("2. Display Alumni");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Batch: ");
                    String batch = scanner.nextLine();
                    System.out.print("Enter Branch: ");
                    String branch = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();

                    alumniDatabase.insertAlumni(name, batch, branch, email, phone);
                    break;
                case 2:
                    alumniDatabase.displayAlumni();
					break;
				case 3:
					System.out.println("Exiting...");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
}
}
}