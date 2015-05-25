/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This interface is used to manage constants used for encryption/decryption 
 */
package diffiehelmanexchange;

public interface EncryptionConstants {
	
    public static int ALPHABET_SIZE = 26;
    public static int UPPERCASE_START = 65;
    public static int LOWERCASE_START = 97;
    public static int UPPERCASE_END = UPPERCASE_START + ALPHABET_SIZE - 1;
    public static int LOWERCASE_END = LOWERCASE_START + ALPHABET_SIZE - 1;
    
    public static enum ConversionType
    {
    	ENCRYPT,
    	DECRYPT
    }
}
