/**
 *  Name:           Runner.
 *  Purpose:        Create and runs a single instance of the Java OpenGL (JOGL) Geometry Drawer.
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Runner {
    public static void main(String[] args){
        /*
         *  Start(  
         *          Width:int,
         *          Height:int,
         *          JFrame Title:String,
         *          Version:String,
         *          Frame Rate:String
         *       )
         *  Note: FPSAnimator class caps frame rate at 60.
         */
        new Start(600, 650, "Java OpenGL (JOGL) Geometry Drawer - By: Daniel Hoynoski", "ver. 0.0.0.1", 60);
    }
}
