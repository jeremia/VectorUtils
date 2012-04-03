package jeremiaMorling.utils.vector;

/**
 * Inteface used in SortableVector in order to compare objects.
 *
 * @author Jeremia
 */
public interface IComparable extends IClonable {

    /**
     * Compares two objects of type Comparable. Returns 0 if objects are considered equal. Returns <0 if this object is considered "less" than parameter. Returns >0 if this object is considered "greater" than parameter.
     *
     * @param comparable Parameter to be compared with this object.
     * @return Returns 0 if objects are considered equal. Returns <0 if this object is considered "less" than parameter. Returns >0 if this object is considered "greater" than parameter.
     * @throws IllegalArgumentException If parameter comparable is not of type Comparable.
     */
    public int compareTo( Object itemToCompare ) throws IllegalArgumentException;
}
