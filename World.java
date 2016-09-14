import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.media.opengl.awt.GLCanvas;
import org.apfloat.Apfloat;
import com.jogamp.opengl.util.FPSAnimator;
/**
 *  Name:           World
 *  Purpose:        Contains the actualy graphic world elements on screen (e.g. all the terrian triangles)
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class World{
    Generate gen;
    Bindings actions;
    boolean procceed, same;
    Scene screen;
    GLCanvas canvas;
    List<Triangle> terrain;
    Triangle before;
    BlockingQueue<List<Triangle>> onScreen, updatedTerrain;
    FPSAnimator animator;
    State state;
    Vertex center;
    long precision;
    public World(int level, Apfloat centerX, Apfloat centerY, Apfloat radius, Bindings actions, GLCanvas canvas, FPSAnimator animator, State state, long precision){
        center = new Vertex(centerX, centerY, precision);
        gen = new Generate(level, center, radius, precision);
        this.actions = actions;
        procceed = true;
        same = false;
        this.precision = precision;
        screen = new Scene(this);
        this.canvas = canvas;
        this.animator = animator;
        this.state = state;
    }
    public void start(){
        // Generates a world from the given inputs.
        gen.generate();
        
        init();
        while(procceed){
            if(updatedTerrain.size() > 0){
                try{
                    terrain = updatedTerrain.take();
                }catch(InterruptedException IE1){
                    IE1.printStackTrace();
                }
            }
            //Check if it's on the screen
            List<Triangle> tempOnScreen = new ArrayList<Triangle>();
            for(int i = 0; i < terrain.size(); i++){
                Triangle tempTri = terrain.get(i);
                if(tempTri.onScreen()){
                    tempOnScreen.add(tempTri.getSelf());
                }
            }
            //check that it's not the same thing as before
            if(before == null)
                before = tempOnScreen.get(0).getSelf();
            else if(tempOnScreen.get(0).equalsTo(before))
                same = true;
            else{
                before = tempOnScreen.get(0).getSelf();
                same = false;
            }
            if(count < 1000)
                rotateArray(terrain);
            count++;
            //Add the new terrain to the on screen queue to be painted
            if(tempOnScreen.size() > 0 && !same)
                try{
                    onScreen.put(tempOnScreen);
                    Thread.currentThread().sleep(10);
                }catch (InterruptedException IE2){
                    IE2.printStackTrace();
                }
        }
        finish();
    }
    int count = 0;
    public void init(){
        terrain = gen.getTerrain();
        onScreen = new LinkedBlockingQueue<List<Triangle>>();
        updatedTerrain = new LinkedBlockingQueue<List<Triangle>>();
        canvas.addGLEventListener(screen);
        if(animator.isPaused())
            animator.resume();
        else
            animator.start();
    }
    public void finish(){
        animator.pause();
        canvas.removeGLEventListener(screen);
    }
    public void end(){
        procceed = false;
    }
    public List<Triangle> getCurrentTerrain(){
        List<Triangle> temp = new ArrayList<Triangle>(terrain.size());
        for(int i = 0; i < terrain.size(); i++){
            temp.add(terrain.get(i).getSelf());
        }
        return temp;
    }
    public void changeTerrain(List<Triangle> newTerrain){
        try{
            updatedTerrain.put(newTerrain);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public void rotateArray(List<Triangle> terrain){
        List<Triangle> temp = new ArrayList<Triangle>();
        for(int i = 0; i < terrain.size(); i++){ 
            temp.add(terrain.get(i).getSelf());
            temp.get(i).rotate(new Apfloat("5",precision), false);
        }
        try {
            updatedTerrain.put(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Triangle> gatherScreen(){
        if(onScreen.size() != 0){
            try{
                return onScreen.take();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
