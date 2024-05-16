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
import java.io.Serializable;
import java.util.Arrays;


public class ClientController extends ControllerPOA implements Serializable {

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
        }else  if(clientActions.equals(ClientActions.WORD_IS_REMOVED_SCORE)) {
            mainFrame.getGameStartedLobby().enableError("One of your unique words have been entered by another player, score will be nulled");
        }
    }


    @Override
    public void updatePlayerListView() {

        try {
            if(gameStarted) {
                mainFrame.getGameStartedLobby().updatePlayerList();
            }else {
                mainFrame.getWaitingLobby().updatePlayerList();
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
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
    public void endGameUpdate(User winner, int score, GamePlayer[] otherPlayers) {
        randomLetters = null;
        gameLobby = "";
        gameStarted = false;

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                mainFrame.getContentPane().remove(1);
                mainFrame.getContentPane().add(new GameSummary(ClientController.this, otherPlayers, 0, true));
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
        idleTimePopup.updateText("");

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                mainFrame.getContentPane().remove(1);
                mainFrame.getContentPane().add(mainFrame.getGameStartedLobby(), 1);
                mainFrame.revalidate();
                mainFrame.repaint();
                return null;
            }
        }.execute();
    }

    @Override
    public void startIdleTime(User roundWinner, int score, int round, GamePlayer[] otherPlayers) {
        new Thread(() -> {

            new SwingWorker<Object, Object>() {
                @Override
                protected Object doInBackground() {
                    mainFrame.getContentPane().remove(1);
                    mainFrame.getContentPane().add(new GameSummary(ClientController.this, otherPlayers,round,  false));
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    return null;
                }
            }.execute();

            idleTimePopup.setVisible(true);

        }).start();
    }


    @Override
    public void setIdleTimeLeft(String message) {
        idleTimePopup.updateText(message);
    }

    @Override
    public void receiveBanNotification() {
        new Thread(() -> {
            applicationServer.logout(loggedInUser.userID);
            loggedInUser = null;
            gameLobby = "";
            gameStarted = false;
            randomLetters = null;
            changeFrame(ClientViews.LOGIN);
            JOptionPane.showMessageDialog(mainFrame, "Your account was banned by the admin");
        }).start();
    }

    @Override
    public void receiveDeleteAccountNotification() {
        new Thread(() -> {
            applicationServer.logout(loggedInUser.userID);
            loggedInUser = null;
            gameLobby = "";
            gameStarted = false;
            randomLetters = null;
            changeFrame(ClientViews.LOGIN);
            JOptionPane.showMessageDialog(mainFrame, "Your account was deleted by the admin");
        }).start();
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
                mainFrame.getGameStartedLobby().enableError("Word is not a valid!");
            }else if(response.payload.extract_long() == 1) {
                mainFrame.getGameStartedLobby().enableError("You have already entered this word!");
            }else if(response.payload.extract_long() == 2) {
                mainFrame.getGameStartedLobby().enableError("Word was already entered by other player!");
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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
        }

    }

    public User[] getLeaderBoards() {
        return applicationServer.getAllUsers();
    }

    public User[] lobbyPlayer(String lobbyId) {
        return applicationServer.getPlayers(lobbyId);
    }
    public void leaveLobby(String lobbyId) throws LobbyException {

        try {
            Response response =  applicationServer.leaveLobby(loggedInUser.userID, lobbyId);

            if(response.isSuccess) {
                gameLobby = "";
                gameStarted = false;
                changeFrame(ClientViews.HOME_PAGE);
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }


    public void changeFrame(ClientViews clientViews) {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {

                switch (clientViews) {
                    case LOGIN: {
                        mainFrame.getHeader().setVisible(false);
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setLogin(new Login(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getLogin(), 1);
                        break;
                    }
                    case HOME_PAGE: {
                        mainFrame.getHeader().setVisible(true);
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setHomePage(new HomePage(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getHomePage(), 1);
                        mainFrame.getHeader().setUserName(loggedInUser.userName);
                        break;
                    }
                    case GAME_LOBBY: {
                        mainFrame.getHeader().setVisible(true);
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setGameStartedLobby(new GameStartedLobby(ClientController.this, gameLobby, randomLetters));
                        mainFrame.getContentPane().add(mainFrame.getGameStartedLobby(), 1);
                        break;
                    }
                    case WAIT_LOBBY: {
                        mainFrame.getHeader().setVisible(true);
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setWaitingLobby(new WaitingLobby(ClientController.this, gameLobby));
                        mainFrame.getContentPane().add(mainFrame.getWaitingLobby(), 1);
                        break;
                    }
                    case LEADER_BOARDS: {
                        mainFrame.getHeader().setVisible(true);
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setLeaderBoards(new LeaderBoards(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getLeaderBoards(), 1);
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
