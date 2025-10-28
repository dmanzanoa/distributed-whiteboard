import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * User.java
 * User class that generates user objects and store its information.
 * 
 * 
 */


public class User implements Serializable {
	String userType;
	String userId;
	int identity;
	String ip;
	int registerPort;

	public User(String userId, int identity, String ip, int registerPort, String userType) {
		this.userId = userId;
		this.identity = identity;
		this.ip = ip;
		this.registerPort = registerPort;
		this.userType = userType;

	}

	public String getUserId() {
		return userId;
	}

	public String getIp() {
		return ip;
	}

	public int getRegisterPort() {
		return registerPort;
	}

	public String getUserType() {
		return this.userType;
	}
	
	public int getUserIdentity() {
		return this.identity;
	}
	
	public void setUserType(String type) {
		this.userType = type;
	}
	
}