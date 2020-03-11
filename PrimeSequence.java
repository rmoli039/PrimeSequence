/*=======================================================================
|   Source code:  PrimeSequence.java
|
|         Class:  PrimeSequence 
|
|        Author:  Richard Molina
|    Student ID:  6140544
|    Assignment:  Program #5 - Sequence of Primes
|  
|            Course:  COP 3337 (Intermediate Programming)
|           Section:  U08
|        Instructor:  William Feild  
|          Due Date:  1 November 2018, at the beginning of class 
|
|	I hereby certify that this collective work is my own
|	and none of it is the work of any other person or entity.
|	______________________________________ [Signature]
|  
|     Language:  Java 
|  Compile/Run:   
| 		javac PrimeSequence.java Sequence.java
| 
|        Purpose:  This class serves to implement the Sequence interface to
|				   allow for the construction of PrimeSequence objects. The
|				   provides methods to construct a new PrimeSequence, iterate
|				   it safely, check if the current number is prime, and do some
|				   data collection for analysis. Specifically, prime numbers in 
|				   a sequence are counted by their last digit, and the class
|				   provides a way to access that data.
|
|  Inherits From:  
|
|     Interfaces:  Sequence
|
|  +-----------------------------------------------------------------------
|
|      Constants:  MAX_NUMBER is the maximum value that can be stored in an
|				   integer variable. This is used to prevent integer overflow
|				   when iterating.
|
|				   OVERFLOW_PRIME is initialized to 0. This is the value
|				   that is used to demonstrate the limit of integer overflow.
|				   If the SequenceDemo class attempts to iterate the
|				   PrimeSequence beyond MAX_NUMBER, a 0 will be returned for
|				   every following request.
|
|				   DIGITS is initialized to 10, the number of digits there are
|				   from 0 to 9, inclusive.
|
| +-----------------------------------------------------------------------
|
|     Constructors:  PrimeSequence
|                     - Constructs a PrimeSequence sequence
|					  - Takes input for the starting number
|					  - Sets the current number to the starting number
|					  - Constructs an array to count the last digits of
|						the sequence's prime numbers
|
|    Class Methods:  None
|
| Instance Methods:  next() 
|					  - Checks for integer overflow
|					  - Safely iterates the PrimeSequence by 1
|					  - Returns the value of the new current number, or the
|						OVERFLOW_PRIME if needed
|					 isPrime()
|					  - Returns whether or not a number is prime
|					 getCurrentNumber()
|					  - Returns the current number of the sequence
|					 processLastDigit()
|					  - Counts the last digit of a new prime number and stores it
|						in the sequence's counter
|					 getLastDigitCounter(int lastDigit)
|					  - Returns the count of a specific last digit (0-9)
|					 getLargestCounter()
|					  - Returns the last digit that was counted the most in the
|						sequence
|					 
|   Extra Credit:  Completed. The program avoids integer overflow by outputting
|				   0 for any additional requested primes over the maximum integer
|				   value.
|
|  *===========================================================================*/

import java.lang.Math;   	    //Allows the use of the sqrt function

public class PrimeSequence implements Sequence
{
	private int currentNumber;
	private int[] lastDigitCounters;
	private final static int MAX_NUMBER = Integer.MAX_VALUE;
	private final static int OVERFLOW_PRIME = 0;
	private final static int DIGITS = 10;

	/*---------------------------- PrimeSequence ----------------------------
    |  Constructor PrimeSequence (startingNumber)
    |
    |  Purpose:  To construct a PrimeSequence sequence. Initializes the current
    |			 number to the input starting number and constructs an array
    |			 to count the last digits.
    |
    |  @param	 startingNumber the number the sequence should start from
    *-------------------------------------------------------------------*/
	public PrimeSequence(int startingNumber) 
	{
		currentNumber = startingNumber;
		lastDigitCounters = new int[DIGITS];
	}
	
