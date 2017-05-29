package org.emerjoin.jarex;

/**
 * @author Mário Júnior
 */
public class JarexException extends RuntimeException {

    public JarexException(String message){
        super(message);
    }

    public JarexException(String message, Throwable cause){
        super(message,cause);
    }

}
