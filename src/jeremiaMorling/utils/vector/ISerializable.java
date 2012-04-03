/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeremiaMorling.utils.vector;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Jeremia
 */
public interface ISerializable {
    public void toSerialFormat( DataOutputStream dataOutputStream );
    public ISerializable fromSerialFormat( DataInputStream dataInputStream );
}
