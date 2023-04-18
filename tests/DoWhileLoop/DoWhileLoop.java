package tests.DoWhileLoop;

public class DoWhileLoop {
	
	private double[] num1;
	private double[] num2;
	
	public DoWhileLoop(double[] num1, double[] num2) {
		super();
		this.num1 = num1;
		this.num2 = num2;
	}
	
	public double EuclideanDist() {
		
		if(num1.length == 0 || num2.length == 0) {
			return 0;
		}
		int i = 0;
		double dist = 0;
		do {
			double diff;
			diff = num1[i] - num2[i];
			if(diff < 0) {
				diff = -diff;
			}
			double squared = diff * diff;
			dist += squared;
		}
		while(i < num1.length);
		
		dist = Math.sqrt(dist);
		return dist;
	}

}
