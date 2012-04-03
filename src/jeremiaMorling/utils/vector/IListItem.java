/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jeremiaMorling.utils.vector;

import javax.microedition.lcdui.Image;

/**
 *
 * @author Jeremia
 */
public interface IListItem extends IComparable {
    public String getText();
    public Image getIcon();
    public boolean isSelected();
    public void setSelected( boolean selected );
    //public String getLowerCaseStringPart();
    //public void setStringPart( String stringPart );
}
