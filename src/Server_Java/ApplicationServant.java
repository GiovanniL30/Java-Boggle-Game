package Server_Java;

import App.*;
import Server_Java.dataBase.Database;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;


import java.util.HashMap;



public class ApplicationServant extends ApplicationServerPOA {

    private final HashMap<String, Controller> controllerHashMap = new HashMap<>();

    @Override
    public User login(String userName, String password, Controller controller) {
        shared.referenceClasses.Response<User> response =  Database.logIn(userName, password);
        User user = response.getData();

       if(response.isSuccess()) {
           controllerHashMap.put(user.userID, controller);
           System.out.println("A new user Logged in total users online: " + controllerHashMap.size() );
       }

        return user;
    }

    @Override
    public void logout(String userID) {
        Database.logout(userID);
        controllerHashMap.remove(userID);
        System.out.println("A user logged out total users online: " + controllerHashMap.size() );
    }

    @Override
    public Response createLobby(User creator) {
        Any responseData = ORB.init().create_any();
        return new Response(responseData, true);
    }

    @Override
    public Response joinLobby(User user, String lobbyId) {
        return null;
    }

    @Override
    public Response leaveLobby(User user, String lobbyId) {
        return null;
    }

    @Override
    public Lobby[] getLobbies() {
        return Database.getLobbies().toArray(new Lobby[0]);
    }

}
