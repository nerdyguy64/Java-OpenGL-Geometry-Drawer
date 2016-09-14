import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import org.apfloat.Apfloat;
/**
 *  Name:           Bindings
 *  Purpose:        Adds in key bindings for controls.
 *  Notes:          Rotating the main world polygon was never fluid and so I believe this is a problem in this version of the JOGL implementation.
 *                  I really couldn't pin point the problem 2-3 years ago, but I am sure if I returned to this program I would be able to fix this.
 *                  I for one would do native OpenGL in C++ as the development for JOGL sort of stopped a few years back (unsure if it picked up again).
 *  Written By:     Daniel Hoynoski
 *  Last Update:
 */
public class Bindings extends GLJPanel{
    InputMap im;
    ActionMap am;
    long precision;
    State state;
    List<Triangle> terrain;
    Apfloat rotationAmount;
    public Bindings(int height, int width, long precision, State state){
        this.setSize(height, width);
        this.precision = precision;
        this.state = state;
        rotationAmount = new Apfloat("2",precision);
        im = this.getInputMap(GLJPanel.WHEN_IN_FOCUSED_WINDOW);
        am = this.getActionMap();
        defineInputMap();
        defineActionMap();
    }
    public void defineInputMap(){
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), "A");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "D");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "Escape");
    }
    public void defineActionMap(){
        am.put("D", new Actions("D",state, (Bindings)this, rotationAmount));
        am.put("A", new Actions ("A",state, (Bindings)this, rotationAmount));
        am.put("Escape", new Actions ("Escape",state, (Bindings)this, rotationAmount));
    }
    public List<Triangle> getTerrain(){
        return terrain;
    }
    public void setTerrain(List<Triangle> terrain){
        this.terrain = terrain;
    }
    public List<Triangle> getCurrentTerrain(){
        List<Triangle> temp = new ArrayList<Triangle>(terrain.size());
        for(int i = 0; i < terrain.size(); i++){
            temp.add(terrain.get(i).getSelf());
        }
        return temp;
    }
    private class Actions extends AbstractAction{
        String key;
        State state;
        Bindings actions;
        Apfloat rotationAmount;
        public Actions(String action,State state, Bindings actions, Apfloat rotationAmount){
            key = action;
            this.state = state;
            this.actions = actions;
            this.rotationAmount = rotationAmount;
        }
        public void actionPerformed(ActionEvent e){
            if(state.getWorld() != null){
                if(actions.getTerrain() == null)
                    actions.setTerrain(state.getWorld().getCurrentTerrain());
                switch(key){
                    case "D":                    
                        for(int i = 0; i < actions.getTerrain().size(); i++){
                            actions.getTerrain().get(i).rotate(rotationAmount, true);
                        }
                        state.getWorld().changeTerrain(actions.getCurrentTerrain());    
                        break;
                    case "A":
                        for(int i = 0; i < actions.getTerrain().size(); i++){
                            actions.getTerrain().get(i).rotate(rotationAmount.negate(), true);
                        }
                        state.getWorld().changeTerrain(actions.getCurrentTerrain());
                        break;
                    case "Escape":
                        // TODO: Add escape functionality
                        System.out.println("Key Press: Escape - No action performed.");
                        break;
                    default:
                        System.err.println("Whoops, unknown action.");
        				break;
    			}
    		}else{
    			if(actions.getTerrain() != null)
    				actions.setTerrain(null);
    		}
    	}
    }
}
