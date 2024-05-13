package Server_Java;

import App.ApplicationServer;
import App.ApplicationServerHelper;
import Server_Java.controller.AdminController;
import Server_Java.view.AdminMainFrame;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;
import java.util.Properties;

public class AdminMainApplication {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "1099");
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");

            ORB orb = ORB.init(args, props);
            org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
            ApplicationServer server = ApplicationServerHelper.narrow(ncRef.resolve_str("Application"));

            SwingUtilities.invokeLater(() -> {
                AdminController adminController = new AdminController(server, orb);
                AdminMainFrame adminMainFrame = new AdminMainFrame(adminController);
                adminController.setAdminMainFrame(adminMainFrame);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
