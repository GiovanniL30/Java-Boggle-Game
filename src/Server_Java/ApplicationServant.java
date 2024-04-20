package Server_Java;

import App.*;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;


import java.util.HashMap;


public class ApplicationServant extends ApplicationServerPOA {

    private final HashMap<String, Controller> controllerHashMap = new HashMap<>();

    @Override
    public void login(User user, Controller controller) {
        controllerHashMap.put(user.userID, controller);
        controller.receiveUpdates(ClientActions.NEW_MESSAGE);
        System.out.println("new user logged in");
        System.out.println(controllerHashMap.size());
    }

    @Override
    public void logout(User user) {
        controllerHashMap.remove(user.userID);
        System.out.println("A user leave");
        System.out.println(controllerHashMap.size());
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
    public Lobby[] getLobby() {
        return new Lobby[0];
    }
}
