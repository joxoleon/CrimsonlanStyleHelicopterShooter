package engine.effects;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import engine.Game;
import engine.GameTime;
import engine.camera.Camera;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.MyFileReader;
import engine.utility.Vector2;

public class EffectManager
{
	private static Map<String, Effect> effectsMap = new HashMap<String, Effect>();
	
	private static LinkedList<Effect> activeEffects = new LinkedList<Effect>();
	private static LinkedList<Effect> queuedEffects = new LinkedList<Effect>();
	
	
	public static void initialize()
	{
		loadEffects("content/effects.txt");
		
	}

	
	public static void update(GameTime gameTime)
	{
		ListIterator<Effect> queuedIterator = queuedEffects.listIterator();
		while(queuedIterator.hasNext())
		{
			Effect effect = queuedIterator.next();
			effect.queueCounter -= gameTime.dt_s();
			if(effect.queueCounter <= 0)
			{
				queuedIterator.remove();
				playQueuedEffect(effect);
			}
		}
		
		for (Effect effect : activeEffects)
		{
			effect.update(gameTime);
		}
	}
	
	public static void draw(Graphics2D g2d, Camera camera)
	{
		for (Effect effect : activeEffects)
		{
			effect.draw(g2d, camera);
		}
	}
	
	public static void filter()
	{
		ListIterator<Effect> iterator = activeEffects.listIterator();
		while(iterator.hasNext())
		{
			Effect effect = iterator.next();
			if(effect.isActive == false)
			{
				iterator.remove();
			}
		}
	}
	
	private static void loadEffects(String path)
	{		
		MyFileReader reader = new MyFileReader(path);
		while(reader.hasMore == true)
		{
			String[] tokens = reader.getNextLineTokens(4);
			
			Effect effect = new Effect();
			
			// effect name.
			effect.name = tokens[0];
			
			// sound effect name.
			effect.soundEffectName = tokens[1];
			
			// Create spriteSheetContainer
			String spriteSheetName = tokens[2];
			int spriteSheetTimeScale = Integer.parseInt(tokens[3]);
			SpriteSheetContainer container = new SpriteSheetContainer(spriteSheetName, new Vector2(), 0, new Vector2(1,1), spriteSheetTimeScale);
			effect.sheetContainer = container;
			
			effectsMap.put(effect.name, effect);
		}
	}
	
	public static void playEffect(String effectName, Vector2 position)
	{
		Effect effect = effectsMap.get(effectName);
		if(effect == null)
		{
			System.err.println("There is no effect with such name: " + effectName);
			Game.game.exitGame();
		}
		
		effect = effect.clone();
		effect.play(position);
		activeEffects.add(effect);
	}
	
	public static void queueEffect(String effectName, Vector2 position, float delay)
	{
		Effect effect = effectsMap.get(effectName);
		if(effect == null)
		{
			System.err.println("There is no effect with such name: " + effectName);
			Game.game.exitGame();
		}
		
		effect = effect.clone();
		effect.queueCounter = delay;
		effect.queuedPosition = position;
		queuedEffects.add(effect);
	}
	
	private static void playQueuedEffect(Effect effect)
	{
		effect.play(effect.queuedPosition);
		activeEffects.add(effect);
	}
	
}
