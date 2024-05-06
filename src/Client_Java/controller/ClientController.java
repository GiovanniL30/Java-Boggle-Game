package Client_Java.controller;

import App.*;
import Client_Java.utilities.ClientViews;
import Client_Java.view.MainFrame;
import Client_Java.view.components.IdleTimePopup;
import Client_Java.view.panels.*;
import Server_Java.GameLobby;
import Server_Java.dataBase.Database;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;
import java.util.Arrays;


public class ClientController extends ControllerPOA {

    private final ApplicationServer applicationServer;
    private final ORB orb;
    private MainFrame mainFrame;
    private User loggedInUser;
    private String gameLobby = "";
    private boolean gameStarted = false;
    private String[] randomLetters = null;
    private IdleTimePopup idleTimePopup;


    public ClientController(ApplicationServer applicationServer, ORB orb) {
        this.applicationServer = applicationServer;
        this.orb = orb;

    }

    @Override
    public void receiveUpdates(App.ClientActions clientActions) {
        if (clientActions.equals(ClientActions.START_GAME)) {
            new Thread(() -> applicationServer.startGame(gameLobby)).start();
            gameStarted = true;
            changeFrame(ClientViews.GAME_LOBBY);
        } else if (clientActions.equals(ClientActions.NO_PLAYER_LOBBY)) {
            new Thread(() -> JOptionPane.showMessageDialog(mainFrame, "The game won't start there are no players :(")).start();
            changeFrame(ClientViews.HOME_PAGE);
        }
    }

    @Override
    public void sendUpdates(App.ClientActions clientActions) {

    }

    @Override
    public void updatePlayerListView() {
        if(gameStarted) {
            mainFrame.getGameStartedLobby().updatePlayerList();
        }else {
            mainFrame.getWaitingLobby().updatePlayerList();
        }
    }

    @Override
    public void setWaitingTime(int time) {
        mainFrame.getWaitingLobby().setTime(time);
    }

    @Override
    public void setGameTime(int time) {
        mainFrame.getGameStartedLobby().setTime(time);
    }

    @Override
    public void setRound(int round) {
        mainFrame.getGameStartedLobby().setRound(round);
    }

    @Override
    public void updatePlayerScore(String id, int newScore) {
        mainFrame.getGameStartedLobby().updatePlayerScores(id, newScore);
    }

    @Override
    public void receiveLetter(String[] letters) {
          randomLetters = letters;

        if(mainFrame.getGameStartedLobby() != null) {
            mainFrame.getGameStartedLobby().setRandomLettersPanel(letters);
        }
    }

    @Override
    public void endGameUpdate(User winner, int score) {

        randomLetters = null;
        gameLobby = "";
        gameStarted = false;

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                mainFrame.getContentPane().remove(1);
                mainFrame.getContentPane().add(new GameSummary(winner, score, ClientController.this));
                return null;
            }

            @Override
            protected void done() {
                mainFrame.getContentPane().revalidate();
                mainFrame.getContentPane().repaint();
            }
        }.execute();

    }

    @Override
    public void stopIdleTime() {
        idleTimePopup.setVisible(false);
    }

    @Override
    public void startIdleTime() {
        new Thread(() -> {
            idleTimePopup.setVisible(true);
        }).start();

    }

    @Override
    public void setIdleTimeLeft(String message) {
        idleTimePopup.updateText(message);
    }


    public void submitWord(String word) {

        if(mainFrame.getGameStartedLobby() == null) {
            System.out.println("null");
            return;
        }
        Response response = applicationServer.submitWord(word, loggedInUser.userID, gameLobby);

        if(response.isSuccess) {
            mainFrame.getGameStartedLobby().addNewWordBlock(word, response.payload.extract_long());
            mainFrame.getGameStartedLobby().removeError();
        }else {

            if(response.payload.extract_long() == 0) {
                mainFrame.getGameStartedLobby().setError("Word is not a valid word");
            }else {
                mainFrame.getGameStartedLobby().setError("You have already entered this word");
            }

        }

        mainFrame.getGameStartedLobby().clearText();
    }
    public void logIn(String userName, String password) {

        try {
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            User user = applicationServer.login(userName, password, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));

            if (!user.userName.isEmpty()) {
                loggedInUser = user;

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if(!gameLobby.isEmpty()) {
                        try {
                            leaveLobby(gameLobby);
                        } catch (LobbyException e) {
                            throw new RuntimeException(e);
                        }
                    }
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

    public void createAccount(User user) throws CreateException {


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

        try {

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            Response response = applicationServer.createLobby(loggedInUser, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));
            if (response.isSuccess) {
                gameLobby = response.payload.extract_string();
                changeFrame(ClientViews.WAIT_LOBBY);
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string())).start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void joinLobby(String lobbyId) {

        try {
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            Response response = applicationServer.joinLobby(loggedInUser, lobbyId, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));
            if (response.isSuccess) {
                gameLobby = lobbyId;
                changeFrame(ClientViews.WAIT_LOBBY);
            } else {
                JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public User[] lobbyPlayer(String lobbyId) {
        return applicationServer.getPlayers(lobbyId);
    }
    public void leaveLobby(String lobbyId) throws LobbyException {
        Response response =  applicationServer.leaveLobby(loggedInUser.userID, lobbyId);

        if(response.isSuccess) {
            gameLobby = "";
            gameStarted = false;
            changeFrame(ClientViews.HOME_PAGE);
        }

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
                        mainFrame.getHeader().setUserName(loggedInUser.userName);
                        break;
                    }
                    case GAME_LOBBY: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setGameStartedLobby(new GameStartedLobby(ClientController.this, gameLobby, randomLetters));
                        mainFrame.getContentPane().add(mainFrame.getGameStartedLobby(), 1);
                        break;
                    }
                    case WAIT_LOBBY: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setWaitingLobby(new WaitingLobby(ClientController.this, gameLobby));
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
        idleTimePopup = new IdleTimePopup(this.mainFrame);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
