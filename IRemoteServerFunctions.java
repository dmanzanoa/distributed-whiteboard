import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface for server-side user management and messaging functions.
 */
public interface IRemoteServerFunctions extends Remote {
    String requestConnect(String userId, String ip, int registerPort, String userType) throws RemoteException;
    ConcurrentHashMap<String, User> getUsers() throws RemoteException;
    void addUser(User user) throws RemoteException;
    ConcurrentHashMap<String, User> list() throws RemoteException;
    void storeMessage(String message) throws RemoteException;
    void kickUser(String name) throws RemoteException;
    boolean getStatus(String userId) throws RemoteException;
    String getHost() throws RemoteException;
    String getMessage() throws RemoteException;
    void setMessage(String message) throws RemoteException;
    void setHost(String host) throws RemoteException;
    boolean isHostActive(String host) throws RemoteException;
    boolean isUserListEmpty() throws RemoteException;
}
