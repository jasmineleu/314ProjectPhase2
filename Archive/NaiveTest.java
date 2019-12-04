import java.math.BigInteger;

public class NaiveTest
{
	public static boolean isPrime(BigInteger candidate)
	{
		if (!candidate.isProbablePrime((100))) return false; // Weed out the likely not primes.
		
		BigInteger loopStop = candidate.sqrt().add(BigInteger.ONE);
		for (BigInteger outerIndex = BigInteger.TWO; !outerIndex.equals(loopStop); outerIndex = outerIndex.add(BigInteger.ONE))
		{
			if(candidate.mod(outerIndex).equals(BigInteger.ZERO)) { return false; }
		}
		return true;
	}
}
