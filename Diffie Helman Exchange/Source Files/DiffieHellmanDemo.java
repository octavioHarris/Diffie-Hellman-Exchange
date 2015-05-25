/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This is the main class which runs the application to display 
 * the workings of the Diffie Helman Exchange
 */
package diffiehelmanexchange;

import java.awt.Point;
import java.awt.Toolkit;

public class DiffieHellmanDemo 
{
    public static void main(String[] args) 
    {
    	DHExchangeEntity user1 =  new DHExchangeEntity("User 1");
    	DHExchangeEntity user2 = new DHExchangeEntity("User 2");
    	
    	int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    	int windowWidth = DHExchangeEntity.WIDTH;
    	
    	Point user1Center = new Point((1 * screenWidth / 4) - (windowWidth / 2), 0);
    	Point user2Center = new Point((3 * screenWidth / 4) - (windowWidth / 2), 0);
    	
    	user1.setLocation(user1Center);
    	user2.setLocation(user2Center);
    	
    	user1.initializeWindow();
        user2.initializeWindow();
        
        DHExchangeEntity.pair(user1, user2);
    }
}