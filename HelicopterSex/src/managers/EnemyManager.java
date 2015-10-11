package managers;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.ListIterator;

import shooting.SimpleShot;
import engine.Actor;
import engine.GameTime;
import engine.camera.Camera;
import engine.utility.Vector2;
import factories.ShipFactory;

public class EnemyManager
{
	private static Vector2 terrainDimensions;
	
	public static LinkedList<Actor> enemies = new LinkedList<Actor>();
	private static float nextEnemyPeriod = 0.5f;
	private static float nextEnemyCounter = 0;
	
	
	public static void initialize(Vector2 terrainDimensions)
	{
		EnemyManager.terrainDimensions = terrainDimensions;
		
		
		
		for (int i = 0; i < 40; i++)
		{
			spawnEnemy();
		}
		
		spawnEnemy();
		enemies.getLast().position = new Vector2(800, 150);
	}
	
	
	public static void update(GameTime gameTime)
	{
		// Spawn next enemy.
		nextEnemyCounter += gameTime.dt_s();
		if(nextEnemyCounter > nextEnemyPeriod)
		{
			spawnEnemy();
			nextEnemyCounter -= nextEnemyPeriod;
		}
		
		for (Actor enemy : enemies)
		{
			enemy.update(gameTime);
		}
	}
	
	
	public static void draw(Graphics2D g2d, Camera camera)
	{
		
		for (Actor enemy : enemies)
		{
			enemy.draw(g2d, camera);
			
			enemy.collider.draw(g2d, camera);
		}
	}
	
	private static void spawnEnemy()
	{
		Vector2 position = generateNextPosition();
		int shipNumber = (int) (Math.random() * 5) + 1;
		Actor enemy = ShipFactory.getShip("enemy0" + shipNumber);
		enemy.position = position;
		enemies.add(enemy);
		
	}
	
	private static Vector2 generateNextPosition()
	{
		Vector2 position = new Vector2(0,0);
		float offset = 50;
		
		int side = (int)(Math.random() * 4);
		
		switch(side)
		{
		// North
		case 0:
		{
			position.x = (float) (Math.random() * terrainDimensions.x);
			position.y = -offset;
		}break;
		
		// South
		case 1:
		{
			position.y = terrainDimensions.y + offset;
			position.x = (float) (Math.random() * terrainDimensions.x);
		}break;
		
		// East.
		case 2:
		{
			position.x = -offset;
			position.y = (float) (Math.random() * terrainDimensions.y);
		}break;
		
		// West.
		case 3:
		{
			position.x = terrainDimensions.x + offset;
			position.y = (float) (Math.random() * terrainDimensions.y);
		}break;
		
		}
		
		
		return position;
	}
	
	public static void filter()
	{
		ListIterator<Actor> listIterator = enemies.listIterator();
		while(listIterator.hasNext())
		{
			Actor enemy = listIterator.next();
			if(enemy.isActive == false)
			{
				enemy.inactiveCounter ++;
				if(enemy.inactiveCounter >= 4)
				{
					listIterator.remove();
				}
			}
		}
	}
	
	
}
