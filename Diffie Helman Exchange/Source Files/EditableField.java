/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This component is designed to make it simple to display a property 
 * while leaving it easy to edit 
 */
package diffiehelmanexchange;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public abstract class EditableField <T> extends FieldDisplay <T> 
{
    private final JButton editButton;
    
    /**
     * Constructor
     * @param description The description of the value
     * @param value The value of a specified type displayed by this component 
     */
    public EditableField(String description, T value)
    {
        super(description, value);
        this.editButton = new JButton("Edit");
        
        setupComponents();
        addComponents();
    }
    
    /**
     * Sets up the properties of the components
     */
    private void setupComponents()
    {
        editButton.addActionListener((ActionEvent e) -> 
        {
            editValue();
        });
    }
    
    /**
     * Adds the components to the main components
     */
    private void addComponents()
    {
        add(editButton);
    }
    
    /**
     * This function is called when the edit button is pressed. It allows the user to alter the value carried by this component.
     */
    protected void editValue()
    {
        String rawInput = JOptionPane.showInputDialog(this, "Please enter a value: ", description, JOptionPane.PLAIN_MESSAGE);

        try
        {
            T newValue = convertType(rawInput);
            setValue(newValue);           
        }
        catch(ExceptionPopup e)
        {
        	e.showErrorPopup();
        }
    };
    
    /**
     * This function is used to convert the entered text into a value of the specified type. 
     * @param input The string entered into the edit prompt
     * @return The entered value's equivalent in the specified type
     * @throws ExceptionPopup When the input cannot be successfully converted into the desired type
     */
    public abstract T convertType(String input) throws ExceptionPopup;
}
