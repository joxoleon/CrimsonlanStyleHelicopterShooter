package factories;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import content.Content;
import engine.Actor;
import engine.Game;
import engine.component.GraphicsComponent;
import engine.component.GunComponent;
import engine.component.PhysicsComponent;
import engine.component.SphereCollider;
import engine.graphics.Sprite;
import engine.utility.MyFileReader;

public class ShipFactory
{
	public static Map<String, Actor> ships = new HashMap<String, Actor>();
	public static ArrayList<String> shipNames = new ArrayList<String>();
	
	public static void initialize()
	{
		loadShips();
	}
	
	private static void loadShips()
	{
		String path = "content/ships.txt";
		MyFileReader reader = new MyFileReader(path);
		int currentState = 0;
		
		Actor ship = null;
		
		while(reader.hasMore == true)
		{
		
			// Simple state machine
			switch(currentState)
			{
			// State 0 :Read ship name nad image name, create a ship, add a graphics component, and put it into the hashMap.
			case 0:
			{
				String[] tokens = reader.getNextLineTokens(4);
				
				String shipName = tokens[0];
				String spriteName = tokens[1];
				float scaleX = Float.parseFloat(tokens[2]);
				float scaleY = Float.parseFloat(tokens[3]);
				
				ship = new Actor();
				ship.name = shipName;
				shipNames.add(ship.name);
				ships.put(ship.name, ship);
				
				// Add graphics component.
				GraphicsComponent gc = new GraphicsComponent();
				BufferedImage image = Content.images.get(spriteName);
				if(image == null)
				{
					System.err.println("There is no image with such name: " + spriteName);
					Game.game.exitGame();
				}
				
				Sprite sprite = new Sprite(spriteName, image, image.getWidth(), image.getHeight());
				sprite.setScale(scaleX, scaleY);
				gc.renderable = sprite;
				ship.graphicsComponent = gc;
				
				currentState = 1;
				
				
			}break;
			
			// Read physics component fields in one line, Create a physics component, and give it to the chopper.
			case 1:
			{
				String[] tokens = reader.getNextLineTokens(6);
				
				PhysicsComponent pc = new PhysicsComponent();
				pc.mass = Float.parseFloat(tokens[0]);
				pc.momentOfInertia = Float.parseFloat(tokens[1]);
				pc.accelerationIntensity = Float.parseFloat(tokens[2]);
				pc.angularAccelerationIntensity = Float.parseFloat(tokens[3]);
				pc.maxVelocitySquared = Float.parseFloat(tokens[4]);
				pc.maxAngularVelocity = Float.parseFloat(tokens[5]);

				ship.addBasicComponent(pc);
				
				SphereCollider collider = new SphereCollider();
				ship.addBasicComponent(collider);
				
				currentState = 2;
			}break;
			// Read the name of the gun slot combination. Create and add a GunComponent.
			case 2:
			{
				String[] tokens = reader.getNextLineTokens(1);
				if(tokens[0].equals("none") == false)
				{	
					GunComponent gunComponent = new GunComponent();
					gunComponent.setGunSlotCombination(tokens[0], GunFactory.getGunSlotCombination(tokens[0]));
					ship.addBasicComponent(gunComponent);
				}
				
				currentState = 3;
			}break;
			
			case 3:
			{
				String[] tokens = reader.getNextLineTokens();
				
				for (String token : tokens)
				{
					ship.addScriptComponent(ActorScriptFactory.getScript(token));
				}
				
				currentState = 0;
			}break;
			
			}			
		}
	}
	
	
	public static Actor getShip(String shipName)
	{

		Actor ship = ships.get(shipName);
		if(ship == null)
		{
			System.err.println("The ship with the following name doens't exist: " + shipName);
			Game.game.exitGame();
		}
		
		return ship.clone();
	}
}
