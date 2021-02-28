package edu.wpi.cs3733.teamO.Database;

import edu.wpi.cs3733.teamO.UserTypes.Admin;
import edu.wpi.cs3733.teamO.UserTypes.Staff;
import edu.wpi.cs3733.teamO.UserTypes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.Encoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandling {

    private static String username;

    /**
     * Create new account and add info to the database
     *
     * @param username
     * @param password
     * @param email
     * @param fName
     * @param lName
     */
    public static void createAccount(String username, String password, String email, String fName, String lName) {
        //TODO encode the password in iteration 3 (no time!)
        String encodedPass = password;

        String query =
                "INSERT INTO Edges VALUES("
                        + "'"
                        + username
                        + "'"
                        + ", "
                        + "'"
                        +  encodedPass
                        + "'"
                        + ", "
                        + "'"
                        + email
                        + "', "
                        + "'"
                        + fName
                        + "', "
                        + "'"
                        + lName
                        + "'"
                        + ")";

        System.out.println("CREATE ACCT QUERY: " + query);
        try {
            PreparedStatement preparedStmt = null;
            preparedStmt = DatabaseConnection.getConnection().prepareStatement(query);

            preparedStmt.execute();
            preparedStmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    /**
     * Logs in the user utilizing DB
     * @param username
     * @param password
     * @return null if login is bad, User with all info from DB if good
     */
    public static User login(String username, String password) {
        setUsername(username);
        User p = new User();

        String query = "SELECT * FROM Users WHERE username = '" + username + "' AND password = " + password;

        try {
            PreparedStatement pstmt = null;
            pstmt = DatabaseConnection.getConnection().prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            // Add props to user
            p.setUsername(rset.getString("username"));
            p.setPassword(rset.getString("password"));
            p.setEmail(rset.getString("email"));
            p.setFirstName(rset.getString("fName"));
            p.setLastName(rset.getString("lName"));

            rset.close();
            pstmt.close();

            return p;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Login Credentials Wrong. Fail.");
            return null;
        }
    }

    /**
     * @return
     */
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String u) {
        username = u;
    }
}
