package io.github.talentdevelopment004.filesdemo;



import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static java.nio.file.Files.readAllLines;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws IOException {
//        externalizedConfigurations();


         Path largeFile = Paths.get("log.txt");
         var lines = Files.lines(largeFile);
         lines.forEach(System.out::println);

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("/src/main/resources/ATM_en.properties");
        prop.load(fis);
        System.out.println(prop.getProperty("welcome1"));
        System.out.println(prop.getProperty("welcome2", "Test"));//line n1
        System.out.println(prop.getProperty("welcome3"));


//        customLoggingToFile();
//        Path filePath = Paths.get("log.txt");
//        List<String> lines = readAllLines(filePath);
//        lines.forEach(line -> {System.out.println(line);});
//        lines.forEach(System.out::println);
//        lines.forEach(line->{
//            String[] split = line.split("\\|");
//            LocalDateTime date = LocalDateTime.parse(split[0].trim());
//            String method = split[1].trim();
//            String level = split[2].trim();
//            String message = split[3].trim();
//            Data data = new Data(date,method,level,message);
//            System.out.println(data.getMessage());
//
//
//        });


    }
    static class Data{
        private LocalDateTime date;
        private String method;
        private String level;
        private String message;

        public Data(LocalDateTime date, String method, String level, String message) {
            this.date = date;
            this.method = method;
            this.level = level;
            this.message = message;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "date=" + date +
                    ", method='" + method + '\'' +
                    ", level='" + level + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    private static void customLoggingToFile() throws IOException {
        FileHandler fileHandler = new FileHandler("log.txt", false);
        CustomFormatter formatter = new CustomFormatter();
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(formatter);
        logger.info("This my info message");
        logger.severe("This my error message");
        logger.warning("This my warning message");
    }

    private static void externalizedConfigurations() throws IOException {
        Path filePath = Paths.get("config.txt");
        List<String> lines = readAllLines(filePath);
        Config config = new Config();
        config.setUsername(lines.get(0).trim());
        config.setPassword(lines.get(1).trim());
        config.setDbHost(lines.get(2).trim());
        config.setDbPort(lines.get(3).trim());
        config.setDbName(lines.get(4).trim());
        System.out.println(config);
        String query = "select * from students";
        try (Connection connection = getMySQLConnection(config);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getString(4));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getMySQLConnection(Config config) throws ClassNotFoundException, SQLException {
        // load className
        Class.forName("com.mysql.cj.jdbc.Driver");
        // register the driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        //establish a connection
        String dbUrl = "jdbc:mysql://"+config.getDbHost()+":"+config.getDbPort()+"/"+config.getDbName();
        return DriverManager.getConnection(dbUrl,config.getUsername(),config.getPassword());
    }

}
