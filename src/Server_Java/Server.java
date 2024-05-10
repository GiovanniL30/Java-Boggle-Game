package Server_Java;


import App.ApplicationServer;
import App.ApplicationServerHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.Properties;

public class Server {

    public static void main(String[] args) {

        try {

            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "1099");
            props.put("org.omg.CORBA.ORBInitialHost", "192.168.25.158");
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            ApplicationServant servant = new ApplicationServant();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
            ApplicationServer href = ApplicationServerHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Application";

            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, href);
            System.out.println("Application Server is running ...");
            orb.run();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
