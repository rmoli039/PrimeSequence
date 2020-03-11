/*=============================================================================  
|   Source code:  SequenceDemo.java
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
| 		javac SequenceDemo.java PrimeSequence.java Sequence.java
|		java SequenceDemo firstNum numPrimes
|
|		The Sequence interface must be in the same directory as the source code.
|
|		The SequenceDemo program takes command line input to function. Specifically,
|		two integer values greater than 0 following the run command, separated by a 
|		delimiter. The first integer (firstNum) is the starting value for the 
|		PrimeSequence to be constructed. The sequence will then test every 
|		integer AFTER the starting value to see if it is prime. The second 
|		integer (numPrimes) determines how many prime numbers the sequence
|		should find before stopping.
| 
|  +-----------------------------------------------------------------------------  
|  
|  Description:  This program tests the implementation of the Sequence interface as well
|				 as the other methods of the PrimeSequence class. User input is taken 
|				 from the command-line, verified, and then used to construct a
|				 PrimeSequence object. The input determines the starting number for
|				 sequence and how many prime numbers it should search for. The program
|				 tests the prime sequencing and searching by displaying the desired prime
|				 numbers in a square formatted table. The program also tests the prime
|				 sequences collection of data by constructing a histogram that displays
|				 the distribution of each possible last digit for the primes found 
|				 in the sequence.
|  
|        Input:  The SequenceDemo program takes command line input to function. Specifically,
|				 two integer values greater than 0 following the run command, separated by a 
|				 delimiter. The first integer is the starting value for the PrimeSequence to
|				 be constructed. The sequence will then test every integer AFTER the starting
|				 value to see if it is prime. The second integer determines how many
|				 prime numbers the sequence should find before stopping.
|  
|       Output:  The program outputs the prime sequence requested in a square formatted 
|				 table, and then the program outputs the last digit count in the form
|				 of a histogram, with percentage values for each digit and a variable scale.
|  
|      Process:  The program's steps are as follows:  
|  
|                1.  The program validates the command line input, terminating if needed.
|                2.  The starting number and desired number of primes are parsed from the
|					 input Strings.
|                3.  The PrimeSequence object is constructed with the input starting number.
|                4.  The sequence is displayed using the displayTable method.
|                5.  The last digit data is displayed using the displayHistogram method.
|  
|   Required Features Not Included:  All required features are included.  
|  
|   Known Bugs:  None; the program operates correctly. 
|
|   Extra Credit:  Completed. The program avoids integer overflow by outputting
|				   0 for any additional requested primes over the maximum integer
|				   value.
|
|  *===========================================================================*/

import java.lang.Math;  	    //Allows the use of the round, sqrt, and ceil functions

public class SequenceDemo 
{

	public static void main(String[] args) 
	{
		
		validateInput(args);
		
		int startingNumber = Integer.parseInt(args[0]);
		int numberOfPrimes = Integer.parseInt(args[1]);

		PrimeSequence primeNumbers = new PrimeSequence(startingNumber);
		
		displayTable(primeNumbers, numberOfPrimes);
		displayHistogram(primeNumbers, numberOfPrimes);
	}

	/*---------------------------- validateInput ----------------------------
    |  Method validateInput ()
    |
    |  Purpose:  To validate command line input, terminating with an error
    |			 message if necessary. First, the method ensures that exactly
    |			 two values were input. Second, the method ensures that both
    |			 of those values are integers using the isInteger method. Last,
    |			 the method ensures that both integers are 1 or greater.
    |			 If any of those three tests fails, the system outputs an
    |			 error message explaining why it failed, and then terminates
    |			 the program.
    |
    |  @param	
    |
    |  @return  void
    *-------------------------------------------------------------------*/
	public static void validateInput(String[] inputStrings)
	{
		if (inputStrings.length != 2) {
	        System.out.print("Invalid input. Exactly 2 numbers are expected.");
	        System.exit(1);
		}
		
		if (!isInteger(inputStrings[0]) || !isInteger(inputStrings[1]))
		{
			System.out.print("Invalid input. Input should be 2 positive integers.");
	        System.exit(1);
		}
		
		if (Integer.parseInt(inputStrings[0]) < 1 || Integer.parseInt(inputStrings[1]) < 1)
		{
			System.out.print("Invalid input. Both numbers must be 1 or greater.");
	        System.exit(1);
		}
	}
	
	/*---------------------------- isInteger ----------------------------
    |  Method isInteger (inputString)
    |
    |  Purpose:  To test an input String to see if it is an integer. A loop
    |			 parses each character of the String and tests it to ensure
    |			 it is a digit (0-9). If any character in the String is not
    |			 a digit, the method returns false. Else, the method returns
    |			 true.
    |
    |			 Source: stackoverflow.com/questions/237159/whats-the-best-
    |			 		 way-to-check-if-a-string-represents-an-integer-in-java
    |
    |
    |  @param	 inputString the String to be tested
    |
    |  @return  true/false whether the input String is an integer
    *-------------------------------------------------------------------*/
	public static boolean isInteger(String inputString)
	{
	    int stringLength = inputString.length();
		
	    for (int position = 0; position < stringLength; position++)
	    {
	        char digit = inputString.charAt(position);
	        if (digit < '0' || digit > '9')
	        {
	            return false;
	        }
	    }
	    return true;
	}
	
