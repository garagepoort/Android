/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author ivarv
 */
public class GCMException extends Exception{
    
    public GCMException(){
        super();
    }
    
    public GCMException(String message) {
        super(message);
    }
    
    public GCMException(Throwable cause){
        super(cause);
    }
    
}
