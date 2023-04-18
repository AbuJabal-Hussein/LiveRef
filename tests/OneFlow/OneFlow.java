package tests.OneFlow;

public class OneFlow {
	public int x;
	public int y;
	public String myStr;
	
	public OneFlow(int x, int y, String myStr) {
		this.x = x;
		this.y = y;
		this.myStr = myStr;
	}
	
	public void foo() {
		int tmp = x;
		x = y;
		y = tmp;
		x += 7;
		y += 10;
		System.out.println(myStr);
	}
	
	public int bar() {
		int[] arr = new int[4];
		arr[0] = x+1;
		arr[1] = x+2;
		arr[2] = x+3;
		arr[3] = x+4;
		int res = 0;
		res = arr[0] + arr[2];
		res = res - arr[1] - arr[3];
		return res;
	}
}
