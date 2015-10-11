package engine.input;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.EnumMap;
import java.util.Map;

import engine.Game;
import engine.camera.Camera;
import engine.utility.Vector2;


public class MouseState
{
    Map<Buttons, ButtonState> buttonStates = new EnumMap<Buttons, ButtonState>(Buttons.class);
    Point position = new Point();
    
    MouseState()
    {
        buttonStates.put(Buttons.LeftButton, ButtonState.RELEASED);
        buttonStates.put(Buttons.MiddleButton, ButtonState.RELEASED);
        buttonStates.put(Buttons.RightButton, ButtonState.RELEASED);
        
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        Point p = new Point(dim.width / 2, dim.height / 2);
//        moveMouse(p);
        position = MouseInfo.getPointerInfo().getLocation();
    }
    
    MouseState(MouseState state)
    {
        buttonStates = new EnumMap<>(state.buttonStates);
        
        position = new Point(state.position);
    }
    
    public boolean isButtonDown(Buttons button)
    {
        return (buttonStates.get(button) == ButtonState.PRESSED);
    }
    
    public boolean isButtonUp(Buttons button)
    {
        return (buttonStates.get(button) == ButtonState.RELEASED);
    }
    
    // Dodati poziciju misa u mouse input i metodu setPosition().
    public Point getPositionPoint()
    {
//    	Vector2 scaleFactor = new Vector2(Game.game.worldDimension.width, Game.game.worldDimension.height);
//        return new Point((int)(position.x / scaleFactor.x), (int)(position.y / scaleFactor.y));
        return MouseInfo.getPointerInfo().getLocation();
    }
    
    
    public Vector2 getWorldPositionVector(Camera camera)
    {
    	Vector2 leftCornerPosition = camera.position.clone().sub(new Vector2(Game.game.worldDimension.width / 2, Game.game.worldDimension.height / 2));
    	Vector2 mousePosition = new Vector2(getPositionPoint().x, getPositionPoint().y);
    	Vector2 mouseCornerOffset = mousePosition.add(leftCornerPosition);
    	
    	Vector2 gameDimension = Game.game.getFrameDimensions();   	
    	Vector2 scaleFactor = new Vector2(Game.game.worldDimension.width / gameDimension.x , Game.game.worldDimension.height / gameDimension.y);
    	    	
    	mouseCornerOffset.mul(scaleFactor);
    	
    	return mouseCornerOffset;
    }
    
    public Vector2 getScreenPositionVector()
    {
    	return new Vector2(getPositionPoint().x, getPositionPoint().y);
    }
    
    public void setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
        
        moveMouse(position);
    }
    
    public void setPosition(Point p)
    {
        setPosition(p.x, p.y);
    }
    
    private void moveMouse(Point p)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        
        // Search the devices for the one that contains the specified point.
        for (GraphicsDevice device : gs)
        {
            GraphicsConfiguration[] configurations = device.getConfigurations();
            
            for (GraphicsConfiguration config : configurations)
            {
                Rectangle bounds = config.getBounds();
                
                if (bounds.contains(p))
                {
                    // Set point to screen coordinates.
                    Point b = bounds.getLocation();
                    Point s = new Point(p.x - b.x, p.y - b.y);
                    
                    try
                    {
                        Robot r = new Robot(device);
                        r.mouseMove(s.x, s.y);
                    }
                    catch (AWTException exception) { exception.printStackTrace(); }
                    
                    return;
                }
            }
        }
        
        // Couldn't move to the point, it may be off screen.
        return;
    }
}
