package jeremiaMorling.utils.vector;

/**
 * Shell String class created in order to be able to use String with SortableVector.
 * Class is not extended from String due to that String is final.
 *
 * @author Jeremia Mörling
 */
public class ComparableString implements IComparable {
    private String value;
    private String lowerCaseValue;

    /**
     * Creates a ComparableString with the given String value.
     * 
     * @param value The String value for the ComparableString.
     */
    public ComparableString( String value ) {
        this.value = value;
        lowerCaseValue = value.toLowerCase();
    }
    
    public ComparableString( String value, boolean lowerCase ) {
        this.value = value;
        lowerCaseValue = value;
    }

    /**
     * Returns the String value.
     *
     * @return The String value.
     */
    public String toString() {
        return value;
    }

    /**
     * Compares this String with the parameter String. Returns 0 if the Strings are considered equal. Returns <0 if this string is considered "less" than paramter String. Returns >0 if this String is considered "greater" than paramter String.
     * Ignores case.
     *
     * @param itemToCompare Paramter to be compared with this String. Either a ComparableString or an IListItem. Uses getStringPart() for comparison in case of IListItem.
     * @return Returns 0 if the Strings are considered equal. Returns <0 if this string is considered "less" than paramter String. Returns >0 if this String is considered "greater" than paramter String.
     * @throws IllegalArgumentException If paramter is not of type ComparableString.
     */
    public int compareTo( Object itemToCompare ) throws IllegalArgumentException {
        if( itemToCompare instanceof ComparableString )
            return lowerCaseCompareToSE( lowerCaseValue, ((ComparableString)itemToCompare).lowerCaseValue );
        else if( itemToCompare instanceof IListItem )
            return lowerCaseCompareToSE( lowerCaseValue, ((ListItem)itemToCompare).getLowerCaseText() );
        else if( itemToCompare instanceof String )
            return lowerCaseCompareToSE( lowerCaseValue, ((String)itemToCompare).toLowerCase() );
        else
            throw new IllegalArgumentException( "comparable has to be of type ComparableString or IListItem, but is of type " + itemToCompare.getClass().getName() );
    }

    /**
     * Tests if this string ends with the specified suffix.
     * 
     * @param suffix the suffix.
     * @return true if the character sequence represented by the argument is a suffix of the character sequence represented by this object; false otherwise.
     * Note that the result will be true if the argument is the empty string or is equal to this String object as determined by the equals(Object) method.
     */
    public boolean endsWith( String suffix ) {
        return value.endsWith( suffix );
    }

    /**
     * Returns a copy of this ComparableString.
     * @return A copy of this ComparableString.
     */
    public IClonable clone() {
        return new ComparableString( value );
    }
    
    public static int lowerCaseCompareToSE( String stringA, String stringB ) {
        int minLength = Math.min( stringA.length(), stringB.length() );
        for( int i=0; i<minLength; i++ ) {
            char charA = stringA.charAt( i );
            char charB = stringB.charAt( i );
            if( charA == 'å' && charB == 'ä' )
                return -1;
            else if( charA == 'ä' && charB == 'å' )
                return 1;
            else if( charA < charB )
                return -1;
            else if( charA > charB )
                return 1;
        }
        
        if( stringA.length() < stringB.length() )
            return -1;
        else if( stringA.length() > stringB.length() )
            return 1;
        else
            return 0;
    }
    
    public static int compareToSEIgnoreCase( String stringA, String stringB ) {
        return lowerCaseCompareToSE( stringA.toLowerCase(), stringB.toLowerCase() );
    }
}
