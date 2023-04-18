package liveRef.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import liveRef.handlers.Parser;

import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.DoStatement;

import org.eclipse.jdt.core.dom.Block;


public abstract class Metrics {
	
	public static int getRating(Candidate candidate) {
		return calculateCC(candidate.nodes);
	}
	
	
	//calculate Cyclomatic Complexity
	public static int calculateCC(List<ASTNode> nodes) {
		int cc = 1;
		for(ASTNode node: nodes) {
			cc += calculateCC(node);
		}
		return cc;
	}
	
	private static int calculateCC(ASTNode node) {
		if(node == null) {
			return 0;
		}
		if(node.getNodeType() == ASTNode.IF_STATEMENT){
			IfStatement statement = (IfStatement) node;
			return 1 + calculateCC(statement.getThenStatement()) + calculateCC(statement.getElseStatement());
		}
		if(node.getNodeType() == ASTNode.WHILE_STATEMENT){
			WhileStatement statement = (WhileStatement) node;
			return 1 + calculateCC(statement.getBody());
		}
		if(node.getNodeType() == ASTNode.FOR_STATEMENT){
			ForStatement statement = (ForStatement) node;
			return 1 + calculateCC(statement.getBody());
		}
		if(node.getNodeType() == ASTNode.ENHANCED_FOR_STATEMENT){
			EnhancedForStatement statement = (EnhancedForStatement) node;
			return 1 + calculateCC(statement.getBody());
		}
		if(node.getNodeType() == ASTNode.DO_STATEMENT){
			DoStatement statement = (DoStatement) node;
			return 1 + calculateCC(statement.getBody());
		}
		
		if(node.getNodeType() == ASTNode.BLOCK) {
			Block block = (Block) node;
			int cc = 0;
			for(Object nodeObj: block.statements()) {
				Statement statement = (Statement) nodeObj;
				cc += calculateCC(statement);
			}
			return cc;
		}
		
		return 0;
	}


	public static int calculateLCOM(List<ASTNode> nodes, List<String> fieldDeclarations) {
		List<Set<String>> statements = new ArrayList<>();
		Set<String> allVars = new HashSet<>();
		for(ASTNode node: nodes) {
			Set<String> currVars = Parser.getStatementAccessedVars((Statement)node);
			currVars.retainAll(fieldDeclarations);
			statements.add(currVars);
			allVars.addAll(currVars);
		}
		int sharedVarsCount = 0;
		for(int i = 0; i < statements.size()-1; ++i) {
			for (int j = i; j < statements.size(); ++j) {
				Set<String> intersection = new HashSet<String>(statements.get(i));
				intersection.retainAll(statements.get(j));
				sharedVarsCount += intersection.size();
			}
		}

		return Math.abs(sharedVarsCount - allVars.size());
	}
	
}
