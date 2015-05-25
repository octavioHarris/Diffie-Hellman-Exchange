/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This exception is used to visually communicate the fact an error occurred
 */
package diffiehelmanexchange;

import java.awt.Container;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ExceptionPopup extends Exception
{
    private final String popupTitle;
    private final String description;
    private final Container parent;
    
    /**
     * Constructor
     * @param errorTitle The title of the popup
     * @param description The description of the error
     */
    public ExceptionPopup (String popupTitle, String description, Container parent)
    {
        this.popupTitle = popupTitle;
        this.description = description;
        this.parent = parent;
    }

    /**
     * Displays the error message in a popup window
     */
	public void showErrorPopup() 
	{
        JOptionPane.showMessageDialog(parent, description, popupTitle, JOptionPane.ERROR_MESSAGE);
	}
}
