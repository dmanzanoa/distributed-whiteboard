import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of the RemoteInterfacePainter for the distributed whiteboard.
 */
public class RemoteInterfacePainterImpl extends UnicastRemoteObject implements RemoteInterfacePainter {
    private WhiteboardManager wbm;
    private CopyOnWriteArrayList<Shapes> shapesConcurrent = new CopyOnWriteArrayList<>();

    public RemoteInterfacePainterImpl(WhiteboardManager wbm) throws RemoteException {
        this.wbm = wbm;
    }

    @Override
    public void addShape(Shapes c) throws RemoteException {
        wbm.addShape(c);
    }

    @Override
    public WhiteboardManager getRemoteWhiteboardManager(WhiteboardManager wbm) throws RemoteException {
        return this.wbm;
    }

    @Override
    public void setShapes(CopyOnWriteArrayList<Shapes> shapes) throws RemoteException {
        this.shapesConcurrent = shapes;
    }

    @Override
    public CopyOnWriteArrayList<Shapes> getShapesConcurrent() throws RemoteException {
        return this.shapesConcurrent;
    }

    @Override
    public void loadWhiteboardPaints(Shapes shape) throws RemoteException {
        this.shapesConcurrent.add(shape);
    }

    @Override
    public void loadWhiteboardPaintsConcurrent(Shapes shapes) throws RemoteException {
        this.shapesConcurrent.add(shapes);
    }
}
