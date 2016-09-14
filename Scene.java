import java.awt.Font;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import org.apfloat.Apfloat;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
/**
 *  Name:           Scene
 *  Purpose:        This is where the magic happens. All graphic drawing is down in the scene below.
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Scene implements GLEventListener{
    World elements;
    List<Triangle> terrianOnScreen;
    Quad test;
    public Scene(World elements){
        this.elements = elements;
        this.test = new Quad(new Vertex(new Apfloat("0", elements.precision), new Apfloat("0.1",elements.precision),elements.precision), new Apfloat("0.1", elements.precision), elements.precision);
    }
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        List<Triangle> temp = elements.gatherScreen();
        if(temp != null)
            terrianOnScreen = temp;
        if(terrianOnScreen != null){
            gl.glBegin(gl.GL_TRIANGLES);
            {
                for(int i = 0; i < terrianOnScreen.size(); i++){
                    gl.glColor3d(Math.sin(i) + 0.1, Math.cos(i) + 0.2, Math.tan(i));
                    terrianOnScreen.get(i).draw(gl);                
                }
            }
            gl.glEnd();
        }
        gl.glBegin(gl.GL_QUADS);
        {
            test.draw(gl);
            test.rotate(new Apfloat("3",elements.precision));
        }
        gl.glEnd();
        // <-------------------------------- BEGIN Draw and display framerate on screen -------------------------------->
        DecimalFormat df = new DecimalFormat("###");
        df.setRoundingMode(RoundingMode.DOWN);
        TextRenderer text = new TextRenderer(new Font("OCR A Std", Font.BOLD, 25));
        text.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        text.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        text.draw(df.format(elements.animator.getLastFPS()), 0, 5);
        text.draw(elements.center.toString(), 450,5);
        try{text.endRendering();}catch(GLException e){e.printStackTrace();}
        // <-------------------------------- BEGIN Draw and display framerate on screen -------------------------------->
    }
    public void dispose(GLAutoDrawable arg0){/* Nothing to see here. */}
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2(); 
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                        // set background (clear) color
        gl.glClearDepth(1.0f);                                          // set clear depth value to farthest
        gl.glEnable(gl.GL_DEPTH_TEST);                                  // enables depth testing
        gl.glDepthFunc(gl.GL_LEQUAL);                                   // the type of depth test to do
        gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, gl.GL_NICEST);     // best perspective correction
        gl.glShadeModel(gl.GL_SMOOTH);                                  // blends colors nicely, and smoothes out lighting
    }
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4){/* Nothing to see here. */}
}