package tests.ShortestPalindrome;

public class ShortestPalindrome {

	String s;
	
	public ShortestPalindrome(String s) {
		this.s = s;
	}
	
	public String findShortestPalindrome() {
        int sn = s.length();
        String rev = new StringBuilder(s).reverse().toString();
        String ss = s + rev + "#";
        int[] Next = new int[sn * 2 + 1];
        getNext(ss, Next);
        return rev.substring(0, sn - Next[sn * 2]) + s;
    }
    
    public void getNext(String ss, int[] Next) {
        int n = ss.length();
        Next[0] = -1;
        for (int i = 0; i < n - 1; i++) {
            int k = Next[i];
            while (k != -1 && ss.charAt(k) != ss.charAt(i)) {
                k = Next[k];
            }
            Next[i + 1] = k + 1;
        }
    }
}
