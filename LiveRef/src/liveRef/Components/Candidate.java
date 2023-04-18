package liveRef.Components;

import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class Candidate {
	
	public Range range;
	public List<ASTNode> nodes;
	public MethodDeclaration method;
	public int cc; // Cyclomatic Complexity 
	public int lcom; // Lack of Cohesion Metric

	public Candidate(Range range, List<ASTNode> nodes, MethodDeclaration method, int cc, int lcom) {
		this.range = range;
		this.nodes = nodes;
		this.method = method;
		this.cc = cc;
		this.lcom = lcom;
	}
	
	public int getCC() {
		return cc;
	}

	public int getLcom() {
		return lcom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(method, range);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Candidate))
			return false;
		Candidate other = (Candidate) obj;
		return Objects.equals(method, other.method) && Objects.equals(range, other.range);
	}
	
}
