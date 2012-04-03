/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jeremiaMorling.utils.vector;

import javax.microedition.lcdui.Image;

/**
 * 
 *
 * @author Jeremia Mörling
 */
public class ListItem implements IListItem {
    private String text;
    private String lowerCaseText;
    private Image icon;
    private boolean selected;
    
    protected ListItem() {}

    public ListItem( String text ) {
        setText( text );
    }

    public ListItem( String text, Image icon ) {
        this( text );
        this.icon = icon;
    }

    public String getText() {
        return text;
    }
    
    public void setText( String text ) {
        this.text = text;
        lowerCaseText = text.toLowerCase();
    }
    
    public void setIcon( Image icon ) {
        this.icon = icon;
    }

    public Image getIcon() {
        return icon;
    }

    public void setSelected( boolean selected ) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public int compareTo( Object itemToCompare ) throws IllegalArgumentException {
        if( !(itemToCompare instanceof ListItem) )
            throw new IllegalArgumentException( "ListItem: compareTo(). comparable has to be of type ListItem, but is of type " + itemToCompare.getClass().getName() );

        ListItem listItemToCompare = (ListItem)itemToCompare;
        return ComparableString.lowerCaseCompareToSE( lowerCaseText, listItemToCompare.lowerCaseText );
    }

    public IClonable clone() {
        ListItem clone = new ListItem( text, icon );
        clone.setSelected( selected );
        
        return clone;
    }
    
    public String getLowerCaseText() {
        return lowerCaseText;
    }
}
