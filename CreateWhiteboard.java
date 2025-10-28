import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;
import java.rmi.Remote;

/**
 * CreateWhiteboard.java:
 * Launch the host and bind the functions of RMI
 */

public class CreateWhiteboard {

    private static int port;
    private static String uname;
    private static String sAddress;
    private int userId = 0;
    private static WhiteboardManager wbm;
    private static WhiteboardViewer wbv;
    private static UserHandler userH;
    private static WhiteboardManager paintManager;
    private static RemoteInterfacePainter remotePaint;
    private static User ManagerUser;
    private static Registry registry;
    private static IRemoteServerFunctions userRemote;

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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        try {
            LocateRegistry.createRegistry(port);
            remotePaint = new RemoteInterfacePainterImpl(wbm);
            userRemote = new RemoteServerFunctions(userH);
            registry = LocateRegistry.getRegistry();
            registry.bind("paint", remotePaint);
            registry.bind("serverRequest", userRemote);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userH = new UserHandler(uname, 0, sAddress, port, "Host");
        wbm = new WhiteboardManager(remotePaint);
        userH.setHostWhiteboardManager(wbm);
        wbv = new WhiteboardViewer(wbm, userH, remotePaint);

        System.out.println("Server Running");
        while(true){
            wbm.refresh();
        }

    }
}
