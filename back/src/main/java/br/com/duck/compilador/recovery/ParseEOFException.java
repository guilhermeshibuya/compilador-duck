package br.com.duck.compilador.recovery;

public class ParseEOFException extends Exception {
    public ParseEOFException(String x) {
        super(x);
    }
}