	/*---------------------------- next ----------------------------
    |  Method next ()
    |
    |  Purpose:  Checks for integer overflow and then safely iterate the 
    |			 PrimeSequence by 1. If the sequence attempts to iterate 
    |			 beyond the MAX_NUMBER, OVERFLOW_PRIME is output for the 
    |			 remainder of the requests.
    |
    |			 Implements and overrides the next() method from the Sequence
    |			 interface.
    |
    |  @param	 
    |
    |  @return  the new current number in the prime sequence
    *-------------------------------------------------------------------*/
	@Override
	public int next() 
	{
		if (currentNumber == OVERFLOW_PRIME)
		{
			return currentNumber;
		}
		
		if (currentNumber != MAX_NUMBER)
		{
			currentNumber++;
		}
		else
		{
			currentNumber = OVERFLOW_PRIME;
		}

		return currentNumber;
	}
	
	/*---------------------------- isPrime ----------------------------
    |  Method isPrime ()
    |
    |  Purpose:  Determine whether the current number is a prime number.
    |  			 A prime number is a whole number that is only divisible 
    |			 by itself and 1. Any prime numbers found in the prime
    |			 sequence have their last digits processed.
    |
    |			 Formula: checks for 2 or 0, and returns true for either 
    |			 one. 2 is the only even prime, and 0, while not technically
    |			 prime, is accepted in the case of integer overflow. Then it
    |			 checks if the current number is divisible by 2 and returns
    |			 false if so. Lastly it runs a loop, testing every possible
    |			 factor that could be a factor of the current number, which
    |			 is every odd number from 3 to the square root of the current
    |			 number. If any of these factors divides evenly, it returns
    |			 false. If not, it returns true.
    |
    |			 Source: professorjava.weebly.com/isprime.html
    |
    |  @param	 
    |
    |  @return  true/false whether the current number is a prime number
    *-------------------------------------------------------------------*/
	public boolean isPrime()
	{
		if (currentNumber == 2 || currentNumber == 0)
	    {
	    	processLastDigit();
			return true;
	    }
		else if (currentNumber % 2 == 0)
	    {
	    	return false;
	    }
 
	    for (int factor = 3; factor <= Math.sqrt(currentNumber); factor += 2)
	    {
	        if(currentNumber % factor == 0)
	        {
	        	return false;
	        }
	    }
    	processLastDigit();
	    return true;
	}
	
	/*---------------------------- getCurrentNumber ----------------------------
    |  Method getCurrentNumber ()
    |
    |  Purpose:  To return the current number in the prime sequence.
    |
    |  @param	 
    |
    |  @return  the current number in the prime sequence
    *-------------------------------------------------------------------*/
	public int getCurrentNumber()
	{
		return currentNumber;
	}
	
	/*---------------------------- processLastDigit ----------------------------
    |  Method processLastDigit ()
    |
    |  Purpose:  To add the last digit of the current prime number to the array
    |			 counting the last digits. The last digit is isolated by taking
    |			 modulus 10 of the current number, which results in a remainder of
    |			 the singles-place value, which is the last digit of the number.
    |
    |			 Source:  Horstmann, Cay S. Big Java: Early Objects, 5th Edition.
    |					  	Wiley, 01/2013, pp.473. [Yuzu].
    |
    |  @param	 
    |
    |  @return  void
    *-------------------------------------------------------------------*/
	public void processLastDigit()
	{
	    int lastDigit = currentNumber % 10;
	    lastDigitCounters[lastDigit]++;
	} 
	
	/*---------------------------- getCurrentNumber ----------------------------
    |  Method getCurrentNumber (lastDigit)
    |
    |  Purpose:  To return the count of last digits for the specific last digit
    |			 input.
    |
    |  @param	 lastDigit the last digit for which the count is desired
    |
    |  @return  the count of how many primes in prime sequence had that last digit
    *-------------------------------------------------------------------*/
	public int getLastDigitCounter(int lastDigit)
	{
		return lastDigitCounters[lastDigit];
	}
	
	/*---------------------------- getLargestCounter ----------------------------
    |  Method getLargestCounter ()
    |
    |  Purpose:  To return the count of whichever last digit appeared in the prime
    |			 sequence most often.
    |
    |  @param	 
    |
    |  @return  the largest count of a last digit in the prime sequence
    *-------------------------------------------------------------------*/
	public int getLargestCounter()
	{

		int largestCounter = lastDigitCounters[0];
		for (int lastDigit = 1; lastDigit < DIGITS; lastDigit++)
		{
			if (lastDigitCounters[lastDigit] > largestCounter) {
				largestCounter = lastDigitCounters[lastDigit];
			}
		}
		
		return largestCounter;
	}
}