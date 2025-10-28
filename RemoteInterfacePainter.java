import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Interface for remote painting operations on the shared whiteboard.
 */
public interface RemoteInterfacePainter extends Remote {
    void addShape(Shapes c) throws RemoteException;
    WhiteboardManager getRemoteWhiteboardManager(WhiteboardManager wbm) throws RemoteException;
    void loadWhiteboardPaints(Shapes shape) throws RemoteException;
    CopyOnWriteArrayList<Shapes> getShapesConcurrent() throws RemoteException;
    void setShapes(CopyOnWriteArrayList<Shapes> shapes) throws RemoteException;
    void loadWhiteboardPaintsConcurrent(Shapes shapes) throws RemoteException;
}
