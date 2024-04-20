package Client_Java.controller;

import App.ControllerPOA;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

public class ClientController extends ControllerPOA  {

    @Override
    public void receiveUpdates(App.ClientActions clientActions) {

        System.out.println("I have received something in here");

    }

    @Override
    public void sendUpdates(App.ClientActions clientActions) {

    }

}
