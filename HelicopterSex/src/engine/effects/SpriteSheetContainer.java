package engine.effects;

import java.awt.Graphics2D;

import managers.GraphicsManager;
import content.Content;
import engine.Game;
import engine.GameTime;
import engine.camera.Camera;
import engine.graphics.SpriteSheet;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.Vector2;

public class SpriteSheetContainer
{
	// Transform.
	Vector2 position;
	float rotation;
	Vector2 scale;
	
	// Sprite sheet
	private SpriteSheet shit;
	private int index = 0;
	private int numOfSprites;
	
	public int timeScale = 2;
	public int timeScaleCounter = 0;
	
	// Info fields.
	public boolean isActive = false;
	
	public SpriteSheetContainer(String spriteSheetName, Vector2 position, float rotation, Vector2 scale, int timeScale)
	{
		shit = Content.spriteSheets.get(spriteSheetName);
		if(shit == null)
		{
			System.err.println("There is no spriteSheet with such name: " + spriteSheetName);
			System.err.println("kurac");
			Game.game.exitGame();
		}
		
		numOfSprites = shit.numOfSprites();
		
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.timeScale = timeScale;
	}
	
	public void play(Vector2 position)
	{
		this.position = position;
		index = 0;
		isActive = true;
	}
	
	public void play()
	{
		index = 0;
		isActive = true;
	}
	
	public void play(Vector2 position, float rotation, Vector2 scale)
	{
		this.rotation = rotation;
		this.scale = scale;
		play(position);
	}
	
	public void update(GameTime gameTime)
	{

		timeScaleCounter++;
		if(timeScaleCounter == timeScale)
		{
			index++;
			timeScaleCounter = 0;
		}
		
		if(index >= numOfSprites)
		{
			isActive = false;
		}
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		if(isActive == true)
		{
			GraphicsManager.saveGraphicsContext(g2d);
			
			Vector2 renderPosition = new Vector2(
					position.x - (camera.position.x - camera.rotatedCornerVector.x),
					position.y - (camera.position.y - camera.rotatedCornerVector.y)						
							);
			g2d.translate(renderPosition.x, renderPosition.y);
			g2d.rotate(rotation);
			g2d.scale(scale.x, scale.y);
			
			shit.draw(g2d, index);
			
			GraphicsManager.restoreGraphicsContext(g2d);
		}
	}

	public SpriteSheetContainer clone()
	{
		SpriteSheetContainer clone = new SpriteSheetContainer(shit.getName(), this.position.clone(), this.rotation, this.scale.clone(), this.timeScale);
		clone.isActive = false;
		return clone;
	}
}
