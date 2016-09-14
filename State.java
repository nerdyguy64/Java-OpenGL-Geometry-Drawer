/**
 *  Name:           State
 *  Purpose:        A basic state holding object that gives status of the world and indicates if the program has been started (isRunning)
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class State{
    boolean isRunning;
    int score;
    World world;
    public State(){
        score = 0;
        isRunning = false;
    }
    public void setWorld(World world){
        this.world = world;
    }
    public World getWorld(){
        return world;
    }
    public boolean isRunning(){
        return isRunning;
    }
    public void end(){
        isRunning = false;
    }
    public void start(){
        isRunning = true;
    }
}
