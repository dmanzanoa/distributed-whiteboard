import javax.swing.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * JoinWhiteboard.java:
 * Client that connects to the remote whiteboard server and launches a local client instance.
 */

public class JoinWhiteboard {

    private static String sAddress;
    private static String uname;
    private static int port;
    private static WhiteboardViewer wbv;
    private static WhiteboardManager wbm;
    private static UserHandler userH;

    public static void main(String[] args) {

        try {
            if (args.length != 3) throw new Exception("Wrong number of arguments. Arguments needed are: server address, port number and your username.");
            try {
                sAddress = args[0];
                port = Integer.parseInt(args[1]);
                uname = args[2];
            } catch (Exception e) {
                throw new Exception("Error: Wrong type of arguments, Port must be an integer and Username a String.");
            }
            if (port < 1024 || port > 65535) throw new Exception("Error: Invalid Port number, need Port number to be between 1024 and 65535.");

            Registry registry = LocateRegistry.getRegistry(sAddress);

            RemoteInterfacePainter remotePaint = (RemoteInterfacePainter) registry.lookup("paint");

            ClientWhiteBoard cwb = new ClientWhiteBoard(false, uname, 1, sAddress, port, "Client", remotePaint);
            while(true) {
                cwb.refresh();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
