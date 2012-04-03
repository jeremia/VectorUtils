package jeremiaMorling.utils.vector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * Vector which is is possible to clone (by using the IClonable interface).
 *
 * @author Jeremia Mörling
 */
public class ClonableVector extends Vector implements IClonable {
    /**
     * Constructs an empty vector so that its internal data array has size 10 and its standard capacity increment is zero.
     */
    public ClonableVector() {
        super();
    }

    /**
     * Constructs an empty vector with the specified initial capacity and with its capacity increment equal to zero.
     * @param initialCapacity the initial capacity of the vector.
     */
    public ClonableVector( int initialCapacity ) {
        super( initialCapacity );
    }

    /**
     * Adds an IClonable element to the Vector.
     * @param o The element to add.
     * @throws IllegalArgumentException If the element is not of type IClonable.
     */
    public void addElement( Object o ) throws IllegalArgumentException {
        if( !(o instanceof IClonable) )
            throw new IllegalArgumentException( "Added element is not of required type IClonable, but of type " + o.getClass().getName() );

        super.addElement( o );
    }

    /**
     * Returns the element at the specified index.
     * @param index The index for the element to retrieve.
     * @return The element at the specified index.
     */
    public IClonable getClonable( int index ) {
        return (IClonable)elementAt( index );
    }

     /**
     * Returns a deep copy of the Vector.
     * @return A deep copy of the Vector.
     */
    public IClonable clone() {
        try {
            //ClonableVector clone = new ClonableVector( size() );
            ClonableVector clone = (ClonableVector)getClass().newInstance();
            
            for ( int i = 0; i < size(); i++ ) {
                clone.addElement( getClonable( i ).clone() );
            }

            return clone;
        } catch ( Exception e ) {
            return null;
        }
    }
    
    /**
     * Removes the last element of the vector
     */
    public void removeLastElement() {
        if( size() == 0 )
            return;
        else
            removeElementAt( size()-1 );
    }
    
    /**
     * Inserts the element first in the list.
     * @param element The element to insert.
     */
    public void insertElementFirst( IClonable element ) {
        insertElementAt( element, 0 );
    }
    
    /**
     * Checks if this vector is equal to the specified vector.
     * @param o The vector to compare with this vector.
     * @return true if both size() are equal and all contained objects are equal. false otherwise.
     */
    public boolean equals( Object o ) {
        if( o == null || !(o instanceof ClonableVector) )
            return false;
        
        ClonableVector vectorToCompare = (ClonableVector)o;
        
        if( elementCount != vectorToCompare.elementCount )
            return false;
        
        for( int i=0; i<elementCount; i++ ) {
            if( !elementAt( i ).equals( vectorToCompare.elementAt( i ) ) )
                return false;
        }
        
        return true;
    }
    
    /**
     * Persists all elements in the vector that are ISerializable.
     * @param rsName The name of the RecordStore where the elements will be persisted.
     * @throws RecordStoreException If there is a problem with the RecordStore.
     * @throws IOException If there is a problem with the output streams used to create the record.
     */
    protected void persist( String rsName ) throws RecordStoreException, IOException {
        RecordStore rs = RecordStore.openRecordStore( rsName, true );

        // Delete any old records
        RecordEnumeration recordEnumeration = rs.enumerateRecords( null, null, false );
        while( recordEnumeration.hasNextElement() ) {
            int recordId = recordEnumeration.nextRecordId();
            rs.deleteRecord( recordId );
        }
        recordEnumeration.destroy();

        byte[] record;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream( outputStream );
        for( int i=0; i<size(); i++ ) {
            Object o = elementAt( i );
            if( o instanceof ISerializable )
                ((ISerializable)o).toSerialFormat( dataOutputStream );
        }

        dataOutputStream.flush();
        record = outputStream.toByteArray();
        
        if( record != null && record.length > 0 )
            rs.addRecord( record, 0, record.length );

        outputStream.close();
        dataOutputStream.close();
        rs.closeRecordStore();
    }
    
    /**
     * Loads the list from the RecordStore with the specified name.
     * @param rsName The name of the RecordStore where the list is persisted.
     * @param dataClass The datatype of the elements to load.
     * @throws RecordStoreException If there is a problem with the record store.
     * @throws IOException If there is a problem with the inputstreams used to read the record.
     * @throws InstantiationException If a default constructor for the specified dataClass is missing.
     * @throws IllegalAccessException If the default constructor for the specified dataClass isn't public.
     */
    protected void loadPersisted( String rsName, Class dataClass ) throws RecordStoreException, IOException, InstantiationException, IllegalAccessException {
        if( !(ISerializable.class.isAssignableFrom( dataClass ) ) )
            throw new IllegalArgumentException( "dataClass has to be of type ISerializable, but was of type " + dataClass.getName() );
        
        RecordStore rs = RecordStore.openRecordStore( rsName, true );
        RecordEnumeration recordEnumeration = rs.enumerateRecords( null, null, false );

        if( !recordEnumeration.hasNextElement() )
            return;

        byte[] record = recordEnumeration.nextRecord();
        ByteArrayInputStream inputStream = new ByteArrayInputStream( record );
        DataInputStream dataInputStream = new DataInputStream( inputStream );
        while( dataInputStream.available() > 0 ) {
            ISerializable serializable = (ISerializable)dataClass.newInstance();
            serializable.fromSerialFormat( dataInputStream );
            addElement( serializable );
        }

        inputStream.close();
        dataInputStream.close();
        recordEnumeration.destroy();
        rs.closeRecordStore();
    }
}
