import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientWhiteBoard{
	protected UserHandler userH;
	protected WhiteboardManager wbm;
	protected WhiteboardViewer wbv;

	public ClientWhiteBoard(boolean isHost, String uname, int identity, String sAddress, int port, String userType, RemoteInterfacePainter remote){
		InitializeClientWhiteboard(isHost, uname, identity, sAddress,port, "Client", remote);
	}
	
	public void InitializeClientWhiteboard(boolean isHost,String uname, int identity, String sAddress, int port, String userType, RemoteInterfacePainter remote) {
		userH = new UserHandler(uname, identity, sAddress,port,userType);
		wbm = new WhiteboardManager(remote);
		wbv = new WhiteboardViewer(this.wbm, userH, remote);
	
		
	}
	
	public void refresh(){
		wbm.refresh();
	}
	
}
