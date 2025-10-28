import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * WhiteboardViewer.java
 * GUI of the whiteboard, that have all the buttons, text fields and 
 * interactive objects for the User. Additionally retrieve information
 * invoking remote methods to update the list of user, check status of the 
 * users and the host, and retrieve messages into the chat.
 *
 */

@SuppressWarnings("unchecked")
public class WhiteboardViewer {
    private JFrame frame;
    private JMenuBar menu;
    private JToolBar toolbar;
    private WhiteBoardPanel whiteboardPanel;
    private WhiteboardManager whiteboardManager;
    private Drawer drawerPainter;
    private Color color;
    private String currentShape = "FreeHand";
    private Color backgroundcolor;
    private UserHandler userH;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton sendButton;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;
    private IRemoteServerFunctions remoteServices;
    private User currentUser;
    private JTextField inputField;
    private JTextArea messageArea;
    private String hostUsername;

    
    public WhiteboardViewer(WhiteboardManager whiteboardManager, UserHandler userH, RemoteInterfacePainter remote) {
    	this.whiteboardManager = whiteboardManager;
    	this.color = new Color(0,0,0);
    	this.userH = userH;
    	this.remoteServices = this.userH.getServices();
    	try {
    		this.hostUsername = remoteServices.getHost();
    	}catch(Exception e) {
    		
    	}
    	this.currentUser = userH.getUser();
    	this.whiteboardPanel = new WhiteBoardPanel(this.whiteboardManager);
    	this.whiteboardManager.setpaintPanel(whiteboardPanel);
    	this.backgroundcolor = this.whiteboardPanel.getbackgGroundColor();
    	this.whiteboardManager.setDrawingColor(this.color);
    	drawerPainter = new Drawer(this);
    	this.whiteboardPanel.addMouseListener(drawerPainter);
    	this.whiteboardPanel.addMouseMotionListener(drawerPainter);
        this.userListModel = new DefaultListModel<>();
        this.userList = new JList<>(userListModel);
        frame = new JFrame();
        frame.setSize(1000,500);
        frame.setFont(new Font("Online Whiteboard: " + currentUser.getUserId(), Font.PLAIN, 20));
        if(currentUser.getUserType().equalsIgnoreCase("Host")) {
        	frame.setTitle("Online Whiteboard: Lets Draw " + currentUser.getUserId() + " !!. You are the: " + currentUser.getUserType());
        }else {
        	frame.setTitle("Online Whiteboard: Lets Draw " + currentUser.getUserId() + " !!. Hosted by: " + this.hostUsername) ;
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());
        frame.add(this.whiteboardPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.setLayout(new FlowLayout()); 
        Integer[] eraserSizes = {5, 10, 20, 30, 40}; 
        JComboBox<Integer> eraserSizeComboBox = new JComboBox<>(eraserSizes);
        eraserSizeComboBox.setSelectedItem(10); 
        eraserSizeComboBox.addActionListener(e -> {
            int selectedSize = (int) eraserSizeComboBox.getSelectedItem();
            this.whiteboardManager.setEraserSize(selectedSize); 
        });
        
        
        JButton colorButton = new JButton("Colors");
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(frame, "Choose Drawing Color", this.color);
            if (newColor != null) {
            	this.whiteboardManager.setDrawingColor(newColor);
            }
        });
        
