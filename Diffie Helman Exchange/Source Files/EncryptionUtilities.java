/**
 * Programmer: Octavio Harris
 * Last Modified: May 19, 2015
 * Description: This interface is used to organize the mathematical
 * utility functions that are used in the key generation
 */
package diffiehelmanexchange;

import java.math.BigInteger;
import java.util.Vector;

public interface EncryptionUtilities 
{
	/**
	 * Determines the primitive root of a given prime number
	 * @param primeValue The prime number
	 * @return The primitive root of the prime number
	 */
    public static int calculatePrimitiveRoot(int primeValue)
    {
        Vector <Integer> primeFactors = getPrimeFactors(primeValue - 1);
        
        for (int rootCandidate = 2; rootCandidate < primeValue; rootCandidate++)
        {                    
            boolean isPrimeRoot = true;
            for (int primeFactor : primeFactors)
            {
            	BigInteger base = new BigInteger(Integer.toString(rootCandidate));
            	BigInteger exponent = new BigInteger(Integer.toString((int)(primeValue - 1)/ primeFactor));
            	BigInteger modulus = new BigInteger(Integer.toString(primeValue));
            	
            	int result = base.modPow(exponent, modulus).intValueExact();
            	
            	if (result == 1)
            	{
                    isPrimeRoot = false;
                    break;
                }
            }
            if (isPrimeRoot)
            {
            	return rootCandidate;
            }
        }
        return -1;
    }
    
    /**
     * Calculates the prime factors of a given number
     * @param num The given number
     * @return A vector carrying the prime factors of a given number
     */
    public static Vector <Integer> getPrimeFactors(int num)
    {
        Vector <Integer> primeFactors = new Vector <> ();

        int n = num;
        
        for (int i = 2; i <= num; i++)
        {
            while (n % i == 0)
            {
                primeFactors.add(i);
                n /= i;
            }
        }
        
        return primeFactors;
    }
}
