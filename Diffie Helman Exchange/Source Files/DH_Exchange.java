/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This class is used to perform the calculations that allow for the secure key exchange
 */
package diffiehelmanexchange;

import java.math.BigInteger;

public class DH_Exchange 
{
    private final int prime;
    private final int generator;
    
    /**
     * Constructor
     * @param prime The prime number which the calculations are based off of (arbitrary prime number)
     */
    public DH_Exchange(int prime)
    {
        this.prime = prime;
        this.generator = EncryptionUtilities.calculatePrimitiveRoot(prime);
    }
           
    /**
     * Generates the public key based on a chosen private key
     */
    public int generatePublicKey(int privateNumber)
    {
        return (int)Math.pow(generator, privateNumber) % prime;
    }

    /**
     * Calculates the key based on the private/public key pair
     * @param privateKey The private key being used in the calculation
     * @param publicKey The public key being used in the calculation
     * @return The generated encryption/decryption key 
     */
    public int calculateKey(int privateKey, int publicKey) 
    {
        BigInteger base = new BigInteger(Integer.toString(privateKey));
    	BigInteger exponent = new BigInteger(Integer.toString(publicKey));
    	BigInteger modulus = new BigInteger(Integer.toString(prime));
    	
    	BigInteger result =   base.modPow(exponent, modulus);
    	
    	return result.intValueExact();
    }
}
