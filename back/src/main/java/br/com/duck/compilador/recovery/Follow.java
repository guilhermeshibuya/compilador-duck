package br.com.duck.compilador.recovery;

import br.com.duck.compilador.parser.*;

public class Follow { //implementa os conjuntos first p/ alguns n.terminais

    static public final RecoverySet start = new RecoverySet();
    static public final RecoverySet statement = new RecoverySet();
    static public final RecoverySet variableDeclaration = new RecoverySet();
    static public final RecoverySet variableDeclarations = variableDeclaration;
    static public final RecoverySet assignment = variableDeclaration;
    static public final RecoverySet multiVariableAssignment = variableDeclaration;
    static public final RecoverySet conditional = variableDeclaration;
    static public final RecoverySet elseNT = conditional;
    static public final RecoverySet elseIf = conditional;
    static public final RecoverySet loop = conditional;
    static public final RecoverySet type = new RecoverySet();
    static public final RecoverySet expression = new RecoverySet();
    static public final RecoverySet orExpression = expression;
    static public final RecoverySet andExpression = orExpression;
    static public final RecoverySet equalityExpression = andExpression;
    static public final RecoverySet relationalExpression = equalityExpression;
    static public final RecoverySet additiveExpression = relationalExpression;
    static public final RecoverySet term = additiveExpression;
    static public final RecoverySet factor = term;
    static public final RecoverySet semicolon = statement;
    static public final RecoverySet identifier = variableDeclarations.union(factor);
    static public final RecoverySet assign = new RecoverySet();
    static public final RecoverySet comma = new RecoverySet();
    static public final RecoverySet openParen = assign;
    static public final RecoverySet closeParen = factor;
    static public final RecoverySet openCurly = statement.remove(Compilador.EOF_TOKEN);
    static public final RecoverySet closeCurly = conditional;
    static public final RecoverySet ifT = new RecoverySet();
    static public final RecoverySet elseT = new RecoverySet();
    static public final RecoverySet elseIfT = ifT;
    static public final RecoverySet whileT = ifT;
    static public final RecoverySet forT = ifT;
    static public final RecoverySet intT = type;
    static public final RecoverySet floatT = type;
    static public final RecoverySet stringT = type;
    static public final RecoverySet or = new RecoverySet();
  static public final RecoverySet and = or;
  static public final RecoverySet equals = or;
  static public final RecoverySet lessThan = or;
  static public final RecoverySet greaterThan = or;
  static public final RecoverySet plus = or;
  static public final RecoverySet minus = or;
  static public final RecoverySet times = or;
  static public final RecoverySet divided = or;
//    static public final RecoverySet or = expression;
//    static public final RecoverySet and = expression;
//    static public final RecoverySet equals = expression;
//    static public final RecoverySet lessThan = expression;
//    static public final RecoverySet greaterThan = expression;
//    static public final RecoverySet plus = expression;
//    static public final RecoverySet minus = expression;
//    static public final RecoverySet times = expression;
//    static public final RecoverySet divided = expression;
    static public final RecoverySet number = factor;
    
    static {
    	start.add(new Integer(Compilador.EOF_TOKEN));
    	
    	statement.add(new Integer(Compilador.EOF_TOKEN));
    	statement.add(new Integer(Compilador.INT));
    	statement.add(new Integer(Compilador.FLOAT));
    	statement.add(new Integer(Compilador.STRING));
    	statement.add(new Integer(Compilador.ID));
    	statement.add(new Integer(Compilador.IF));
    	statement.add(new Integer(Compilador.WHILE));
    	statement.add(new Integer(Compilador.FOR));
    	statement.add(new Integer(Compilador.CLOSE_CURLY));

		variableDeclaration.add(new Integer(Compilador.SEMICOLON));
		
		type.add(new Integer(Compilador.ID));
		
		expression.add(new Integer(Compilador.CLOSE_PAREN));
		expression.add(new Integer(Compilador.COMMA));
		expression.add(new Integer(Compilador.SEMICOLON));
		
		andExpression.add(new Integer(Compilador.OR));
		
		equalityExpression.add(new Integer(Compilador.AND));
		
		relationalExpression.add(new Integer(Compilador.EQUALS));
		
		additiveExpression.add(new Integer(Compilador.LESS_THAN));
		additiveExpression.add(new Integer(Compilador.GREATER_THAN));
		
		term.add(new Integer(Compilador.PLUS));
		term.add(new Integer(Compilador.MINUST));
		
		factor.add(new Integer(Compilador.TIMES));
		factor.add(new Integer(Compilador.DIVIDED));
		
		identifier.add(new Integer(Compilador.ASSIGN));
		identifier.add(new Integer(Compilador.COMMA));
		
		assign.add(new Integer(Compilador.NUM));
		assign.add(new Integer(Compilador.ID));
		assign.add(new Integer(Compilador.OPEN_PAREN));
			
		comma.add(new Integer(Compilador.ID));
		
		closeParen.add(new Integer(Compilador.OPEN_CURLY));
		
		openCurly.add(new Integer(Compilador.CLOSE_CURLY));
		
		closeCurly.add(new Integer(Compilador.ELSE));	
		closeCurly.add(new Integer(Compilador.ELSEIF));
		
		ifT.add(new Integer(Compilador.OPEN_PAREN));
		
		elseT.add(new Integer(Compilador.OPEN_CURLY));
		
		or.add(new Integer(Compilador.NUM));
		or.add(new Integer(Compilador.ID));
		or.add(new Integer(Compilador.OPEN_PAREN));
    }
}
