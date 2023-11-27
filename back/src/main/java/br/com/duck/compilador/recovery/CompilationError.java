package br.com.duck.compilador.recovery;

public class CompilationError {
	private String message;
	
	public CompilationError(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
		
	public void setMessage(String message) {
		this.message = message;
	}
}
