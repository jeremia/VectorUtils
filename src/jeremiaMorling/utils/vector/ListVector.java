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
public class ListVector extends SortableVector {
    public ListVector() {
        super();
    }
    
    public ListVector( int initialCapacity ) {
        super( initialCapacity );
    }
    
    public void addElement( Object element ) {
        if( !(element instanceof IListItem) )
            throw new IllegalArgumentException( "element has to be of type IListItem, but is of type " + element.getClass().getName() );

        super.addElement( element );
    }

    public void insertElementAt( Object o, int index ) {
        if( !(o instanceof IListItem) )
            throw new IllegalArgumentException( "Added objects has to be of type IListItem. Object was of type " + o.getClass().getName() );

        super.insertElementAt( o, index );
    }

    public IListItem getListItem( int index ) {
        return (IListItem)elementAt( index );
    }
}
