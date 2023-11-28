package br.com.duck.compilador.recovery;

import java.util.ArrayList;
import java.util.List;

public class CompilationError {
	private List<String> messages = new ArrayList<String>();
	
	public void addError(String msg) {
		messages.add(msg);
	}
	
	public List<String> getErrors() {
		return this.messages;
	}
//	private int lineNumber;
	
//	public String getMessage() {
//		return this.message;
//	}
//	
//	public int getLineNumber() {
//		return this.lineNumber;
//	}
//	
//	public void setMessage(String msg) {
//		this.message = msg;
//	}	
//	
//	public void setLineNumber(int line) {
//		this.lineNumber = line;
//	}
//	
//	@Override
//    public String toString() {
//        return "CompilerError{" +
//                "message='" + message + '\'' +
//                ", lineNumber=" + lineNumber +
//                '}';
//    }
}
