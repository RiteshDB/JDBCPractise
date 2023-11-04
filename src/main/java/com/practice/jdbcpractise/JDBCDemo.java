package com.practice.jdbcpractise;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Component
public class JDBCDemo{
    public void simpleMethod() throws SQLException, IOException {
        //System.out.println("This is inside the JDBC demo class");

        Properties properties = new Properties();
        InputStream in = new FileInputStream("");
        properties.load(in);
        in.close();


        //step 3: making the connection using DriverManager.getConnection() method from java.sql.DriverManager class
        Connection connection = DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("username"),properties.getProperty("password"));

        if(connection != null){
            System.out.println("Connection is successful");
        }
        //1. Select statement execution
            //selectStatment(connection);

        //2. Creating a table from Java
            //createTable(connection);

        //3. Inserting & updating and deleting functionality.
            //dmlfunctions(connection);

        //4. usage of prepared Statement.
            //dynamicValues(connection, "Ritu", "Bhandari", "Dattatraya", 1234, "ritu@gmail.com");

        //5. usage of callableStatement to execute Stored procedures.
            executeStoreProcedure(connection);
    }

    //implimenting callableStatement to access stored procedure.
    public static void executeStoreProcedure(Connection connection) throws SQLException {
        CallableStatement cstmt = connection.prepareCall("{call getBasicDetails()}");
        ResultSet resultSet = cstmt.executeQuery();
        while(resultSet.next()){
            System.out.println("Id is "+ resultSet.getInt("id")+"Name is "+resultSet.getString("FirstName")
            + resultSet.getString("LastName")+ " mail id is "+ resultSet.getString("emialId"));
        }
    }


    //Usage of Prepared statement.
    public static void dynamicValues(Connection connection, String firstName, String lastName, String middlename, int num, String mail) throws SQLException {
        String insertQuery = "insert into personalInfo (FirstName, LastName, MiddleName, phoneNo, emialId)\n" +
                "values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, middlename);
        preparedStatement.setInt(4, num);
        preparedStatement.setString(5, mail);
        ResultSet result = preparedStatement.executeQuery();

        System.out.println(result);

    }

    //Method to insert, update or delete the entry.
    public static void dmlfunctions(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String insertQuery = "insert into personalInfo (FirstName, LastName, MiddleName, phoneNo, emialId)\n" +
                "values ('Dattatraya', 'Bhandari', 'Narayan', 86265 , 'dattuBhandari@gmail.com')";

        ResultSet resultSet = statement.executeQuery(insertQuery);
        ResultSetMetaData rm = resultSet.getMetaData();


        System.out.println("Number of rows impacted" + rm.getColumnCount());

    }

    //Method used to get the data's from the table.
    public static void selectStatment(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        // Select query using the JDBC.
        String sqlQuery = "SELECT TOP (1000) [id]\n" +
                "      ,[FirstName]\n" +
                "      ,[LastName]\n" +
                "      ,[MiddleName]\n" +
                "      ,[phoneNo]\n" +
                "      ,[emialId]\n" +
                "  FROM [TestingDB].[dbo].[personalInfo]\n";

        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while(resultSet.next() == true) {
            System.out.print("id is " + resultSet.getInt("id"));
            System.out.print(" Full name is " + resultSet.getString("FirstName") + resultSet.getString("MiddleName") != null ? resultSet.getString("MiddleName") : "" + resultSet.getString("LastName"));
            System.out.print(" Phone number is " + resultSet.getInt("phoneNo"));
            System.out.print(" and email id is " + resultSet.getString("emialId"));
        }




    }
    //Method used to create a table.
    public static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createQuery = "Create Procedure getBasicDetails \n" +
                "as\n" +
                "Select * from personalinfo \n" +
                "GO;";

        statement.execute(createQuery);

        System.out.println("Stored procedure got created successfully");
    }
}

