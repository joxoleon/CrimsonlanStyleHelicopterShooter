package engine.effects;

import java.awt.Graphics2D;

import managers.AudioManager;
import engine.GameTime;
import engine.camera.Camera;
import engine.utility.Vector2;

public class Effect
{
	public SpriteSheetContainer sheetContainer;
	public String soundEffectName;
	public boolean isActive = false;
	public String name;
	
	
	public void play(Vector2 position, int timeScale)
	{
		sheetContainer.timeScale = timeScale;
		play(position);
	}
	
	public void play(Vector2 position, float rotation, Vector2 scale, int timeScale)
	{
		sheetContainer.timeScale = timeScale;
		play(position, rotation, scale);
	}
	
	public void play(Vector2 position, float rotation, Vector2 scale)
	{
		sheetContainer.play(position, rotation, scale);
		AudioManager.playOnceNoInterrupt(soundEffectName);
		isActive = true;
	}
	
	public void play(Vector2 position)
	{
		sheetContainer.play(position);
		AudioManager.playOnceNoInterrupt(soundEffectName);
		isActive = true;
	}

	public void update(GameTime gameTime)
	{
		if(isActive == true)
		{
			sheetContainer.update(gameTime);
			if(sheetContainer.isActive == false)
			{
				this.isActive = false;
			}
		}
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		if(isActive == true)
		{
			sheetContainer.draw(g2d, camera);
		}
	}
	
	public Effect clone()
	{
		Effect clone = new Effect();
		clone.sheetContainer = sheetContainer.clone();
		clone.soundEffectName = soundEffectName;
		
		return clone;
	}
}
