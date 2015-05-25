/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This component is used to display a value of a certain property
 */
package diffiehelmanexchange;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class FieldDisplay <T> extends JPanel 
{
    protected T value;
    protected final String description;
    protected final JLabel descriptionLabel;
    protected final JLabel valueLabel;
    
    /**
     * Constructor
     * @param description The description of the value being displayed
     * @param value The value being displayed
     */
    public FieldDisplay(String description, T value)
    {
        super();
        this.value = value;
        this.description = description;
        
        descriptionLabel = new JLabel(description);
        valueLabel = new JLabel(value + "");
        
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        setupParentContainer();
        addComponents();
    }
    
    /**
     * Sets up the properties of the parent container
     */
    private void setupParentContainer()
    {
    	setBackground(Color.WHITE);
        setLayout(new GridLayout(0,3));
    }
    
    /**
     * Adds the various components to the main component
     */
    private void addComponents()
    {
        add(descriptionLabel);
        add(valueLabel);
    }
    
    /**
     * Sets the value carried by this component
     * @param value The value of the specified type
     */
    public void setValue(T value)
    {
        this.value = value;
        valueLabel.setText(value.toString());
    }

    /**
     * Returns the value currently stored by this component
     * @return The value being displayed
     */
    public T getValue()
    {
        return value;
    }   
}
