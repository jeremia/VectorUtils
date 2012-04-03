package jeremiaMorling.utils.vector;

/**
 * This class contains algorithms to sort a Vector using MergeSort.
 * In order to obtain this the method subVector is also implemented.
 *
 * @author Jeremia Mörling
 */
public class SortableVector extends ClonableVector {
    //private boolean isSorted = false;

    /**
     * Creates a SortableVector.
     */
    public SortableVector() {
        super();
    }

    /**
     * Creates a SortableVector with the specified initial capacity.
     *
     * @param initialCapacity Initial capacity for the SortedVector.
     */
    public SortableVector( int initialCapacity ) {
        super( initialCapacity );
    }

    /**
     * Sorts the Vector using mergesort.
     */
    public void sort()  {
        /*if( isSorted )
            return;*/

        // If size > 2. Divide Vector in the middle. Sort each part and merge them.
        if ( elementCount > 2 ) {
            int middleIndex = elementCount / 2;
            SortableVector subVectorLeft = subVectorLeft( middleIndex );
            SortableVector subVectorRight = subVectorRight( middleIndex );
            subVectorLeft.sort();
            subVectorRight.sort();
            merge( subVectorLeft, subVectorRight );
        }

        // If size == 2. Compare the elements and return a new Vector with correct order.
        else if( elementCount == 2 ) {
            IComparable firstComparable = getComparable( 0 );
            IComparable secondComparable = getComparable( 1 );
            int comparison = firstComparable.compareTo( secondComparable );
            if( comparison > 0 ) {
                replace( 0, secondComparable );
                replace( 1, firstComparable );
            }
        }

        //isSorted = true;
    }


    /**
     * Internal function used for sort()
     *
     * @param vectorA A sorted Vector.
     * @param vectorB A sorted Vector.
     * @return A new sorted Vector with all elements from both vectorA and vectorB.
     */
    private void merge( SortableVector vectorA, SortableVector vectorB ) {
        // One index pointer per vector
        int indexA = 0;
        int indexB = 0;

        // Temp variables to store one element from each Vector to compare.
        IComparable elementA;
        IComparable elementB;

        // Variable used to store result of comparison between two elements
        int comparison;

        // Merge
        for( int i=0; i<elementCount; i++ ) {
            // If pointer for vectorA is out of range, just add element from vectorB.
            if( indexA >= vectorA.elementCount ) {
                replace( i, vectorB.getComparable( indexB ) );
                indexB++;
            }
            // ...and vice versa...
            else if( indexB >= vectorB.elementCount ) {
                replace( i, vectorA.getComparable( indexA ) );
                indexA++;
            }
            // If both index pointers are within range a comparison between the elements at the index pointers has to be made.
            else {
                elementA = vectorA.getComparable( indexA );
                elementB = vectorB.getComparable( indexB );
                comparison = elementA.compareTo( elementB );
                if( comparison <= 0 ) {
                    replace( i, elementA );
                    indexA++;
                }
                else {
                    replace( i, elementB );
                    indexB++;
                }
            }
        }
    }

    /**
     * Returns a new vector which contains the elements from startIndex to (and including) endIndex.
     * 
     * @param startIndex The first index to include.
     * @param endIndex The last index to include.
     * @return The new sub vector with objects from startIndext to endIndex.
     * @throws IllegalArgumentException If startIndex and/or endIndex are incorrect.
     */
    public SortableVector subVector( int startIndex, int endIndex ) throws IllegalArgumentException, IndexOutOfBoundsException {
        // Check parameters
        if ( startIndex > endIndex )
            throw new IllegalArgumentException( "startIndex (" + startIndex + ") > endIndex (" + endIndex + ")" );
        else if ( startIndex < 0 )
            throw new IndexOutOfBoundsException( "startIndex (" + startIndex + ") < 0" );
        else if ( endIndex >= elementCount )
            throw new IndexOutOfBoundsException( "endIndex (" + endIndex + ") >= size + (" + elementCount + ")" );

        SortableVector result = new SortableVector( endIndex - startIndex + 1 );
        for ( int i=startIndex; i <= endIndex; i++ )
            result.addElement( getComparable(i) );

        return result;
    }

    private SortableVector subVectorLeft( int index ) {
        return subVector( 0, index-1 );
    }

    private SortableVector subVectorRight( int index ) {
        return subVector( index, elementCount-1 );
    }

    /**
     * Adds a Vector to the end of this Vector.
     * 
     * @param vectorToAdd A Vector to be added to the end of this Vector.
     * @return A new Vector beginning with this Vector and ending with the elements from vectorToAdd.
     */
    public void addVector( SortableVector vectorToAdd ) {
        ensureCapacity( elementCount + vectorToAdd.elementCount );

        // Add elements from paramter Vector.
        for( int i=0; i<vectorToAdd.elementCount; i++ )
            addElement( vectorToAdd.getComparable( i ) );

        //isSorted = false;
    }

