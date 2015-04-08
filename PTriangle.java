import java.util.*;

//this program should work for primes less than the square root of the max value of int (aprox 65k)
public class PTriangle {

	private int startRow, endRow,prime;
	private int[][] triangleElements;
	
	
	public int getPrime()
	{
		return prime;
	}
	public PTriangle(int startR, int endR, int prime)
	{
		getTriangle(startR,endR,prime);
	}

	public PTriangle() {
	}

	// the top row is considered row 1
	void getTriangle(int startRow, int endRow, int p) {
		
		this.startRow = startRow;
		this.endRow = endRow;
		this.prime= p;
		
		triangleElements = new int[endRow - startRow + 1][];
		// calculate the initial row

		triangleElements[0] = new int[(startRow + 1) / 2];
		if (startRow == 1) {
			triangleElements[0][0] = 1;
		} else {
			PTriangle smallTriangle = new PTriangle(1,p,p);
			triangleElements[0][0] = 1;
			for (int i = 1; i <= (startRow-1) / 2; i++) {
				triangleElements[0][i] = smallTriangle.chooseModFromLucas(startRow, i);
			}
		}

		// calculate everything else based on the initial row
		for (int i = 1; i <= endRow - startRow; i++) { // i is the row
			triangleElements[i] = new int[(startRow + i + 1) / 2];
			triangleElements[i][0] = 1;
			for (int j = 1; j < (startRow + i) / 2; j++) {
				triangleElements[i][j] = (triangleElements[i - 1][j] + triangleElements[i - 1][j - 1])
						% p;
			}
			if (startRow + i % 2 == 1) {
				triangleElements[i][(startRow + i) / 2] = (2 * triangleElements[i - 1][((startRow + i) / 2) - 1]) % p;
			}
		}
	}
	
	int multiplicity(long m, long n)
	{
		return (sumOfBaseExpansion(n) + sumOfBaseExpansion(m - n) - sumOfBaseExpansion(m)) / (prime - 1);
	}
	
	private int sumOfBaseExpansion(long m)
	{
		int sum = 0;
		while(m > 0)
		{
			sum += m%prime;
			m /= prime;
		}
		return sum;
	}
	
	//this PTriangle must contains rows 1-p to get the right answer
	int chooseModFromLucas(long m, long n)
	{
		if (n > m / 2)
			return chooseModFromLucas(m, m-n);
		// use luca's theorem
		int equiv = 1;
		int m0;
		int n0;
		while (n > 0) {
			m0 = (int)(m % prime);
			n0 = (int)(n % prime);
			if (n0 > m0) {
				equiv = 0;
				break;
			}
			equiv *= chooseMod(m0, n0);
			equiv %= prime;
			if (equiv == 0)
				break;
			m /= prime;
			n /= prime;
		}
		return equiv;
	}

	// must be within startRow and endRow and in the triangle
	long chooseMod(int n, int r) {
		if (r <= n / 2)
			return triangleElements[n - (startRow - 1)][r];
		else
			return triangleElements[n - (startRow - 1)][n - r];

	}
}
