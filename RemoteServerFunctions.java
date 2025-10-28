
import java.rmi.RemoteException;
import java.util.Random;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * RemoteServerFunctions.java
 * Class that implement IRemoteServerFunctions interface.
 * 
 * 
 */
public class RemoteServerFunctions extends UnicastRemoteObject implements IRemoteServerFunctions{
	
	UserHandler userH;
	int identity = 1;
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
	public String messageArchive = "";
	static boolean valid = false;
	private Random r = new Random();
	private String Host;
	
	public RemoteServerFunctions(UserHandler userH) throws RemoteException{
		this.userH = userH;
	}
	
	
	@Override
	public ConcurrentHashMap<String, User> getUsers() throws RemoteException{
		return this.users;
	}
	
	@Override
	public synchronized void addUser(User user) {
		String id = user.getUserId();
		this.users.put(id, user); 
	}
	
	@Override
	public synchronized String requestConnect(String userId, String ip, int registerPort, String userType) throws RemoteException{
		String userIdFinal = "";
		boolean flag = true;
		boolean flag2 = users.containsKey(userId);
		int option = JOptionPane.showConfirmDialog(null,  "Do you approve User: " + userId + " to join?", "Request to join",JOptionPane.YES_NO_OPTION);
		if (option == 0){
			if(flag2) {
				option = JOptionPane.showConfirmDialog(null,  "The username is already in use, do you want to generate a random username?", "Request to join",JOptionPane.YES_NO_OPTION);
				if (option == 0){
					String number_rand = Integer.toString(r.nextInt(100-0+1)+0);
					userIdFinal = userId + number_rand;
					valid = true;
				}else {
					option = JOptionPane.showConfirmDialog(null,  "Do you want to try another username?", "Request to join",JOptionPane.YES_NO_OPTION);
					if (option == 0){
						while(flag) {
							try {	
								String userInput = JOptionPane.showInputDialog(
										null,
										"Write your username:",
										JOptionPane.PLAIN_MESSAGE );
                	        		if(users.containsKey(userInput)){
                	        			valid = false;
                	        			throw new Exception("Username in use");
                	        		}else{
                	        			userIdFinal = userInput;
                	        			valid = true;
                	        			flag = false;
        	        				}
        	        			}catch(Exception e) {
                	        		JOptionPane.showMessageDialog(null, "Username in use", "Login Error", JOptionPane.INFORMATION_MESSAGE);
        	        			}
        	        	}
					}else{
						valid = false;
					}
        	  }
			}else{
				valid = true;
				userIdFinal = userId;
	}
			
}
		if (valid == true){
        	users.put(userIdFinal, new User(userIdFinal, identity, ip, registerPort, userType));
            this.identity++;
        		return userIdFinal;
        	}else {
        		return "";
        	}
    }
	
	@Override
	public synchronized ConcurrentHashMap<String, User> list() {
		return users;
	}
	
	@Override
	public synchronized void kickUser(String name) throws RemoteException{
		users.remove(name);
	}
	@Override
    public synchronized void storeMessage(String message) throws RemoteException {
    	messageArchive += message;
    }
	@Override
    public synchronized String getMessage() throws RemoteException {
    	return this.messageArchive;
    }
    
    public synchronized void setMessage(String Message) throws RemoteException {
    	this.messageArchive = Message;
    }
	
	@Override
	public boolean getStatus(String userId) throws RemoteException{
	    return users.containsKey(userId);
	  }
	@Override
	public synchronized boolean isHostActive(String host) throws RemoteException{
		return users.containsKey(host);
	}
	@Override
	public synchronized String getHost() {
		return this.Host;
	} 
	@Override
	public synchronized void setHost(String host) throws RemoteException{
		this.Host = host;
	}
	@Override
	public boolean isUserListEmpty() throws RemoteException{
		return users.isEmpty();
	}
	
}