    /**
     * Replaces the object at the specified index.
     *
     * @param index The index for the object to replace.
     * @param item The object to replace the current object at the specified index.
     * @throws IndexOutOfBoundsException If specified index is < 0 or > sice().
     */
    public void replace( int index, IComparable item ) throws IndexOutOfBoundsException {
        if( index < 0 )
            throw new IndexOutOfBoundsException( "index (" + index + ") < 0" );
        else if( index >= elementCount )
            throw new IndexOutOfBoundsException( "index (" + index + ") >= elementCount(" + elementCount + ")" );

        elementData[ index ] = item;

        //isSorted = false;
    }

    /*private void replace( int index, Object item ) throws IllegalArgumentException {
        if( !(item instanceof Comparable) )
            throw new IllegalArgumentException( "Item is not of type Comparable." );
        
        replace( index, (Comparable)item );
    }*/

    /**
     * Adds an element of type Comparable to the Vector.
     * @param o The Comparable element to add.
     * @throws IllegalArgumentException If element is not of type Comparable.
     */
    public void addElement( Object o ) throws IllegalArgumentException {
        if( !(o instanceof IComparable) )
            throw new IllegalArgumentException( "Added objects has to be of type IComparable. Object was of type " + o.getClass().getName() );

        super.addElement( o );

        //isSorted = false;
    }

    /**
     * Inserts the specified object as a component in this vector at the specified index. Each component in this vector with an index greater or equal to the specified index is shifted upward to have an index one greater than the value it had previously.
     * The index must be a value greater than or equal to 0 and less than or equal to the current size of the vector. (If the index is equal to the current size of the vector, the new element is appended to the Vector.)
     * This method is identical in functionality to the add(Object, int) method (which is part of the List interface). Note that the add method reverses the order of the parameters, to more closely match array usage.
     * @param obj the component to insert.
     * @param index where to insert the new component.
     */
    public void insertElementAt( Object obj, int index ) {
        if( !(obj instanceof IComparable) )
            throw new IllegalArgumentException( "Added objects has to be of type IComparable. Object was of type " + obj.getClass().getName() );

        super.insertElementAt( obj, index );

        //isSorted = false;
    }

    /**
     * Gets an element from the Vector.
     * @param index The index of the element to return.
     * @return The element in the Vector pointed out by index.
     */
    public IComparable getComparable( int index ) {
        return (IComparable)elementAt( index );
    }

    /**
     * If the Vector is not sorted, this method will simply call super.indexOf( o ).
     * If the Vector is sorted, a binary search will be performed.
     * @param o The object to search for.
     * @return The index of the object to search for, or -1 if it was not found.
     */
    /*public int indexOf( Object o ) {
        if( !(o instanceof IComparable) )
            return -1;

        if( !isSorted )
            return super.indexOf( o );
        else {
            return binarySearch( (IComparable)o, 0, size()-1 );
        }
    }*/

    /**
     * Performs a binary search for the comparable. Beware! This only works if the vector is sorted.
     * Even if the vector has been sorted, any of the contained objects may have changed, which the vector cannot discover.
     * So it us up to the user of this SortableVector to make sure that the vector is sorted when calling binarySearch.
     * @param c The comparable to find.
     * @return The index for the comparable, or -1 if the comparable was not found.
     */
    public int binarySearch( IComparable c ) {
        return binarySearch( c, true );
    }
    
    /**
     * Performs a binary search for the comparable. Beware! This only works if the vector is sorted.
     * Even if the vector has been sorted, any of the contained objects may have changed, which the vector cannot discover.
     * So it us up to the user of this SortableVector to make sure that the vector is sorted when calling binarySearch.
     * @param c The comparable to find.
     * @param exact Weather to find a match as close as possible or an exact match.
     * @return The index for the comparable (or closest comparable), or -1 if the comparable was not found.
     */
    public int binarySearch( IComparable c, boolean exact ) {
        return binarySearch( c, 0, size()-1, exact );
    }

    private int binarySearch( IComparable c, int startIndex, int endIndex, boolean exact ) {
        int middleIndex = (startIndex + endIndex) / 2;
        IComparable cAtMiddleIndex = getComparable( middleIndex );
        int comparison = cAtMiddleIndex.compareTo( c );

        if( startIndex == endIndex ) {
            if( !exact ) {
                if( comparison >= 0 || middleIndex == (size()-1) )
                    return middleIndex;
                else
                    return middleIndex + 1;
            }
            if( c.equals( cAtMiddleIndex ) )
                return middleIndex;
            else
                return -1;
        }

        if( comparison > 0 )
            return binarySearch( c, startIndex, middleIndex-1, exact );
        else if( comparison < 0 )
            return binarySearch( c, middleIndex+1, endIndex, exact );
        else if( cAtMiddleIndex.equals( c ) )
            return middleIndex;
        else
            return -1;
    }
}