        frame.add(buttonPanel, BorderLayout.NORTH);
        if(currentUser.getUserType().equalsIgnoreCase("Host")) {
        	menu = new JMenuBar();
        	JMenu fileMenu = new JMenu("File");
        	JMenuItem newItem = new JMenuItem("New");
        	JMenuItem openItem = new JMenuItem("Open");
        	JMenuItem SaveItem = new JMenuItem("Save");
        	JMenuItem SaveAsItem = new JMenuItem("Save As");
		

        	newItem.addActionListener(e -> {
        		try {
        			int choice = JOptionPane.showConfirmDialog(frame, "Are you sure that you want to start a new Paint?", "Confirm new painter", JOptionPane.YES_NO_OPTION);
        			if (choice == JOptionPane.YES_OPTION) {
        				remote.getShapesConcurrent().clear();
        				whiteboardManager.refresh();
        			}
        		} catch (RemoteException e1) {
        			e1.printStackTrace();
        		}
        	});

        	openItem.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				JFileChooser jfilechooser = new JFileChooser(System.getProperty("user.dir"));
    				int option = jfilechooser.showOpenDialog(null);
    				
    				if(option == 0) {
    					System.out.println("Opening File!");
    					
    					FileInputStream fileInputStream;
    					try {
    						fileInputStream = new FileInputStream(jfilechooser.getSelectedFile());
    						ObjectInputStream objInput = new ObjectInputStream(fileInputStream);
    						CopyOnWriteArrayList<Shapes> shapes = (CopyOnWriteArrayList<Shapes>) objInput.readObject(); 
    						remote.setShapes(shapes);
    						objInput.close();
    						fileInputStream.close();
    					} catch (FileNotFoundException e1) {
    						e1.printStackTrace();
    					} catch (RemoteException e1) {
    						e1.printStackTrace();
    					} catch (IOException e1) {
    						e1.printStackTrace();
    					} catch (ClassNotFoundException e1) {
    						e1.printStackTrace();
    					}

    					
    				}
    				else {
    					System.out.println("Shape History Save Failed!");
    				}

    				
    				
    				
    			}
    		});

        	SaveItem.addActionListener(e -> {
        		try {
        			FileOutputStream fileOutput = new FileOutputStream("imageHistory.txt");
		        	try (ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput)) {
		        		objOutput.writeObject(remote.getShapesConcurrent());
		        		JOptionPane.showMessageDialog(frame, "File: Draws save successfully", "File Save Error", JOptionPane.INFORMATION_MESSAGE);
		        	}
        		} catch (IOException ex) {
        			ex.printStackTrace();
        		}
        	});

        	SaveAsItem.addActionListener(e -> {
        		JFileChooser jfilechooser = new JFileChooser(System.getProperty("user.dir"));
        		int option = jfilechooser.showSaveDialog(null);
		    
        		if (option == JFileChooser.APPROVE_OPTION) {
        			System.out.println("Shape History saved successfully to file!");
		        
        			try (FileOutputStream fileOutput = new FileOutputStream(jfilechooser.getSelectedFile());
        					ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput)) {
        				objOutput.writeObject(remote.getShapesConcurrent());
        				JOptionPane.showMessageDialog(frame, "File: Draws save successfully", "File Save Error", JOptionPane.INFORMATION_MESSAGE);
        			} catch (IOException ex) {
        				ex.printStackTrace();
        			}
        		} else {
        			System.out.println("Shape History Save Failed!");
        		}
        	});
        
        	fileMenu.add(newItem);
        	fileMenu.add(openItem);
        	fileMenu.add(SaveItem);
        	fileMenu.add(SaveAsItem);
        	menu.add(fileMenu);
        	frame.setJMenuBar(menu);
        }
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(50);
        sendButton = new JButton("Send");
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        messageArea = new JTextArea();
        messageArea.setEditable(false);  
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageArea.setPreferredSize(new Dimension(200, 200));
        messageArea.setLineWrap(true);
        frame.add(scrollPane, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	sendMessageGui();
                getMessages();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JPanel userListPanel = createUserListPanel();
        frame.add(userListPanel, BorderLayout.WEST);
    	toolbar = new JToolBar();
        JButton addCircleButton = new JButton("Circle");
        JButton addLineButton = new JButton("Line");
        JButton addRectangleButton = new JButton("Rectangle");
        JButton addOvalButton = new JButton("Oval");
        JButton addFreeHandButton = new JButton("Freehand");
        JButton addEraserButton = new JButton("Eraser");
        JButton addTextButton = new JButton("Text");
        addCircleButton.addActionListener(e -> setShape("Circle"));
        addLineButton.addActionListener(e -> setShape("Line"));
        addRectangleButton.addActionListener(e -> setShape("Rectangle"));
        addOvalButton.addActionListener(e -> setShape("Oval"));
        addFreeHandButton.addActionListener(e -> setShape("FreeHand"));
        addEraserButton.addActionListener(e -> setShape("Eraser"));
        addTextButton.addActionListener(e -> setShape("Text"));
        toolbar.add(addCircleButton); 
        toolbar.add(addLineButton);
        toolbar.add(addRectangleButton); 
        toolbar.add(addOvalButton);
        toolbar.add(addFreeHandButton);
        toolbar.add(addTextButton);
        toolbar.addSeparator(); 
        toolbar.add(addEraserButton);
        toolbar.add(eraserSizeComboBox);
        toolbar.addSeparator(); 
        toolbar.add(colorButton);
        if(currentUser.getUserType().equalsIgnoreCase("Host")) {
        	JButton clearButton = new JButton("Clear"); 
        
        	clearButton.addActionListener(e -> {
        		try {
        			remote.getShapesConcurrent().clear();
        			whiteboardManager.refresh();
        		}catch(Exception em) {
        		
        		}
        	});
        	toolbar.add(clearButton);
        }
        this.whiteboardPanel.add(toolbar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
            	if(currentUser.getUserType().equalsIgnoreCase("Host")) {
                   		try {
                   			remoteServices.kickUser(currentUser.getUserId());
                   			frame.dispose();
                   			while(true){
                   				if(remoteServices.isUserListEmpty()) {
                   					System.exit(0);
                   				}
                   			}
                   		}catch(Exception em) {
                   			System.out.println("Error: error closing the window");
                   		}
                System.out.println("The host is closing the application. Do you accept?");
            	}
        }
        });
        
        Thread updateStatusAndMessages = new Thread(() -> {
            while (true) {
                getMessages();
                try {
                    
                    if (currentUser.getUserType().equalsIgnoreCase("Client")) {
                    	checkingStatus();
                    }
                    Thread.sleep(50); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           }
        });
     
        updateStatusAndMessages.start();
        
        Thread updateUserListThread = new Thread(() -> {
            while (true) {
            	updateUserListPanel(remoteServices);
                try {
                    
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           }
        });
     
        updateUserListThread.start();
        
        frame.setVisible(true);
        

    }
    
    private JPanel createUserListPanel() {
        JPanel userListPanel = new JPanel(new BorderLayout());
        userListPanel.setPreferredSize(new Dimension(200, 0)); 
        userListPanel.setMinimumSize(new Dimension(200, 0)); 
        userListPanel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE)); 
        JLabel userListLabel = new JLabel("User List");
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListPanel.add(userListLabel, BorderLayout.NORTH);
        userListPanel.add(userListScrollPane, BorderLayout.CENTER);
        currentUser = userH.getUser();
        remoteServices = userH.getServices();
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (userList.getSelectedIndex() != -1 && currentUser.getUserType().equalsIgnoreCase("Host") && userList.getSelectedValue() != null) {
                        String selectedUser = userList.getSelectedValue();
                        int choice = JOptionPane.showConfirmDialog(frame, "Delete user " + selectedUser + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                        	try {
                        		remoteServices.kickUser(selectedUser);
                        	}catch(Exception em){
                        		System.out.println("Error: error deleting user");
                        		
                        	}
                        }
                    }
                }
            }
        });

        userListPanel.add(userListLabel, BorderLayout.NORTH);
        userListPanel.add(userListScrollPane, BorderLayout.CENTER);
        return userListPanel;
    }


    private void checkingStatus(){
    	try {
    	boolean selfStatus = remoteServices.getStatus(this.currentUser.getUserId());
    	boolean hostStatus = remoteServices.isHostActive(hostUsername);
    	if(!selfStatus) {
    		JOptionPane.showMessageDialog(null, "You has been kicked", "Server Message", JOptionPane.INFORMATION_MESSAGE);
    		System.exit(0);
    	}
    	
    	if(!hostStatus) {
    		JOptionPane.showMessageDialog(null, "Host has abandoned the whiteboard", "Server Message", JOptionPane.INFORMATION_MESSAGE);
    		remoteServices.kickUser(this.currentUser.getUserId());
    		System.exit(0);
    	}
    	}catch(RemoteException e) {
    		
    	}
    }
    
    private void updateUserListPanel(IRemoteServerFunctions remoteServices) {
        SwingUtilities.invokeLater(() -> {
            ConcurrentHashMap<String, User> userActive = new ConcurrentHashMap<>();
            try {
                userActive = remoteServices.getUsers();
            } catch (Exception e) {
                System.out.println("Error: error retrieving users");
            }
            String[] users = new String[userActive.size()];
            int i = 0;
            for (String user : userActive.keySet()) {
            	User u = userActive.get(user);
            	if(u.getUserType().equalsIgnoreCase("Client")) {
            		users[i] = user;
                	i++;
            	}
            }
            userList.setListData(users);
        });
    }
    


	private void sendMessageGui() {
		String message = inputField.getText();
		if (!message.isEmpty()) {
			try {
			remoteServices.storeMessage(System.lineSeparator() + this.currentUser.getUserId() + ": " + message + System.lineSeparator());
			inputField.setText("");
			}catch(RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getMessages() {
		try {
			messageArea.setText(remoteServices.getMessage());
		} catch (RemoteException e) {
			System.out.println("Error inside repaint method of ServerGUI");
			e.printStackTrace();
		}
	}

	

    public JFrame getFrame() {
        return frame;
    }
    
    
    public String getShape() {
    	return this.currentShape;
    }
    
    public void setShape(String currentShape) {
    	this.currentShape = currentShape;
    }
    
    public Color getbackgGroundColor() {
    	return this.backgroundcolor;
    }
    
    public WhiteboardManager getWhiteboardManager() {
        return this.whiteboardManager;
    }

}