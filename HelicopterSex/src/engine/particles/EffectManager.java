package engine.particles;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import engine.GameTime;
import engine.camera.Camera;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.Vector2;

public class EffectManager
{
	private static Map<String, ParticleEmitter> emittersMap = new HashMap<String, ParticleEmitter>();
	
	private static ParticleEmitter explosionEmitter;
	
	public static void initialize()
	{
		Vector2 position = new Vector2(500, 500);
		float rotation = 0;
		Vector2 scale = new Vector2(1, 1);
		
		explosionEmitter = new ParticleEmitter("explosion02", position, rotation, scale);
		explosionEmitter.play();
		
	}
	
	public static void update(GameTime gameTime)
	{
		if(Input.isKeyPressed(Keys.E))
		{
			explosionEmitter.play();
		}
		explosionEmitter.update(gameTime);
	}
	
	public static void draw(Graphics2D g2d, Camera camera)
	{
		explosionEmitter.draw(g2d, camera);
	}
}
