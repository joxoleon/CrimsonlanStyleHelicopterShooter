package camera;

import engine.Game;
import utility.Vector2;

public class Camera
{
	// ******************** Fields ******************** 
	public Vector2 position = new Vector2(0, 0);
	public float rotation;
	public Vector2 scale = new Vector2(1, 1);
	
	public int cameraWidth = Game.game.worldDimension.width;
	public int cameraHeight = Game.game.worldDimension.height;
	
	
	
	// ******************** Constructors ******************** 
	public Camera()
	{
		
	}
	
	
	
	
	
	// ******************** Methods ******************** 

}
