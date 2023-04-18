package tests.LongWhileLoop;

import java.util.List;

public class LongWhileLoop {
	
	private List<Integer> nums;
	private int left;
	private int right;
	
	public LongWhileLoop(List<Integer> nums, int left, int right) {
		super();
		this.nums = nums;
		this.left = left;
		this.right = right;
	}

	public int foo(int l, int r, int num) {
		if(l < left) {
			return -1;
		}
		if(r > right) {
			return -1;
		}
		right = r;
		left = l;
		int mid;
		while(l <= r) {
			mid = (l+r)/2;
			if(nums.get(mid) == num) {
				return mid;
			}
			if(nums.get(mid) > num) {
				l = mid+1;
			}
			if(nums.get(mid) < num) {
				r = mid-1;
			}
			System.out.println("left = " + left);
			System.out.println("right = " + right);
			System.out.println("mid = " + mid);
		}
		
		return -1;
	}
	
}
