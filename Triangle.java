import javax.media.opengl.GL2;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
/**
 *  Name:           
 *  Purpose:        
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Triangle implements Shape<Triangle>{
	Vertex top, left, right;
	Apfloat cos, sin;
	long precision;
	public Triangle(Vertex top, Vertex left, Vertex right, Apfloat sin, Apfloat cos, long precision){
		this.top = top;
		this.left = left;
		this.right = right;
		this.sin = sin;
		this.cos = cos;
		this.precision = precision;
	}
	public Triangle getSelf(){
		return new Triangle(top.getSelf(), left.getSelf(), right.getSelf(),sin, cos, precision);
	}
	public void changeXs(Apfloat amount){
		top.moveX(amount);
		left.moveX(amount);
		right.moveX(amount);
	}
	public void changeYs(Apfloat amount){
		top.moveY(amount);
		left.moveY(amount);
		right.moveY(amount);
	}
	public Vertex getTop(){
		return new Vertex(top.getX(), top.getY(),precision);
	}
	public Vertex getLft(){
		return  new Vertex(left.getX(), left.getY(),precision);
	}
	public Vertex getRght(){
		return new Vertex(right.getX(), right.getY(),precision);
	}
	public void rotate(){
		if(sin != null && cos != null){
			Apfloat Rise = top.getY();
			Apfloat Run = top.getX();
			top.rotate(cos, sin, Rise, Run);
			left.rotate(cos, sin, Rise, Run);
			right.rotate(cos, sin, Rise, Run);
		}
	}
	public void rotate(Apfloat degree, boolean includeTop){
		Apfloat newCos = ApfloatMath.cos(toRadians(degree));
		Apfloat newSin = ApfloatMath.sin(toRadians(degree));
		Apfloat Rise = top.getY();
		Apfloat Run = top.getX();
		if(includeTop)
			top.rotate(newCos, newSin, Rise, Run);
		left.rotate(newCos, newSin, Rise, Run);
		right.rotate(newCos, newSin, Rise, Run);
	}
	public Apfloat toRadians(Apfloat degree){
		Apfloat PIdiv180 = ApfloatMath.pi(precision).divide(new Apfloat("180",precision));
		return degree.multiply(PIdiv180);
	}
	public boolean equalsTo(Triangle t){
		//System.err.println("Warning: If rotation method used, 'equalsTo' will return false.");
		if(top.equalsTo(t.getTop()) && left.equalsTo(t.getLft()) && right.equalsTo(t.getRght()))
			return true;
		return false;
	}
	public boolean onScreen(){
		if(top.isOnScreen())
			return true;
		if(left.isOnScreen())
			return true;
		if(right.isOnScreen())
			return true;
		return false;
	}
	public void draw(GL2 gl){
		gl.glVertex2f(Float.parseFloat(top.getX().toString(true)), Float.parseFloat(top.getY().toString(true)));
		gl.glVertex2f(Float.parseFloat(left.getX().toString(true)), Float.parseFloat(left.getY().toString(true)));
		gl.glVertex2f(Float.parseFloat(right.getX().toString(true)), Float.parseFloat(right.getY().toString(true)));
	}
	public String toString(){
		return "Top: " + top.toString() + ", Left: " + left.toString() + ", Right: " + right.toString();
	}
}
