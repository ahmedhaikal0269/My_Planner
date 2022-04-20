package ahmed.haikal.myplanner.Controller.Database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

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

        public Login(DatabaseController databaseController, String username, String password, Context context ) {
            super(databaseController, "", context);
            this.username = username;
            this.password = password;
        }

        public boolean validate(){
            boolean success = false;
            String query = "SELECT Password FROM LOGIN WHERE Username = '" + username + "'";

            try{
                ResultSet resultSet = databaseController.executeResultsQuery(query);
                if(resultSet.next()){
                    String correctPassword = resultSet.getString("Password");
                    if(password.equals(correctPassword))
                        success = true;
                }
                databaseController.closeResultSet(resultSet);

            } catch (SQLException e){
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            validated =  validate();
            return null;
        }

        public void loginFailed(){
            LogInFragment.loginFailed();
        }

        @Override
        protected void onPostExecute(Void unused) {
            if (validated){
                    Intent intent = new Intent(context, MainScreenActivity.class);
                context.startActivity(intent);
            }
            else {
                loginFailed();
            }

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

        //this boolean should check if the the username already exists since it has to be unique
        public boolean getRecord(String retrievalValue){
            return false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            databaseController.executeUpdateQuery(insertNew(tableName, insertionFields, insertionValues));
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
            if(valueExists(tableName, retrievalField, retrievalValue)) {
                SignUpFragment.setUsernameExists(true);
            }
            else {
                SignUpFragment.setUsernameExists(false);
            }
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
