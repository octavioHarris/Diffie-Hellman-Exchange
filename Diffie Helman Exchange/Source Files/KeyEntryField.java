/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This component is used to manage keys that are entered
 */
package diffiehelmanexchange;

@SuppressWarnings("serial")
public class KeyEntryField extends EditableField<Integer> {

	private final DHExchangeEntity entity;
	
	/**
	 * Constructor
	 * @param description The description of the value
	 * @param value The value that will be displayed
	 * @param entity The entity that the key is tied to
	 */
    public KeyEntryField(String description, Integer value, DHExchangeEntity entity) 
    {
        super(description, value);
        this.entity = entity;
    }

    /**
     * Edits the value displayed in the component. This function also updates the values carried by <code>entity</code>
     */
    @Override
    public void editValue() 
    {
    	super.editValue();
    	entity.updateValues();
    }
    
    /**
     * Converts the entered value into its numerical value
     * @throws ExceptionPopup when the value entered cannot be converted
     */
    @Override
    public Integer convertType(String input) throws ExceptionPopup 
    {       
        try
        {
            return Integer.parseInt(input);
        }
        catch(Exception e)
        {
            throw new ExceptionPopup("Invalid Key", "The value entered was not a valid key.", this);
        }
    }
}
