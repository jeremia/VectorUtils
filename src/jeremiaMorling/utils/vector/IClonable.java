package jeremiaMorling.utils.vector;

/**
 * Interface used by ClonableVector to Clone whole Vectors.
 *
 * @author Jeremia Mörling
 */
public interface IClonable {
    /**
     * Returns a deep copy of the object.
     * @return A deep copy of the object.
     */
    public IClonable clone();
}