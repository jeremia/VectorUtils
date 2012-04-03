/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jeremiaMorling.utils.vector;

/**
 * 
 *
 * @author Jeremia Mörling
 */
public class IntVector extends SortableVector {
    public IntVector() {}
    
    public IntVector( int initialCapacity ) {
        super( initialCapacity );
    }
    
    public void addElement( Object o ) {
        if( !(o instanceof ComparableInteger) )
            throw new IllegalArgumentException( "o has to be of type ComparableInteger, but is of type " + o.getClass().getName()  );

        super.addElement( o );
    }

    public void addElement( int element ) {
        addElement( new ComparableInteger( element ) );
    }

    public void insertElementAt( Object o, int index ) {
        if( !(o instanceof IComparable) )
            throw new IllegalArgumentException( "Added objects has to be of type ComparableInteger. Object was of type " + o.getClass().getName() );

        super.insertElementAt( o, index );
    }

    public int getInt( int index ) {
        return ((ComparableInteger)elementAt( index )).value;
    }
    
    public boolean contains( int value ) {
        for( int i=0; i<elementCount; i++ ) {
            if( getInt( i ) == value )
                return true;
        }
        
        return false;
    }

    public class ComparableInteger implements IComparable {
        private int value;

        private ComparableInteger( int value ) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int compareTo( Object itemToCompare ) throws IllegalArgumentException {
            if( itemToCompare instanceof ComparableInteger ) {
                return compareInt( ((ComparableInteger)itemToCompare).value );
            } else if( itemToCompare instanceof Integer ) {
                return compareInt( ((Integer)itemToCompare).intValue() );
            } else {
                throw new IllegalArgumentException( "comparable has to be of type ComparableInteger, but is of type " + itemToCompare.getClass().getName() );
            }
        }
        
        private int compareInt( int intToCompare ) {
            if( value < intToCompare )
                return -1;
            else if( value > intToCompare )
                return 1;
            else
                return 0;
        }

        public IClonable clone() {
            ComparableInteger clone = new ComparableInteger( value );
            return clone;
        }
        
        public boolean equals( Object o ) {
            if( o instanceof ComparableInteger )
                return (value == ((ComparableInteger)o).value);
            else if( o instanceof Integer )
                return (value == ((Integer)o).intValue());
            else
                return false;
        }
    }
    
    public int[] toIntArray() {
        int[] result = new int[elementCount];
        for( int i=0; i<elementCount; i++ )
            result[i] = getInt( i );
        
        return result;
    }
}
