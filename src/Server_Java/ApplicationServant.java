package Server_Java;

import App.*;
import org.omg.CORBA.Object;


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
    public String helloWord(String sentence) {
        return null;
    }

    @Override
    public void logout(User user) {
        controllerHashMap.remove(user.userID);
        System.out.println("A user leave");
        System.out.println(controllerHashMap.size());
    }
}
