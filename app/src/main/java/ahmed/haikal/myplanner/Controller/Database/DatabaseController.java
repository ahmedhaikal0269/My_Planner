package ahmed.haikal.myplanner.Controller.Database;

/**
 * This class is intended to establish a conenction with the database and perform basic operations
 * such as queries, add, update, and delete items from the database
 * The class follows the Singleton design pattern to ensure only one connection is used throughout
 * the whole time the application is active
 */

public class DatabaseController {


    private String connectionString;
    private String dbUsername;
    private String dbPassword;
    private static DatabaseController databaseController = new DatabaseController();
    //Constructor is private as per the Singleton Pattern
    private DatabaseController(){

    }

    public DatabaseController getDatabaseController(){
        return databaseController;
    }
}
