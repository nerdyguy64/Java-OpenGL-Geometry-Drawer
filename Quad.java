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
public class Quad implements Shape<Quad>{
	Vertex bttmLft, topLft, topRght, bttmRght;
	long precision;
	Vertex center;
	public Quad(Vertex bttmLft, Vertex topLft, Vertex topRght, Vertex bttmRght, long precision){
		this.bttmLft = bttmLft;
		this.topLft = topLft;
		this.topRght = topRght;
		this.bttmRght = bttmRght;
		this.precision = precision;
		
		Apfloat two = new Apfloat("2",precision);
		Apfloat halfSide = (topRght.getY().subtract(bttmRght.getY())).divide(two);
		System.out.println(halfSide);
		this.center = new Vertex(bttmRght.getX().subtract(halfSide),bttmRght.getY().add(halfSide), precision);
	}
	public Quad(Vertex center, Apfloat radius, long precision){
		this.precision = precision;
		this.center = center;
		Apfloat xLength, yLength;
		xLength = radius.multiply(ApfloatMath.cos(toRadians(new Apfloat("45",precision))));
		yLength = radius.multiply(ApfloatMath.sin(toRadians(new Apfloat("45",precision))));
		
		bttmLft = new Vertex(center.getX().subtract(xLength), center.getY().subtract(yLength),precision);
		topLft = new Vertex(center.getX().subtract(xLength), center.getY().add(yLength),precision);
		topRght = new Vertex(center.getX().add(xLength), center.getY().add(yLength),precision);
		bttmRght = new Vertex(center.getX().add(xLength), center.getY().subtract(yLength),precision);
	}
	public Quad getSelf() {
		return new Quad(bttmLft.getSelf(), topLft.getSelf(), topRght.getSelf(), bttmRght.getSelf(), precision);
	}
	public void changeXs(Apfloat amount) {
		bttmLft.moveX(amount);
		bttmRght.moveX(amount);
		topLft.moveX(amount);
		topRght.moveX(amount);
	}
	public void changeYs(Apfloat amount) {
		bttmLft.moveY(amount);
		bttmRght.moveY(amount);
		topLft.moveY(amount);
		topRght.moveY(amount);
	}
	public Vertex getBttmLft(){
		return bttmLft.getSelf();
	}
	public Vertex getBttmRght(){
		return bttmRght.getSelf();
	}
	public Vertex getTopLft(){
		return topLft.getSelf();
	}
	public Vertex getTopRght(){
		return topRght.getSelf();
	}
	public void rotate(Apfloat degree) {
		//Rotates from center of quad.
		Apfloat newCos = ApfloatMath.cos(toRadians(degree));
		Apfloat newSin = ApfloatMath.sin(toRadians(degree));
		
		Apfloat Rise = center.getY();
		Apfloat Run = center.getX(); 
		
		bttmLft.rotate(newCos, newSin, Rise, Run);
		topLft.rotate(newCos, newSin, Rise, Run);
		topRght.rotate(newCos, newSin, Rise, Run);
		bttmRght.rotate(newCos, newSin, Rise, Run);
	}
	public Apfloat toRadians(Apfloat degree) {
		Apfloat PIdiv180 = ApfloatMath.pi(precision).divide(new Apfloat("180",precision));
		return degree.multiply(PIdiv180);
	}
	public boolean equalsTo(Quad t) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onScreen() {
		if(bttmLft.isOnScreen())
			return true;
		if(topLft.isOnScreen())
			return true;
		if(topRght.isOnScreen())
			return true;
		if(bttmRght.isOnScreen())
			return true;
		return false;
	}
	public void draw(GL2 gl) {
		gl.glVertex2f(Float.parseFloat(bttmLft.getX().toString(true)),Float.parseFloat(bttmLft.getY().toString(true)));
		gl.glVertex2f(Float.parseFloat(topLft.getX().toString(true)),Float.parseFloat(topLft.getY().toString(true)));
		gl.glVertex2f(Float.parseFloat(topRght.getX().toString(true)),Float.parseFloat(topRght.getY().toString(true)));
		gl.glVertex2f(Float.parseFloat(bttmRght.getX().toString(true)),Float.parseFloat(bttmRght.getY().toString(true)));
	}
	public String toString(){
		return "Bottem_Left: " + bttmLft.toString() + " Top_Left: " + topLft.toString() + 
				" Top_Right: " + topRght.toString() + " Bottom_Right: " + bttmRght.toString();
	}
}
