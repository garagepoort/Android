/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author ivarv
 */
public class DatabaseException extends Exception{
    
    public DatabaseException(){
        super();
    }
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(Throwable cause){
        super(cause);
    }
}
