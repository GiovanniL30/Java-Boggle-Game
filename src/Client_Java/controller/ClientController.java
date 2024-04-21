package Client_Java.controller;

import App.*;
import Client_Java.utilities.ClientViews;
import Client_Java.view.MainFrame;
import Client_Java.view.panels.HomePage;
import Client_Java.view.panels.Login;
import Client_Java.view.panels.Signup;
import Client_Java.view.panels.WaitingLobby;
import Server_Java.dataBase.Database;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;


public class ClientController extends ControllerPOA {

    private final ApplicationServer applicationServer;
    private final ORB orb;
    private MainFrame mainFrame;
    private User loggedInUser;
    private String currentLobbyID;


    public ClientController(ApplicationServer applicationServer, ORB orb) {
        this.applicationServer = applicationServer;
        this.orb = orb;


    }

    @Override
    public void receiveUpdates(App.ClientActions clientActions) {
        JOptionPane.showMessageDialog(mainFrame, "Hello Updated");
    }

    @Override
    public void sendUpdates(App.ClientActions clientActions) {

    }

    @Override
    public void updateWaitingLobbyView(User user) {

    }

    public void logIn(String userName, String password) {

        try {
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            User user = applicationServer.login(userName, password, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));

            if (!user.userName.isEmpty()) {
                loggedInUser = user;

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    applicationServer.logout(loggedInUser.userID);
                }));

                changeFrame(ClientViews.HOME_PAGE);
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(mainFrame, user.userID)).start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void createAccount(User user) {
        Response response = applicationServer.createAccount(user);
        if (response.isSuccess) {
            new Thread(() -> JOptionPane.showMessageDialog(mainFrame, "Created Account Successfully")).start();
            changeFrame(ClientViews.LOGIN);
        } else {
            if (response.payload.extract_string().contains("User name already exist")) {
                mainFrame.getSignup().getUserName().enableError("Username already exist");
            } else {
                JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string());
            }
        }
    }

    public void createNewLobby() {
        Response response = applicationServer.createLobby(loggedInUser);
        if(response.isSuccess) {
            currentLobbyID = response.payload.extract_string();
            changeFrame(ClientViews.GAME_LOBBY);
        }else {
            new Thread(() -> JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string())).start();
        }

    }

    public void joinLobby(String lobbyId) {

        if(applicationServer.joinLobby(loggedInUser, lobbyId )) {
            currentLobbyID = lobbyId;
            changeFrame(ClientViews.GAME_LOBBY);
        }else {
            JOptionPane.showMessageDialog(mainFrame, "Not available lobby id");
        }

    }

    public User[] lobbyPlayer(String lobbyId) {
        return applicationServer.getPlayers(lobbyId);
    }

    public void changeFrame(ClientViews clientViews) {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {

                switch (clientViews) {
                    case LOGIN: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setLogin(new Login(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getLogin(), 1);
                        break;
                    }
                    case SIGN_UP: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setSignup(new Signup(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getSignup(), 1);
                        break;
                    }
                    case HOME_PAGE: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setHomePage(new HomePage(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getHomePage(), 1);
                        break;
                    }
                    case GAME_LOBBY: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setWaitingLobby(new WaitingLobby(ClientController.this, currentLobbyID));
                        mainFrame.getContentPane().add(mainFrame.getWaitingLobby(), 1);
                        break;
                    }
                    default: {
                        System.out.println("Error changing frame");
                    }
                }

                return null;
            }

            @Override
            protected void done() {
                mainFrame.getContentPane().revalidate();
                mainFrame.getContentPane().repaint();
            }

        }.execute();
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
