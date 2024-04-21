package Server_Java.dataBase;

import App.Lobby;
import App.User;
import shared.referenceClasses.Response;

import java.sql.*;
import java.util.LinkedList;

public class Database {

    private static Connection connection;


    private static void openConnection(){
        if(connection != null) return;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/boggled?user=root&password=password");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }


    public static synchronized Response<String> createAccount(User user) {

        openConnection();


        try {
            PreparedStatement checkUserName = connection.prepareStatement("SELECT count(*) FROM users WHERE username = ?");
            checkUserName.setString(1, user.userName);
            ResultSet resultSet = checkUserName.executeQuery();
            if(resultSet.next()) {
                if(resultSet.getInt(1) != 0) {
                    return new Response<>("User name already exist", false);
                }
            }

            PreparedStatement newUser = connection.prepareStatement("INSERT INTO users (userId, firstName, lastName, username, password, isLoggedIn, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            newUser.setString(1, user.userID);
            newUser.setString(2, user.firstName);
            newUser.setString(3, user.lastName);
            newUser.setString(4, user.userName);
            newUser.setString(5, user.password);
            newUser.setInt(6, 0);
            newUser.setString(7, "Offline");
            if(newUser.executeUpdate() > 0) {
                 return new Response<>("Created Account Successfully", true);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Response<>("Failed to create the account", false);
    }
    public static synchronized LinkedList<Lobby> getLobbies() {
        return new LinkedList<>();
    }

    public static Response<User> logIn(String userName, String password) {
        openConnection();

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = getUser(resultSet);

                if(user.isLoggedIn) {
                    return new Response<>(new User("Your account is already logged in on another machine","","","", "", false, ""), false);
                }else {
                    PreparedStatement updateOnlineStatus = connection.prepareStatement("UPDATE users SET status ='Online', isLoggedIn = '1' WHERE userID = ?");
                    System.out.println(user.userID);
                    updateOnlineStatus.setString(1, user.userID);

                    if(updateOnlineStatus.executeUpdate() > 0) {
                        return new Response<>(getUser(resultSet), true);
                    }else {
                        return new Response<>(new User("Having an error logging you in","","","", "", false, ""), false);
                    }

                }

            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Response<>(new User("Invalid Login Credentials","","","", "", false, ""), false);
    }

    public static void logout(String userId) {
        openConnection();
        try {
            PreparedStatement updateOnlineStatus = connection.prepareStatement("UPDATE users SET status ='Offline', isLoggedIn = '0' WHERE userID = ?");
            updateOnlineStatus.setString(1, userId);
            updateOnlineStatus.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static User getUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6) == 1, resultSet.getString(7));
    }

}
