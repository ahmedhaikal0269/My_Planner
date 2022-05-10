package ahmed.haikal.myplanner.Controller.Database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is intended to establish a connection with the database and perform basic operations
 * such as queries, add, update, and delete items from the database
 * The class follows the Singleton design pattern to ensure only one connection is used throughout
 * the whole time the application is active
 */

public class DatabaseController {


    private final String connectionString;
    private final String dbUsername;
    private final String dbPassword;
    private static final DatabaseController databaseController = new DatabaseController(
            "jdbc:jtds:sqlserver://192.168.0.16:1433;databaseName=MyPlanner",
            "sa", "mouserat");

    private Connection connection;
    private Statement statement;

    //Constructor is private as per the Singleton Pattern
    private DatabaseController(String connectionString, String dbUsername, String dbPassword) {
        this.connectionString = connectionString;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    //create only only instance of the databaseController
    public static DatabaseController getInstance() {
        return databaseController;
    }

    //Driver
    public void loadDriver(){
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("driver has been loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("driver wasn't loaded");
            e.printStackTrace();
        }
    }

    //open connection with the database
    public boolean openConnection(){

        try{
            loadDriver();
            connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
            System.out.println("connection opened successfully");
            return true;
        } catch (Exception e){
            System.out.println("couldn't connect");
            e.printStackTrace();
        }
        return false;
    }

    //Query the database for some information and return it if found
    public synchronized ResultSet executeResultsQuery(String query){

        ResultSet resultSet = null;

        try{
            if(!query.isEmpty()){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;

    }

    //update, delete, and add information to the database
    public synchronized int executeUpdateQuery(String query){

        int updatedRows = 0;

        try{
            if(!query.isEmpty()){
                statement = connection.createStatement();
                updatedRows = statement.executeUpdate(query);
                statement.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return updatedRows;

    }

    public synchronized void closeResultSet(ResultSet resultSet){
        if(resultSet != null)
            try{
                resultSet.close();
                statement.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    public void closeConnection(){

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
