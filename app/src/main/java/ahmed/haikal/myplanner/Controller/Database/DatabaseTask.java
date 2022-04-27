package ahmed.haikal.myplanner.Controller.Database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.cj.protocol.Resultset;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ahmed.haikal.myplanner.View.Main_Screen.MainScreenActivity;
import ahmed.haikal.myplanner.View.Sign_In_Or_Up.LogInFragment;
import ahmed.haikal.myplanner.View.Sign_In_Or_Up.SignUpFragment;

public abstract class DatabaseTask extends AsyncTask<Void, Void, Void> {

    protected static DatabaseController databaseController;
    protected String query;
    protected Context context;

    public DatabaseTask(DatabaseController databaseController, String query, Context context) {
        this.databaseController = databaseController;
        this.query = query;
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(Void...progress){

    }

    @Override
    protected void onPostExecute(Void unused) {

    }




    public static class Connect extends DatabaseTask{

        public Connect(DatabaseController databaseController) {
            super(databaseController, "", null);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            if(databaseController.openConnection())
                System.out.println("connection opened");
            return null;
        }
    }

    public static class Login extends DatabaseTask{

        private String username, password;
        private boolean validated = false;
        private int userID;
        private Context context;

        public Login(DatabaseController databaseController, String username, String password, Context context ) {
            super(databaseController, "", context);
            this.username = username;
            this.password = password;
            this.context = context;
            System.out.println("given username: " + username + " and given password: "
             + password);
        }

        public boolean validate(){
            boolean success = false;
            String query = "SELECT * FROM USERS WHERE Username = '" + username + "'";

            try{
                ResultSet resultSet = databaseController.executeResultsQuery(query);
                if(resultSet.next()){
                    String correctUname = resultSet.getString("Username");
                    String correctPassword = resultSet.getString("Password");
                    System.out.println(" Uname: " + correctUname);
                    System.out.println(" password: " + correctPassword);
                    if(password.equals(correctPassword)) {
                        userID = resultSet.getInt("UserID");
                        success = true;
                    }
                    else
                        System.out.println("didn't work");
                }
                databaseController.closeResultSet(resultSet);

            } catch (SQLException e){
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(validate()) {
                LogInFragment.loginSuccess(context, username, userID);
                validated = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            if(!validated)
                LogInFragment.loginFailed();
        }
    }

    public static class Insert extends DatabaseTask {

        private String tableName;
        private ArrayList<String> insertionFields, insertionValues;

        public Insert(DatabaseController databaseController, String tableName, ArrayList<String> fields,
                      ArrayList<String> values, Context context) {
            super(databaseController, "", context);
            this.tableName = tableName;
            insertionFields = fields;
            insertionValues = values;
        }

        public String insertNew(String tableName, ArrayList<String> fields, ArrayList<String> values){

            if(values == null) {
                System.out.println(" no fields");
                return "";
            }

            String query = "INSERT INTO " + "\"" + tableName + "\"";
            if(fields.size() == values.size() && !fields.isEmpty()){
                StringBuilder fieldList = new StringBuilder(" ( ");
                for(int i = 0; i < fields.size(); i++){
                    String field = fields.get(i);
                    if(i != fields.size() - 1)
                        fieldList.append(field).append(", ");
                    else
                        fieldList.append(field);
                }
                fieldList.append(")");
                query += fieldList + " VALUES ";

                StringBuilder valueList = new StringBuilder("( ");
                for(int i = 0; i < values.size(); i++){
                    String value = values.get(i);
                    if(i != values.size() - 1)
                        valueList.append(value).append(", ");
                    else
                        valueList.append(value);
                }
                valueList.append(")");
                query += valueList;
            }
            System.out.println(query);
            return query;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            databaseController.executeUpdateQuery(insertNew(tableName, insertionFields, insertionValues));
            return null;
        }
    }

    public static class CreateUser extends DatabaseTask {

        private ArrayList<String> userFields, userValues;
        private String givenUsername;
        public CreateUser(DatabaseController databaseController, ArrayList<String> userFields,
                          ArrayList<String> uservalues, Context context) {
            super(databaseController, "", context);
            this.userFields = userFields;
            this.userValues = uservalues;
            givenUsername = uservalues.get(3);
        }

        public boolean checkIfUsernameExists(String username){
            String query = "SELECT * FROM USERS WHERE Username = " + username;
            ResultSet resultset = databaseController.executeResultsQuery(query);
            System.out.println("check username query is: " + query);
            try {
                if(resultset.next())
                    return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return false;
        }

        public String createUser(ArrayList<String> fields, ArrayList<String> values){

            String insertionQuery = "INSERT INTO USERS ";

            if(fields.size() == values.size() && !fields.isEmpty()){
                StringBuilder fieldList = new StringBuilder(" ( ");
                for(int i = 0; i < fields.size(); i++){
                    String field = fields.get(i);
                    if(i != fields.size() - 1)
                        fieldList.append(field).append(", ");
                    else
                        fieldList.append(field);
                }
                fieldList.append(")");
                insertionQuery += fieldList + " VALUES ";

                StringBuilder valueList = new StringBuilder("( ");
                for(int i = 0; i < values.size(); i++){
                    String value = values.get(i);
                    if(i != values.size() - 1)
                        valueList.append(value).append(", ");
                    else
                        valueList.append(value);
                }
                valueList.append(")");
                insertionQuery += valueList;
            }

            System.out.println(insertionQuery);
            return insertionQuery;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!checkIfUsernameExists(givenUsername)) {
                databaseController.executeUpdateQuery(createUser(userFields, userValues));
                SignUpFragment.signUpSuccess(context);
            }
            else {
                SignUpFragment.signUpFailure(context);
            }
            return null;
        }
    }

    public static class Retrieve extends DatabaseTask {

        private String tableName, retrievalField, retrievalValue;

        public Retrieve(DatabaseController databaseController, String tableName,
                        String retrievalField, String retrievalValue, Context context) {
            super(databaseController, "", context);
            this.tableName = tableName;
            this.retrievalField = retrievalField;
            this.retrievalValue = retrievalValue;
        }

        public boolean valueExists(String tableName, String retrievalField, String retrievalValue){
            String query = "SELECT * FROM " + tableName + " WHERE " + retrievalField + " = " + retrievalValue;
            ResultSet resultSet = databaseController.executeResultsQuery(query);
            try {
                if(!resultSet.next())
                return false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return true;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    public static class Delete extends DatabaseTask {

        public Delete(DatabaseController databaseController, String query, Context context) {
            super(databaseController, query, context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
