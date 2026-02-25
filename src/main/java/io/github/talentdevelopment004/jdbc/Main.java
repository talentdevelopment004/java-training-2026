package io.github.talentdevelopment004.jdbc;


import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = getPostgresSQLConnection()) {
            // CRUD
//        insert(connection); // create
            select(connection);
            update(connection);
            delete(connection);
            System.out.println("======= Confirmation======================");
            select(connection);

        } catch (SQLException  e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }


    }

    private static void delete(Connection connection) throws SQLException {
        String sql = "delete from students where registration_number=?";
        try(
                //statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, "001");
            preparedStatement.executeUpdate();
        }
    }

    private static void update(Connection connection) throws SQLException {
        String sql = "update students set first_name=? where registration_number=?";
        try(
                //statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, "WesMut");
            preparedStatement.setString(2, "002");
            preparedStatement.executeUpdate();

        }
    }

    private static void insert(Connection insertionConnection) throws SQLException {
        try(Scanner scanner = new Scanner(System.in)){
            System.out.print("Enter your registration number: ");
            String registrationNumber = scanner.nextLine();
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            Student student = new Student(registrationNumber, firstName, lastName, email, password);
            databaseInsertion(insertionConnection,student);
        }
    }

    private static void databaseInsertion(Connection connection,Student student) throws SQLException {
        String sql = "insert into students(registration_number,first_name,last_name,email,password) values ( ?,?,?,?,?)";

        try(
                //statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, student.getRegistrationNumber());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getPassword());
            boolean execution = preparedStatement.execute();
            System.out.println(execution);

        }
    }

    private static void select(Connection connection) throws SQLException {
        // query
        String query = "select * from students";
        try(

                // create a statement
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                ){
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String registrationNumber = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String email = resultSet.getString(5);
                String password = resultSet.getString(6);
                Student student = new Student(id, registrationNumber, firstName, lastName, email, password);
                System.out.println(student);
            }

        }
    }

    private static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
//        "com.mysql.cj.jdbc.Driver"//
        // load className
//        n1
        // register the driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        //establish a connection
        return DriverManager.getConnection(Config.MYSQL_DB_URL, Config.MYSQL_DB_USER, Config.MYSQL_DB_PASSWORD);
    }

    private static Connection getPostgresSQLConnection() throws SQLException, ClassNotFoundException {
        // load className
        Class.forName("org.postgresql.Driver");
        // register the driver
        DriverManager.registerDriver(new org.postgresql.Driver());
        //establish a connection
        return DriverManager.getConnection(Config.POSTGRES_DB_URL, Config.POSTGRES_DB_USER, Config.POSTGRES_DB_PASSWORD);
    }

}
