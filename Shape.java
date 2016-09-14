import javax.media.opengl.GL2;
import org.apfloat.Apfloat;
/**
 *  Name:           Shape Interface
 *  Purpose:        Makes sure that each new Shape implements 
 *  Notes:          An abstract class would be better here. (Keeping this for legacy (This is an old project!))
 *  Written By:     Daniel Hoynoski
 *  Last Update:    
 */
public interface Shape<T>{
    T getSelf();
    void changeXs(Apfloat amount);
    void changeYs(Apfloat amount);
    Apfloat toRadians(Apfloat degree);
    boolean equalsTo(T t);
    boolean onScreen();
    void draw(GL2 gl);
    String toString();
}