	/*---------------------------- displayTable ----------------------------
    |  Method displayTable (primeNumbers, numberOfPrimes)
    |
    |  Purpose:  Prints a square formatted table of the prime numbers found
    |			 in the constructed prime sequence. The width of the table
    |			 is calculated by the number of columns, which is a rounded
    |			 value of the square root of the number of primes found. The
    |			 maximum number of columns is 10, wherein only the number of
    |			 rows will continue to grow.
    |
    |  @param	 primeNumbers the prime sequence object to utilize
    |
    |			 numberOfPrimes the number of primes to find
    |
    |  @return  void
    *-------------------------------------------------------------------*/
	public static void displayTable(PrimeSequence primeNumbers, int numberOfPrimes)
	{
		final int DEFAULT_COLUMNS = 1;
		final int MAX_COLUMNS = 10;
		
		System.out.printf("Printing a sequence of %d prime numbers after %d:%n"
						  , numberOfPrimes, primeNumbers.getCurrentNumber());
		
		int columns = DEFAULT_COLUMNS;

		if (numberOfPrimes >= (MAX_COLUMNS * MAX_COLUMNS))
		{
			columns = MAX_COLUMNS;
		}
		else if (numberOfPrimes > DEFAULT_COLUMNS)
		{
			columns = (int)Math.round(Math.sqrt(numberOfPrimes));
		}
		
		int primeCounter = 0;
		int columnCounter = 0;
		
		while (primeCounter < numberOfPrimes)
		{
			primeNumbers.next();
			if (primeNumbers.isPrime())
			{
				System.out.printf("%10d ",primeNumbers.getCurrentNumber());
				primeCounter++;
				columnCounter++;
			}
			
			if (columnCounter == columns)
			{
				columnCounter = 0;
				System.out.println("");
			}
		}
		
		System.out.printf("%n%n");
	}
	
	/*---------------------------- displayHistogram ----------------------------
    |  Method displayHistogram (primeNumbers, numberOfPrimes)
    |
    |  Purpose:  Prints a formatted histogram of the distribution of each possible
    |			 last digit for the primes found in the sequence. The scale of the
    |			 histogram is dynamically determined based on the largest single
    |			 percentage value a digit has. Percentages are rounded up, so that
    |			 even a single instance of a digit will result in 1% and a mark on
    |			 the graph. Every digit has its count displayed alongside the graph,
    |			 as well as the percent composition it makes up of the total count.
    |			 Under the graph, the totals are printed, the scale of the histogram
    |			 is given, and a disclaimer about accuracy is given.
    |
    |			 The scale for the histogram is determined by taking the largest
    |			 percentage for a single digit in the distribution, and dividing it
    |			 by the maximum number of asterisks that can be printed per line.
    |			 This number is then rounded up, resulting in the scale of how
    |			 many percentage points each asterisk represents on average.
    |
    |  @param	 primeNumbers the prime sequence object to utilize
    |
    |			 numberOfPrimes the number of primes to find
    |
    |  @return  void
    *-------------------------------------------------------------------*/
	public static void displayHistogram(PrimeSequence primeNumbers, int numberOfPrimes)
	{
		final double PERCENT = 100.0;
		final int DIGITS = 10;
		final int MAX_ASTERISKS = 25;
		int lastDigitPercentage = 0;
		int asteriskScale = (int)Math.ceil(((PERCENT * primeNumbers.getLargestCounter() 
											 / numberOfPrimes) / MAX_ASTERISKS));
		
		System.out.println("Last Digit Histogram:");
		
		for (int lastDigit = 0; lastDigit < DIGITS; lastDigit++)
		{
			int count = primeNumbers.getLastDigitCounter(lastDigit);
			
			lastDigitPercentage = (int)Math.ceil(PERCENT * count / numberOfPrimes);
			System.out.printf("[%d]", lastDigit);
			printAsterisks((int)Math.ceil((double)lastDigitPercentage / asteriskScale));
			System.out.printf("(%-6s%4d%%)%n", count + ",", lastDigitPercentage);
		}
		
		System.out.printf("%nTotal(actual count, %%)%17s%-7s100%%)%n%n"
						  , "(", numberOfPrimes + ",");
		System.out.printf("Scaled as %%, each * = %d%%%n", asteriskScale);
		System.out.printf("Total count may vary slightly from 100%% due to rounding%n%n");
	}
	
	/*---------------------------- printAsterisks ----------------------------
    |  Method printAsterisks (numberOfAsterisks)
    |
    |  Purpose:  Prints a line of asterisk with a length of the input value.
    |			 Also calculates the difference in spacing needed to fill
    |			 the rest of the histogram with a formula:
    |			 total spaces (35) - used spaces (numberOfAsterisks). Then
    |			 calls the printSpaces method with that calculated value.
    |
    |  @param	 numberOfAsterisks the number of asterisks to print
    |
    |  @return  void
    *-------------------------------------------------------------------*/
	public static void printAsterisks(int numberOfAsterisks)
	{
		final int HISTOGRAM_SPACES = 35;
		
		for (int counter = 0; counter < numberOfAsterisks; counter++)
		{
			System.out.print("*");
		}
		
		printSpaces(HISTOGRAM_SPACES - numberOfAsterisks);
	}
	
	/*---------------------------- printSpaces ----------------------------
    |  Method printSpaces (numberOfSpaces)
    |
    |  Purpose:  Prints a line of spaces with a length of the input value.
    |
    |  @param	 numberOfSpaces the number of spaces to print
    |
    |  @return  void
    *-------------------------------------------------------------------*/
	public static void printSpaces(int numberOfSpaces)
	{
		for (int counter = 0; counter < numberOfSpaces; counter++)
		{
			System.out.print(" ");
		}
	}
}