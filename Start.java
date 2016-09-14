import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;
import org.apfloat.Apfloat;
import com.jogamp.opengl.util.FPSAnimator;
/**
 *  Name:           Start
 *  Purpose:        Initilizes FPSAnimator, JFrame, begins world generation process, and configures the state of the program.
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Start {
    private int height, width, frameRate;
    private final long precision;
    private String title, version;
    private GLProfile glp;
    private GLCapabilities caps;
    private GLCanvas canvas;
    private GLJPanel bindings;
    private JFrame frame;
    private Generate generate;
    private FPSAnimator animator;
    private World world;
    private State state;
    /*
     *  Purpose:    Configures and Initializes the frame, graphical profile, the world, and the current state of the program.
     *  Input:      width 
     *                  ↳ the specified width of the JFrame.
     *              height
     *                  ↳ the specified height of the JFrame.
     *              version
     *                  ↳ the current standing version of the program.
     *              frameRate
     *                  ↳ specifies the max frame rate possible. This is, however, capped at 60 frames as due to the implementation of the FPSAnimator class.
     *  Output:     None.
     *  Return:     None. -> This is a Constructor.
     *  Notes:      None.
     */
    public Start(int width, int height, String title, String version, int frameRate){
        this.width = width;
        this.height = height;
        this.title = title;
        this.version = version;
        this.frameRate = frameRate;
        
        // Decimal depth for floating numbers
        precision = 11;
        
        state = new State();
        
        // Configures the graphical profile used to display the graphics on screen.
        glProfileConfig();
        
        // Paints and refreshes screen
        animator = new FPSAnimator(canvas,frameRate);
        animator.setUpdateFPSFrames(10, null);
        
        // Key-Bindings class that handles all keyboard inputs
        bindings = new Bindings(height, width, precision, state);
        
        // A world object is where every object "lives" in.
        world = new World(2, (new Apfloat("0",precision)),(new Apfloat("-1",precision)),
                          new Apfloat("1.0",precision), (Bindings)bindings, canvas, 
                          animator, state,precision);
        
        // Sets up the JFrame for display the graphics
        configureFrame();
        
        // Configures and begins the current state of the program
        state.setWorld(world);
        state.start();
        
        frame.setVisible(true);
        world.start();
    }
    /*
     *  Purpose:    Configures the graphical profile used to display the graphics on screen.
     *  Input:      None.
     *  Output:     None.
     *  Return:     None.
     *  Notes:      None.
     */
    private void glProfileConfig(){
        glp = GLProfile.getDefault();       // Standard OpenGL profile
        caps = new GLCapabilities(glp);
        caps.setHardwareAccelerated(true);
        canvas = new GLCanvas(caps);
    }
    /*
     *  Purpose:    Setups a JFrame to display the graphical elements
     *  Input:      None.
     *  Output:     None.
     *  Return:     None.
     *  Notes:      None.
     */
    private void configureFrame(){
        frame = new JFrame(title + " :: " + version);
        frame.setSize(height, width);
        frame.setLocationRelativeTo(null);              // Positions frame in middle of screen.
        frame.getContentPane().add(bindings);
        frame.getContentPane().add(canvas);             // Addes the OpenGL canvas to the frame to allow displaying of OpenGL graphics.
        
        /* This is sets up a listener for closing the JFrame when the 'X' is hit on the JFrame */
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                /*  Shutdown Process:
                 *      1.) If a valid state exists, termiante it.
                 *      2.) If a world exists, terminate it.
                 *      3.) Stop the (valid) FPSAnimator if it has been started.
                 *      4.) Terminate the main thread.
                 */ 
                if(state != null && state.isRunning())
                    state.end();
                if(world != null)
                    world.end();
                if(animator != null && animator.isStarted() || animator.isPaused()){
                    System.out.print("Stopping Animator...");
                    animator.stop();
                    System.out.println("done.");
                }
                System.out.print("Halting Program...");
                System.out.println("done.");
                System.exit(0);
            }
        });
    }
}
