/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jeremiaMorling.utils.vector;

import java.util.Vector;

/**
 * 
 *
 * @author Jeremia Mörling
 */
public class Queue extends Vector {
    public Queue() {
    }
    
    public void push( Object o ) {
        addElement( o );
    }

    public Object pop() {
        Object result = elementAt( 0 );
        removeElementAt( 0 );
        return result;
    }
}
