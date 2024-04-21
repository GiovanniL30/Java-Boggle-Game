package Client_Java;

import App.*;
import Client_Java.controller.ClientController;
import Client_Java.view.MainFrame;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;

public class MainApplication {

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
            ApplicationServer server = ApplicationServerHelper.narrow(ncRef.resolve_str("Application"));

            SwingUtilities.invokeLater(() -> {
                ClientController clientController = new ClientController(server, orb);
                MainFrame mainFrame = new MainFrame(clientController);
                clientController.setMainFrame(mainFrame);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
