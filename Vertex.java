import org.apfloat.Apfloat;
/**
 *  Name:           Vertex
 *  Purpose:        A base class used by all shape objects. Every shape has at least one vertex.
 *  Notes:          None.
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Vertex {
	Apfloat x, y;
	long precision;
	public Vertex(Apfloat x, Apfloat y, long precision){
		this.x = x;
		this.y = y;
		this.precision = precision;
	}
	public Apfloat getX(){
		return new Apfloat(x.toString(true),precision);
	}
	public Apfloat getY(){
		return new Apfloat(y.toString(true),precision);
	}
	public void moveX(Apfloat amount){
		this.x = x.add(amount);
	}
	public void moveY(Apfloat amount){
		this.y = y.add(amount);
	}
	public Vertex getSelf(){
		return new Vertex(new Apfloat(x.toString(true),precision),new Apfloat(y.toString(true),precision), precision);
	}
	public void rotate(Apfloat cos, Apfloat sin, Apfloat Rise, Apfloat Run){
		Apfloat repositionX = x.subtract(Run);
		Apfloat repositionY = y.subtract(Rise);
		
		Apfloat x_cos = cos.multiply(repositionX);
		Apfloat y_sin = sin.multiply(repositionY);
		Apfloat x_sin = sin.multiply(repositionX);
		Apfloat y_cos = cos.multiply(repositionY);
		
		Apfloat newX = x_cos.subtract(y_sin);
		Apfloat newY = x_sin.add(y_cos);
		
		this.x = newX.add(Run);
		this.y = newY.add(Rise);
	}
	public boolean equalsTo(Vertex v){
		if(x.compareTo(v.getX()) == 0 && y.compareTo(v.getY()) == 0)
			return true;
		else
			return false;
	}
	public boolean isOnScreen(){
		if((x.compareTo(new Apfloat("1",precision)) == -1 || x.compareTo(new Apfloat("1",precision)) == 0) &&
		   (x.compareTo(new Apfloat("-1",precision)) == 1 || x.compareTo(new Apfloat("-1",precision)) == 0)){
			if((y.compareTo(new Apfloat("1",precision)) == -1 || y.compareTo(new Apfloat("1",precision)) == 0) &&
			   (y.compareTo(new Apfloat("-1",precision)) == 1 || y.compareTo(new Apfloat("-1",precision)) == 0)){
				return true;
			}
		}
		return false;
	}
	public String toString(){
		return "(" + x.toString(true) + "," + y.toString(true) + ")";
	}
}
