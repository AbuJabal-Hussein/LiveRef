package liveRef.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public abstract class Parser {
	
	private static ASTParser parser = ASTParser.newParser(ASTParser.K_COMPILATION_UNIT);
	private static CompilationUnit cu;
	
	private static ArrayList<MethodDeclaration> methods = new ArrayList<>();
	private static List<String> fields = new ArrayList<>();
	private static Set<String> accessedVars = new HashSet<>();
	
	public static void parse(String sourceCode) {
		parser.setSource(sourceCode.toCharArray());
		
		cu = (CompilationUnit) parser.createAST(null);
		methods = new ArrayList<>();
		fields = new ArrayList<>();
		accessedVars = new HashSet<>();
	}

	
	public static ArrayList<MethodDeclaration> getMethods() {
		if(methods.isEmpty()) {
			ASTVisitor cuVisitor = new ASTVisitor() {
				
				@Override
			    public boolean visit(MethodDeclaration node) {
					methods.add(node);
					return true;
				}
			};
			cu.accept(cuVisitor);
		}

		return methods;
	}
	
	public static List<String> getClassFields(ASTNode thisClass) {
		fields = new ArrayList<>();
		ASTVisitor fieldsVisitor = new ASTVisitor() {
			
			@Override
		    public boolean visit(FieldDeclaration node) {
				for(Object fieldObj: node.fragments()) {
					VariableDeclarationFragment field = (VariableDeclarationFragment) fieldObj;
					fields.add(field.getName().getIdentifier());
				}
				
				return true;
			}
		};
		thisClass.accept(fieldsVisitor);
		return fields;
	}
	
	public static Set<String> getStatementAccessedVars(Statement statement){
		accessedVars = new HashSet<>();
		
		ASTVisitor varsVisitor = new ASTVisitor() {
			
			@Override
		    public boolean visit(SimpleName node) {
	            String varName = node.getIdentifier();
	            accessedVars.add(varName);				
				return true;
			}
		};
		statement.accept(varsVisitor);
		return accessedVars;
	}
}
