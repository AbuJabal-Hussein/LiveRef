package liveRef.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.*;

import liveRef.handlers.Parser;

public class LiveRef {
	
	private List<Candidate> candidates;
	private int minStatementsForRefactoring = 3;
	private float maxStatementsPercentForRefactoring = 0.749f;
	
	public LiveRef() {
		this.candidates = new ArrayList<>();
	}

	//This is the implementation of Algorithm 1 in the paper
	public void findExtractMethodCandidates(String sourceCode) {
		Parser.parse(sourceCode);
		
		//stage 1
		ArrayList<MethodDeclaration> methods = Parser.getMethods();
		
		//stage 1, 2, 3, 4 combined
		for(MethodDeclaration method: methods) {
			if(method.getBody() != null) {		
				List<String> fieldDeclarations = Parser.getClassFields(method.getParent());
				boolean blockIsMethodBody = true;
				Queue<ASTNode> q = new LinkedList<>();
				q.add(method.getBody());
				while(!q.isEmpty()) {
					ASTNode curr = q.poll();
					switch(curr.getNodeType()) {
						case ASTNode.BLOCK: {
							Block blk = (Block) curr;
							List<ASTNode> newBlk = new ArrayList<>(); 
							
							for(Object stmntObj: blk.statements()) {
								Statement statement = (Statement)stmntObj;
								newBlk.add(statement);
								q.add(statement);
							}
						
							extractCandidatesOfStatements(newBlk, method, fieldDeclarations, blockIsMethodBody);
						}break;
						case ASTNode.IF_STATEMENT:{
							IfStatement statement = (IfStatement) curr;
							if(statement.getThenStatement() != null) {
								q.add(statement.getThenStatement());
							}
							if(statement.getElseStatement() != null) {
								q.add(statement.getElseStatement());
							}
						}break;
						case ASTNode.WHILE_STATEMENT:{
							WhileStatement statement = (WhileStatement) curr;
							if(statement.getBody() != null) {
								q.add(statement.getBody());
							}
						}break;
						case ASTNode.FOR_STATEMENT:{
							ForStatement statement = (ForStatement) curr;
							if(statement.getBody() != null) {
								q.add(statement.getBody());
							}
						}break;
						case ASTNode.ENHANCED_FOR_STATEMENT:{
							EnhancedForStatement statement = (EnhancedForStatement) curr;
							if(statement.getBody() != null) {
								q.add(statement.getBody());
							}
						}break;
						case ASTNode.DO_STATEMENT:{
							DoStatement statement = (DoStatement) curr;
							if(statement.getBody() != null) {
								q.add(statement.getBody());
							}
						}break;
					}
					blockIsMethodBody = false;
				}
				
			}
		}
		
		candidates.sort((candidate1, candidate2) -> {
			if(candidate1.cc < candidate2.cc) {
				return -1;
			}
			if(candidate1.cc > candidate2.cc) {
				return 1;
			}
			if(candidate1.lcom < candidate2.lcom) {
				return -1;
			}
			if(candidate1.lcom > candidate2.lcom) {
				return 1;
			}
			return candidate2.nodes.size() - candidate1.nodes.size();
		});
	}

	private void extractCandidatesOfStatements(List<ASTNode> statements, MethodDeclaration method,
			List<String> fieldDeclarations, boolean blockIsMethodBody) {
		
		int maxStatementsNum;
		if(blockIsMethodBody) {
			maxStatementsNum = (int) (maxStatementsPercentForRefactoring * statements.size());
		}else {
			maxStatementsNum = statements.size();
		}
		
		for(int windowSize = minStatementsForRefactoring; windowSize <= maxStatementsNum; ++windowSize) {
			for(int windowStart = 0; windowStart + windowSize < statements.size(); ++windowStart) {
				List<ASTNode> nodes = statements.subList(windowStart, windowStart + windowSize);
				ASTNode firstStatement = nodes.get(0);
				ASTNode lastStatement = nodes.get(nodes.size()-1);
				Range range = new Range(firstStatement.getStartPosition(), lastStatement.getStartPosition() + lastStatement.getLength());
				int cc = Metrics.calculateCC(nodes);
				int lcom = Metrics.calculateLCOM(nodes, fieldDeclarations);

				Candidate candidate = new Candidate(range, nodes, method, cc, lcom);
				
				candidates.add(candidate);
			}
		}
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}
	

}
