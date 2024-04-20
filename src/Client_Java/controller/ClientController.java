package Client_Java.controller;

import App.ControllerPOA;
import Client_Java.view.MainFrame;


public class ClientController extends ControllerPOA  {

    private MainFrame mainFrame;

    public ClientController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void receiveUpdates(App.ClientActions clientActions) {
        System.out.println("I have received something in here");
    }

    @Override
    public void sendUpdates(App.ClientActions clientActions) {

    }


}
