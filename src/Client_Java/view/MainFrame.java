package Client_Java.view;

import Client_Java.controller.ClientController;
import Client_Java.view.components.Header;
import Client_Java.view.panels.*;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private JPanel mainLayout = new JPanel();
    private Login login;
    private HomePage homePage;
    private Signup signup;
    private WaitingLobby waitingLobby;
    private GameStartedLobby gameStartedLobby;


    public MainFrame(ClientController clientController) {
        init();
        login = new Login(clientController);
        getContentPane().add(new Header(), BorderLayout.NORTH);
        getContentPane().add(login, BorderLayout.CENTER);
    }

    private void init() {

        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("Button.arc", 10);
            UIManager.put("TextComponent.arc", 5);
            UIManager.put("ScrollBar.width", 10);
            UIManager.put("ScrollBar.thumbArc", 3);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Boggled");
        BorderLayout borderLayout = new BorderLayout();

        mainLayout.setBorder(new EmptyBorder(25, 50 ,25, 50));
        mainLayout.setBackground(Color.white);
        setContentPane(mainLayout);

        getContentPane().setLayout(borderLayout);
        setVisible(true);
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

    public Signup getSignup() {
        return signup;
    }

    public void setSignup(Signup signup) {
        this.signup = signup;
    }

    public WaitingLobby getWaitingLobby() {
        return waitingLobby;
    }

    public void setWaitingLobby(WaitingLobby waitingLobby) {
        this.waitingLobby = waitingLobby;
    }

    public GameStartedLobby getGameStartedLobby() {
        return gameStartedLobby;
    }

    public void setGameStartedLobby(GameStartedLobby gameStartedLobby) {
        this.gameStartedLobby = gameStartedLobby;
    }
}