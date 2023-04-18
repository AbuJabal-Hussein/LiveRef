package tests.NestedLoops;


public class NestedLoops {
	
	int[] nums;
	
	public NestedLoops(int[] nums) {
		this.nums = nums;
	}
	
	public void doSomthing() {
		StringBuilder sb = new StringBuilder();
		int count1 = 0, count2 = 0;
		for(int num: nums) {
			for(int i = 0; i < num; ++i) {
				sb.append(i);
				if(i > num/2) {
					sb.append(num-i);
					++count1;
				}
				++count2;
				if(count1 > count2) {
					sb.append(count1);
				}
				if(count1 < count2) {
					sb.append(count2);
				}
			}
		}
	}
	
}
