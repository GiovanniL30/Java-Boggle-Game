package Server_Java.dataBase;

import App.Lobby;
import App.User;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import shared.utilities.UtilityMethods;
import shared.referenceClasses.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Database {

    private static Connection connection;


    private static void openConnection() {
        if (connection != null) return;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newschema1?user=root&password=");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static synchronized User[] getPlayers() {

        openConnection();

        LinkedList<User> users = new LinkedList<>();
        String query = "SELECT * FROM users WHERE totalScore != 0 ORDER BY 6 desc";

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                users.add(getUser(resultSet));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        return users.toArray(new User[0]);
    }


    public static synchronized void updatePlayerScores(HashMap<String, Integer> playerScores) {

        openConnection();

        String query = "UPDATE users SET totalScore = ? WHERE (userID = ?)";

        playerScores.forEach((playerID, playerAddedScore) -> {

            int newScore = getPlayerScore(playerID) + playerAddedScore;

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, newScore );
                preparedStatement.setString(2, playerID);
                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        });

    }

    private static synchronized int getPlayerScore(String playerId) {

        String query  = "SELECT totalScore FROM users WHERE userID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        return 0;
    }

    public static synchronized User[] lobbyPlayers(String lobbyId) {
        openConnection();

        String query = "SELECT users.* FROM lobbyplayers INNER JOIN users ON users.userID = lobbyplayers.playerID WHERE lobbyplayers.lobbyID = ?";
        LinkedList<User> players = new LinkedList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                players.add(getUser(resultSet));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return players.toArray(new User[0]);
    }

    public static synchronized LinkedList<String> getWords() {

        openConnection();

        LinkedList<String> words = new LinkedList<>();

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM wordlist");

            while (resultSet.next()) {
                words.add(resultSet.getString(2));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return words;
    }

    public static synchronized void setTopPlayer(String userID, int score,  String lobbyID) {
        openConnection();

        String query = "UPDATE lobby SET topPlayerID = ?, topPlayerScore = ? WHERE (lobbyID = ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            preparedStatement.setInt(2, score);
            preparedStatement.setString(3, lobbyID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    public static synchronized Response<String> createNewLobby(String creatorId) {

        openConnection();
        String query = "INSERT INTO lobby (lobbyID, lobbyStatus, dateTimeCreated, topPlayerID) VALUES (?, ?, ?, ?)";

        try {
            String lobbyID = UtilityMethods.generateRandomID().substring(0, 5);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyID);
            preparedStatement.setString(2, "Waiting");
            preparedStatement.setString(3, UtilityMethods.getCurrentDateTime());
            preparedStatement.setString(4, null);

            if(preparedStatement.executeUpdate() > 0) {
               addPlayer(creatorId, lobbyID);
               return new Response<>(lobbyID, true);
            }else {
                return new Response<>(lobbyID, false);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Response<>("", false);
    }

    public static synchronized boolean addPlayer(String playerId, String lobbyId) {

        openConnection();
        String query = "INSERT INTO lobbyplayers (lobbyID, playerID) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            preparedStatement.setString(2, playerId);
            boolean res = preparedStatement.executeUpdate() >  0;
            updateIsPlaying(playerId, res);
            return res;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public static synchronized void deleteLobby(String lobbyId) {

        openConnection();

        User[] players = lobbyPlayers(lobbyId);

        for (User player : players) {
            removePlayer(player.userID ,lobbyId);
        }


        String query = "DELETE FROM lobby WHERE lobbyStatus = 'Waiting' AND lobbyID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public static synchronized void updateIsPlaying(String playerId, boolean p) {

        int i = p ? 1 : 0;

        openConnection();
        String query = "UPDATE users SET isPlaying = ? WHERE (userID = ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2, playerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public static synchronized void finishedGame(String lobbyId) {

        openConnection();

        String query = "UPDATE lobby SET  lobbyStatus = 'Finished' WHERE lobbyID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static synchronized boolean isAccountBanned(String userId) {

        openConnection();
        String query = "SELECT isBanned FROM users WHERE userID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
               return resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
    public static synchronized boolean isAccountPlaying(String userId) {

        openConnection();
        String query = "SELECT isPlaying FROM users WHERE userID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public static synchronized void startGame(String lobbyId) {
        openConnection();

        if(isLobbyStarted(lobbyId)) return; // don't need to set started again

        String query  = "UPDATE lobby SET lobbyStatus='Started' WHERE lobbyID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public static synchronized boolean isLobbyStarted(String lobbyId) {
        openConnection();

        String query = "SELECT lobbyStatus FROM lobby WHERE lobbyID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return !resultSet.getString(1).equals("Waiting");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

    public static synchronized boolean lobbyExist(String lobbyId) {
        openConnection();

        String query = "SELECT count(*) FROM lobby WHERE lobbyID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lobbyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public static synchronized int[] getTime(){

        openConnection();

        LinkedList<Integer> time = new LinkedList<>();
        String query = "SELECT length FROM time";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                time.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int[] timeArray = new int[time.size()];
        for (int i = 0; i < time.size(); i++) {
            timeArray[i] = time.get(i);
        }

        return timeArray;
    }

    public static synchronized int getGameTime() {

        openConnection();

        String query = "SELECT time.length FROM time INNER JOIN gamesettings ON gamesettings.gameTime = time.timeID";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            if (resultSet.next()) {
              return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return 0;
    }

    public static synchronized int getWaitingTime() {

        openConnection();

        String query = "SELECT time.length FROM time INNER JOIN gamesettings ON gamesettings.waitingTime = time.timeID";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return 0;
    }

    public static synchronized void updateGameTime(int length) {

        openConnection();

        String query = "UPDATE gamesettings SET gameTime = (SELECT timeID FROM time WHERE length = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, length);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static synchronized void updateWaitingTime(int length) {

        openConnection();

        String query = "UPDATE gamesettings SET waitingTime = (SELECT timeID FROM time WHERE timeID = (SELECT timeID FROM time WHERE length = ?))";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, length);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static synchronized App.Response deleteUser(String userId) {

        Any any = ORB.init().create_any();
        openConnection();

        if (isAccountPlaying(userId)){
            any.insert_string("Cannot delete account. Account is still playing");
            return new App.Response(any, false);
        }

        String query = "DELETE FROM users WHERE userID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);

            if(preparedStatement.executeUpdate() > 0) {
                any.insert_string("Account is Deleted successfully");
                return new App.Response(any, true);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        any.insert_string("Failed to Delete account");
        return new App.Response(any, false);
    }

    public static synchronized boolean removePlayer( String playerId, String lobbyId) {
        openConnection();

        String query = "DELETE FROM lobbyplayers WHERE playerID = ? AND lobbyID = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerId);
            preparedStatement.setString(2, lobbyId);

            if(preparedStatement.executeUpdate() > 0) {
                if(lobbyPlayers(lobbyId).length == 0) { //delete the lobby if there are no players already
                    deleteLobby(lobbyId);
                }

                updateIsPlaying(playerId, false);
                return true;
            }else  return false;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static synchronized Response<String> createAccount(User user) {

        openConnection();

        try {
            PreparedStatement checkUserName = connection.prepareStatement("SELECT count(*) FROM users WHERE username = ?");
            checkUserName.setString(1, user.userName);
            ResultSet resultSet = checkUserName.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) != 0) {
                    return new Response<>("User name already exist", false);
                }
            }

            PreparedStatement newUser = connection.prepareStatement("INSERT INTO users (userId, firstName, lastName, username, password, totalScore, isBanned, isPlaying) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            newUser.setString(1, user.userID);
            newUser.setString(2, user.firstName);
            newUser.setString(3, user.lastName);
            newUser.setString(4, user.userName);
            newUser.setString(5, user.password);
            newUser.setInt(6, 0);
            newUser.setInt(7, 0);
            newUser.setInt(8, 0);
            if (newUser.executeUpdate() > 0) {
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

            if (resultSet.next()) {
                User user = getUser(resultSet);

                if(isAccountBanned(user.userID)) {
                    return new Response<>(new User("Your account is banned", "", "", "", "", 0), false);
                }

                return new Response<>(user, true) ;
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new Response<>(new User("Invalid Login Credentials", "", "", "", "", 0), false);
    }

    public static User getUser(String id) {

        openConnection();

        String query = "SELECT * FROM users WHERE userID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return  getUser(resultSet);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new User();
    }



    private static User getUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
    }

    public static synchronized App.Response banUser(String userID) {

        Any any = ORB.init().create_any();
        openConnection();


        if (isAccountPlaying(userID)){
            any.insert_string("Player is currently playing, cannot ban");
            return new App.Response(any, false);
        }

        String query = "UPDATE users SET isBanned = 1 WHERE userID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);

            if(preparedStatement.executeUpdate() > 0) {
                any.insert_string("Successfully banned player");
                return new App.Response(any, true);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        any.insert_string("Failed to ban user");
        return new App.Response(any, false);
    }

    public static synchronized App.Response unBanUser(String userID) {
        openConnection();

        Any any = ORB.init().create_any();
        String query = "UPDATE users SET isBanned = 0 WHERE userID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);

            if(preparedStatement.executeUpdate() > 0) {
                any.insert_string("Successfully unBanned player");
                return new App.Response(any, true);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        any.insert_string("Failed to unban user");
        return new App.Response(any, false);
    }
}
