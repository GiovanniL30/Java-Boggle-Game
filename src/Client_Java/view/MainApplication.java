package Client_Java.view;

import App.*;
import Client_Java.controller.ClientController;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class MainApplication {

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object obj = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(obj);
            ApplicationServer server = ApplicationServerHelper.narrow(ncRef.resolve_str("Application"));

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            ClientController clientController = new ClientController();
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(clientController);

            Controller controllerRef = ControllerHelper.narrow(ref);

            User user = new User("userID", "firstName", "lastName", "userName", "password", false, "status", false);

            server.login(user, controllerRef);



            System.out.println("Ac");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Hell");
                server.logout(user);
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
