package tests.FindStringAnagrams;

import java.util.List;
import java.util.ArrayList;

public class FindStringAnagrams {

	private String s;
	private String p;
	
	public FindStringAnagrams(String s, String p) {
		this.s = s;
		this.p = p;
	}

	public List<Integer> find() {
        if(p.length() > s.length()){
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        
        int[] hist = new int[26];
        for(char c: p.toCharArray()){
            hist[c - 'a']++;
        }

        int pLen = p.length(), sLen = s.length();
        
        for(int i = 0; i < sLen - pLen + 1; ++i){
            if(hist[s.charAt(i) - 'a'] > 0){
            	int[] hc = hist.clone();
        		int j = i;
        		
        		while(j < i + pLen && hc[s.charAt(j) - 'a'] > 0){
        		    hc[s.charAt(j) - 'a']--;
        		    ++j;
        		}
                if(j == i + pLen){
                    res.add(i);
                }
            }
        }
        return res;
    }


	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}
}
