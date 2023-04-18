package tests.NoSuggestions;

public class NoSuggestions {
	public int x;
	public int y;
	
	public NoSuggestions(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void swap(){
		int tmp = x;
		x = y;
		y = tmp;
	}
	
	public int sumNTimesOfMultiplication(int n) {
		int sum = 0;
		int mul = x * y;
		for(int i = 0; i < n; ++i) {
			sum += mul;
		}
		return sum;
	}
	
	public void sumAndMulArray(int[] arr) {
		x = 0;
		y = 1;
		for(int num: arr) {
			x += num;
			y *= y;
		}
	}
}
