package engine.particles;

import java.awt.Graphics2D;

import managers.AudioManager;
import engine.GameTime;
import engine.camera.Camera;
import engine.utility.Vector2;

public class Effect
{
	private ParticleEmitter emitter;
	private String soundEffectName;
	public boolean isActive = false;
	public String name;
	
	
	public void play(Vector2 position, int timeScale)
	{
		emitter.timeScale = timeScale;
		play(position);
	}
	
	public void play(Vector2 position, float rotation, Vector2 scale, int timeScale)
	{
		emitter.timeScale = timeScale;
		play(position, rotation, scale);
	}
	
	public void play(Vector2 position, float rotation, Vector2 scale)
	{
		emitter.play(position, rotation, scale);
		AudioManager.playOnceNoInterrupt(soundEffectName);
		isActive = true;
	}
	
	public void play(Vector2 position)
	{
		emitter.play(position);
		AudioManager.playOnceNoInterrupt(soundEffectName);
		isActive = true;
	}

	public void update(GameTime gameTime)
	{
		if(isActive == true)
		{
			emitter.update(gameTime);
			if(emitter.isActive == false)
			{
				this.isActive = false;
			}
		}
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		if(isActive == true)
		{
			emitter.draw(g2d, camera);
		}
	}
	
	public Effect clone()
	{
		Effect clone = new Effect();
		clone.emitter = emitter.clone();
		clone.soundEffectName = soundEffectName;
		
		return clone;
	}
}
