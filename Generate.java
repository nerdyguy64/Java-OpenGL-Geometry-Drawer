import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
/**
 *  Name:           Generate
 *  Purpose:        Used to generate a full functioning world made out of triangles.
 *  Notes:          This could definitely be signicantly simplified, i.e. using one single class.
 *                  (no reason to have the two classes, world and generate, seperate.)
 *                  I have grown a lot since this project. :)
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Generate{
    boolean state;
    int level;
    Apfloat degree, radius,numOfTriangles, length;
    Vertex center;
    List<Triangle> terrain;
    long precision;
    public Generate(int level, Vertex center, Apfloat radius, long precision){
        state = false;
        this.level = level;
        this.center = center;
        this.radius = radius;
        this.precision = precision;
    }
    public void generate(){
        try{
            levelProperties();
            build();
        }catch(Exception e){
            e.printStackTrace();
        }
        state = true;
    }
    public void levelProperties(){
        switch(level){
            case 1:
                //First Level
                numOfTriangles = new Apfloat("8.0",precision);
                break;
            case 2:
                //Second Level
                numOfTriangles = new Apfloat("6.0",precision);
                break;
            default:
                System.err.println("Error generating, could not find world number. \nDefaulting to Level One.");
                level = 1;
                levelProperties();
                break;
        }
    }
    public void build(){
        //Build Arrays
        terrain = new ArrayList<Triangle>(Integer.parseInt(numOfTriangles.toString(true)));
        //Obtain degree
        Apfloat total = new Apfloat("360",precision);
        degree = total.divide(numOfTriangles);
        System.out.println("Degree: " + degree.toString(true));
        //Cosine & Sine
        Apfloat cos = ApfloatMath.cos(toRadians(degree));
        Apfloat sin = ApfloatMath.sin(toRadians(degree));
        //Vertices for Left & Right
        Vertex left,right;
        //Useful
        Apfloat halfDegree = degree.divide(new Apfloat("2",precision));
        //Sin and Cos of half angle
        Apfloat sinHalf = ApfloatMath.sin(toRadians(halfDegree));
        Apfloat cosHalf = ApfloatMath.cos(toRadians(halfDegree));
        //X & Y Distance
        Apfloat xDis = radius.multiply(sinHalf);
        Apfloat yDis = radius.multiply(cosHalf);
        //Put it all together
        left = new Vertex(center.getX().subtract(xDis),yDis.add(center.getY()), precision);
        right = new Vertex(center.getX().add(xDis),yDis.add(center.getY()), precision);
        //Build Reference
        Triangle reference = new Triangle(center, left, right, sin, cos, precision);
        terrain.add(reference);
        //Generate rest, and store in array.
        Triangle next;
        for(int i = 0; i < Integer.parseInt(numOfTriangles.toString(true)) - 1; i++){ 
            next = terrain.get(i).getSelf();
            next.rotate();
            terrain.add(next);
        }
        //Distance Check:
        Apfloat x,y,sqX,sqY,xplusy, final_;
        x = left.getX().subtract(center.getX());
        y = left.getY().subtract(center.getY());
        sqX = ApfloatMath.pow(x, 2);
        sqY = ApfloatMath.pow(y, 2);
        xplusy = sqX.add(sqY);
        final_ = ApfloatMath.sqrt(xplusy);
        System.out.println("Current Radius: " + final_.toString(true) + " (Correct: " + radius.toString(true) + ")");
    }
    public boolean isFinished(){
        return state;
    }
    public List<Triangle> getTerrain(){
        List<Triangle> temp = new ArrayList<Triangle>(terrain.size());
        for(int i = 0; i < terrain.size(); i++){
            temp.add(terrain.get(i).getSelf());
        }
        return temp;
    }
    public Apfloat toRadians(Apfloat degree){
        Apfloat PIdiv180 = ApfloatMath.pi(precision).divide(new Apfloat("180",precision));
        return degree.multiply(PIdiv180);
    }
}
