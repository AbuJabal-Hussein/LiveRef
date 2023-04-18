package liveRef.Components;

import java.util.Objects;

public class Range {
	public int start;
	public int end;
	
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Range))
			return false;
		Range other = (Range) obj;
		return start == other.start && end == other.end;
	}

	@Override
	public String toString() {
		return "Range [start=" + start + ", end=" + end + "]";
	}


}
