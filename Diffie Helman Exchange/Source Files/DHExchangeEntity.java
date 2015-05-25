/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This class represents one entity participating in the key exchange
 */
package diffiehelmanexchange;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class DHExchangeEntity extends JFrame implements EncryptionConstants, EncryptionUtilities
{    
    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;
	
	private static final String ENCRYPT_STR = "Encrypt";
    private static final String DECRYPT_STR = "Decrypt";    
    private static final String CONVERT_BUTTON_STR = "Convert";
    
	private static final String ENTRY_TEXT_LABEL_STR = "Enter your text: ";
	private static final String RESULTING_TEXT_LABEL_STR = "Resulting text: ";
    
    private static final int PRIME = 17;
    
    private static final String REMOTE_USER_PUBLIC_KEY_STR = "Remote user public key: ";
    private static final String MY_PUBLIC_KEY_STR = "My public key: ";
    private static final String MY_PRIVATE_KEY_STR = "My private key: ";
    
    private final DH_Exchange dh_exchange;
    private final JButton convertButton;
	private final JButton sendButton;
    private final JTextArea entryText;
    private final JTextArea resultingText;
    private final JPanel conversionPanel;     
    private final JPanel keyEntryPanel;
    private final JPanel textPanel;
    private final JComboBox <String> conversionOptions;
    private final KeyEntryField myPrivateNumberField;
    private final KeyEntryField remoteUserPublicKeyField;
    private final FieldDisplay <Integer> myPublicKeyField;
    private final String name;
    
    private DHExchangeEntity remoteEntity;
	private int myPrivateNumber;
	private int myPublicKey;
	private int remotePublicKey;
    
	/**
	 * Constructor
	 * @param userName The name of the user
	 */
    public DHExchangeEntity (String userName)
    {
    	name = userName;
        Vector <String> optionsList = new Vector <String> ();
        optionsList.add(ENCRYPT_STR);
        optionsList.add(DECRYPT_STR);
        
        remoteEntity = null;
        conversionOptions = new JComboBox <String> (optionsList);
        dh_exchange = new DH_Exchange(PRIME);
        convertButton = new JButton(CONVERT_BUTTON_STR);
        sendButton = new JButton("Send");
        entryText = new JTextArea();
        resultingText = new JTextArea();
        conversionPanel = new JPanel(new GridLayout(0,2));
    	keyEntryPanel = new JPanel();
        textPanel = new JPanel(new GridLayout(0,2));
    	remoteUserPublicKeyField = new KeyEntryField(REMOTE_USER_PUBLIC_KEY_STR, 0, this);
        myPrivateNumberField = new KeyEntryField(MY_PRIVATE_KEY_STR, 0, this);
        myPublicKeyField = new FieldDisplay <Integer> (MY_PUBLIC_KEY_STR, getPublicKey());
        
        setPrivateNumber(0); 
        receivePublicKey(0);
        
        setupParentContainer();
        setupComponents();
        addComponents();
    }
    
    /**
     * Sets the properties of the parent container
     */
    private void setupParentContainer()
    {
        BorderLayout layout = new BorderLayout();
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setForeground(Color.BLACK);
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);
        setLayout(layout);
    }    

    /**
     * Sets a custom border as the border of a text area
     * @param textArea The text area
     */
    private void setTextAreaBorder(JTextArea textArea)
    {
    	Border border = null;
    	border = BorderFactory.createCompoundBorder(border, new EmptyBorder(2,2,2,2));
    	border = BorderFactory.createCompoundBorder(border, new LineBorder(Color.BLACK));
    	border = BorderFactory.createCompoundBorder(border, new EmptyBorder(2,2,2,2));
    	textArea.setBorder(border);
    }
    
    /**
     * Sets the properties of the components
     */
    private void setupComponents()
    {       
    	setupTextArea();
    	
        keyEntryPanel.setBackground(Color.WHITE);

        myPublicKeyField.setBorder(new EmptyBorder(5,0,5,0));
        
        conversionPanel.add(conversionOptions);
        conversionPanel.add(convertButton);
        
        convertButton.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				updateValues();
	            String message = entryText.getText();
	            String result = getConvertedMessage(message);
	            resultingText.setText(result);
			}
        });
        
        myPublicKeyField.add(sendButton);
        
        sendButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
				sendPublicNumber(remoteEntity);
        	}
        });
    }
    
    /**
     * Updates the values that will be used in the key exchange based on the user's entries
     */
    public void updateValues()
    {  
        setPrivateNumber(myPrivateNumberField.getValue()); 
        receivePublicKey(remoteUserPublicKeyField.getValue());
        
        myPublicKeyField.setValue(getPublicKey());
                
        repaint();
        revalidate();
    }
    
    /**
     * Sets the properties of the two text areas
     */
    private void setupTextArea()
    {
    	entryText.setLineWrap(true);
    	entryText.setWrapStyleWord(true);
    	setTextAreaBorder(entryText);
    	
    	resultingText.setEditable(false);
    	resultingText.setLineWrap(true);
    	resultingText.setWrapStyleWord(true);
    	setTextAreaBorder(resultingText);
    	
        JLabel entryTextLabel = new JLabel(ENTRY_TEXT_LABEL_STR);
        JLabel resultingTextLabel = new JLabel(RESULTING_TEXT_LABEL_STR);
    	
        JPanel labels = new JPanel(new GridLayout(0,2));
        JPanel textAreas = new JPanel(new GridLayout(0,2));
        
        labels.setBackground(Color.WHITE);
        
        labels.add(entryTextLabel);
        labels.add(resultingTextLabel);
        
        textAreas.add(entryText);
        textAreas.add(resultingText);
        
        entryText.setMargin(new Insets(5,5,5,5));
        
        textPanel.setLayout(new BorderLayout());        
        textPanel.add(labels, BorderLayout.NORTH);
        textPanel.add(textAreas, BorderLayout.CENTER);
    }
    
    /**
     * Adds the various components to the content pane of the window
     */
    private void addComponents()
    {
        keyEntryPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2,2,2,2);
        
        addToGridBag(myPrivateNumberField, 		keyEntryPanel, 0, 0, constraints, -1, 2, -1);
        addToGridBag(remoteUserPublicKeyField, 	keyEntryPanel, 0, 1, constraints, -1, 2, -1);
        addToGridBag(myPublicKeyField, 			keyEntryPanel, 0, 2, constraints, -1, 2, -1);
        addToGridBag(conversionPanel, 			keyEntryPanel, 0, 3, constraints, -1, 2, -1);
        
        add(keyEntryPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
    }
        
    /**
     * Encrypts or decrypts the message depending on the user's selected
     * @param message The message being altered
     * @return The message after it has been converted
     */
    private String getConvertedMessage(String message)
    {
        String encryptedMessage = "";
        int key = calculateKey();
        
        for (int charIndex = 0; charIndex < message.length(); charIndex++)
        {
            char currentCharacter = message.charAt(charIndex);      
        	encryptedMessage += convertCharacter(currentCharacter, key);
        }
        
        return encryptedMessage;
    }
    
    /**
     * Maps a character to another character based on the key provided
     * @param c The character to be changed
     * @param key The key to be used in the mapping
     * @return The character that was mapped to
     */
    private char convertCharacter(char c, int key)
    {
        boolean encrypt = (getConversionType() == ConversionType.ENCRYPT);
        return  encrypt ? encrypt(c, key) : decrypt(c, key);   
    }
    
    /**
     * Maps a plain text character to a cipher text character
     * @param c The character to be mapped
     * @param key The key to be used in creating the mapping
     * @return The cipher text character
     */
    private char encrypt (char c, int key)
    {
    	c += key;
    	return c;
    }
    
    /**
     * Maps a cipher text character to a plain text character
     * @param c The character to be mapped
     * @param key The key to be used in creating the mapping
     * @return The plain text character
     */
    private char decrypt (char c, int key)
    {
    	c -= key;
    	return c;
    }
    
    /**
     * Checks whether the user wishes to encrypt or decrypt the entered message
     * @return The type of the conversion
     */
    private ConversionType getConversionType()
    {
    	String conversionSelection = (String)conversionOptions.getSelectedItem();
    	boolean encrypt  = conversionSelection.equals(ENCRYPT_STR) ? true : false;
    	return encrypt ? ConversionType.ENCRYPT : ConversionType.DECRYPT;
    }
    
    /**
     * Adds a component to a container that utilizes a gridbag layout
     * @param component The component that is to be added
     * @param container This container to which the component will be added
     * @param gridx The column number of the components in the grid
     * @param gridy The row number of the component in the grid
     * @param baseConstraints The constraints object which h
     * @param weightx The weighting of the row with respect to the others
     * @param gridWidth How many horizontal cells of the grid, the component will occupy.
     * @param fill The fill of the object
     */
    private void addToGridBag(Component component, Container container, int gridx, int gridy, GridBagConstraints baseConstraints, double weightx, int gridwidth, int fill)
    {
    	GridBagConstraints originalConstraints = (GridBagConstraints)baseConstraints.clone();
    	
    	if (weightx != -1) baseConstraints.weightx = weightx;
    	if (gridx != -1) baseConstraints.gridx = gridx;
    	if (gridy != -1) baseConstraints.gridy = gridy;
    	if (fill != -1) baseConstraints.fill = fill;
    	if (gridwidth != -1) baseConstraints.gridwidth = gridwidth;

    	container.add(component, baseConstraints);
    	
    	baseConstraints = originalConstraints;
    }
    
    /**
     * Initializes the current display
     */
    public void initializeWindow()
    {
        pack();
        setTitle(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }
    
    /**
     * Allows the this instance to communicate to another entity
     * @param entity
     */
    private void setRemoteUser(DHExchangeEntity entity)
    {
    	this.remoteEntity = entity;
    	sendButton.setText("Send to " + remoteEntity.getUserName());
    }
    
    /**
     * Gets the user name of the remote entity
     * @return The user name
     */
    public String getUserName()
    {
    	return name;
    }
    
    /**
     * Allows two entities to communicate with one another
     * @param user1 One entity
     * @param user2 Another entity
     */
    public static void pair(DHExchangeEntity user1, DHExchangeEntity user2)
    {
    	user1.setRemoteUser(user2);
    	user2.setRemoteUser(user1);
    }

    /**
     * Sets the private number of this entity
     * @param num The private number
     */
	public void setPrivateNumber(int num) 
	{
    	myPrivateNumber = num;
		myPublicKey = dh_exchange.generatePublicKey(myPrivateNumber);
	}
	
	/**
	 * Returns the public key which is based off of the private number
	 * @return
	 */
	public int getPublicKey() 
	{
		return myPublicKey;
	}

	/**
	 * Calculates the encryption/decryption key based on the private key and public key
	 * @return The calculated encryption/decryption key
	 */
	public int calculateKey() {
        return dh_exchange.calculateKey(remotePublicKey, myPrivateNumber);
	}

	/**
	 * Receives the public key from another entity
	 * @param key The other entity's public key
	 */
	public void receivePublicKey (int key) {
		remoteUserPublicKeyField.setValue(key);
        remotePublicKey = key;
	}

	/**
	 * Sends the public key to another entity
	 * @param receiver
	 */
	public void sendPublicNumber(DHExchangeEntity receiver) {
		if (receiver == null) return;
		
		receiver.receivePublicKey(myPublicKey);
	}
}

