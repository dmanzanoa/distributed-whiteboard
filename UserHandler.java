import java.io.Serializable;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.rmi.NotBoundException;
import javax.swing.JOptionPane;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * UserHandler.java
 * Class responsible of creating and handle users. It sends information 
 * to the remote structures in the RMI about the users to store remotely.
 * 
 */

public class UserHandler {
	
		private ConcurrentHashMap<String, User> users;
		private Map<String, RemoteInterfacePainter> remotePaints;
		private WhiteboardManager hostPainterManager;
		private RemoteInterfacePainter hostremotePaint;
		private Map<String, User> visitors;
		private int identity= 1;
		private IRemoteServerFunctions serverRemoteFunctions;
		private User userH;
		private String userType;
		private boolean isHost;
		
		public UserHandler(String name, int identity, String sAddress, int port, String userType) {
			this.remotePaints = new ConcurrentHashMap<>();
			this.users = new ConcurrentHashMap<>();
			this.userType = userType;
			if(userType.equalsIgnoreCase("Host")){
				try {
					this.userH = new User(name, identity, sAddress, port, userType);
					Registry registryHost = LocateRegistry.getRegistry(sAddress);
					this.serverRemoteFunctions = (IRemoteServerFunctions) registryHost.lookup("serverRequest");
					serverRemoteFunctions.setHost(name);
					serverRemoteFunctions.addUser(userH);
				}catch(Exception e) {
					System.out.println("Error: Problems locating the register");
				}
			}else {
				try {
					Registry registryHost = LocateRegistry.getRegistry(sAddress);
					this.hostremotePaint = (RemoteInterfacePainter) registryHost.lookup("paint");
					remotePaints.put(name, hostremotePaint);
					this.serverRemoteFunctions = (IRemoteServerFunctions) registryHost.lookup("serverRequest");
					String idName = serverRemoteFunctions.requestConnect(name, sAddress, port, userType);
					if(!idName.equalsIgnoreCase("")) {
						this.userH = new User(idName, identity, sAddress, port, userType);
					}else {
						System.exit(0);
					}
				}catch(Exception e) {
					System.out.println("Error: Problems locating the register");
				}
			}
				
		}

		public Map<String, RemoteInterfacePainter> getuserPaints() {
			for(String id: remotePaints.keySet()) {
		}
			return this.remotePaints;
		}
		
		public ConcurrentHashMap<String, User> getUsers(){
			return users;
		}
		
		
		public boolean userExist(String userID) {
			return this.users.containsKey(userID);
		}

		
		public void setHostWhiteboardManager(WhiteboardManager hostPainterManager) {
			this.hostPainterManager = hostPainterManager;
		}
		
		public WhiteboardManager getWhiteboardManager() {
			return hostPainterManager;
		}
		
		public User getUser() {
			return userH;
		}
		
		public RemoteInterfacePainter getHostRemotePaint() {

			return this.hostremotePaint;
		}
		
		public IRemoteServerFunctions getServices() {
			return serverRemoteFunctions;
		}
		
		public UserHandler getUserHandler() {
			return this;
		}
}
