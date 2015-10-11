package factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import engine.Actor;
import engine.Game;
import engine.component.GraphicsComponent;
import engine.component.GunComponent;
import engine.component.PhysicsComponent;
import engine.component.SphereCollider;
import engine.utility.MyFileReader;

public class HelicopterFactory
{
	// ******************** Fields ********************
	public static Map<String, Actor> helicopters = new HashMap<String, Actor>();
	
	public static ArrayList<String> helicopterNames = new ArrayList<String>();
	
	// ******************** Constructors ********************
	
	
	
		
	// ******************** Methods ********************
	public static void initialize()
	{
		loadHelicopters();
	}
	
	private static void loadHelicopters()
	{
		String path = "content/helicopters.txt";
		MyFileReader reader = new MyFileReader(path);
		int currentState = 0;
		
		Actor helicopter = null;
		
		while(reader.hasMore == true)
		{
		
			// Simple state machine
			switch(currentState)
			{
			// State 0 :Read chopper name, create a chopper, add a graphics component, and put it into the hashMap.
			case 0:
			{
				String[] tokens = reader.getNextLineTokens(1);
				
				helicopter = new Actor();
				helicopter.name = tokens[0];
				helicopterNames.add(helicopter.name);
				helicopters.put(helicopter.name, helicopter);
				
				// Add graphics component.
				GraphicsComponent gc = new GraphicsComponent();
				gc.renderable = ModelFactory.getInstance().getFlyweightModel(helicopter.name);
				helicopter.graphicsComponent = gc;
				
				currentState = 1;
				
				
			}break;
			
			// Read physics component fields in one line, Create a physics component, and give it to the chopper.
			// Add a sphereCollider!
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

				helicopter.addBasicComponent(pc);
				
				SphereCollider collider = new SphereCollider();
				helicopter.addBasicComponent(collider);
				
				currentState = 2;
			}break;
			// Read the name of the gun slot combination. Create and add a GunComponent.
			case 2:
			{
				String[] tokens = reader.getNextLineTokens(1);
				
				GunComponent gunComponent = new GunComponent();
				gunComponent.setGunSlotCombination(tokens[0], GunFactory.getGunSlotCombination(tokens[0]));
				helicopter.addBasicComponent(gunComponent);
				
				currentState = 3;
			}break;
			
			// Read script names and create them from the ActorScriptFactory.
			case 3:
			{
				String[] tokens = reader.getNextLineTokens();
				
				for (String token : tokens)
				{
					helicopter.addScriptComponent(ActorScriptFactory.getScript(token));
				}
				
				currentState = 4;
			}break;
			
			// Read eventHandler names and create them from the EventHandlerFactory.
			case 4:
			{
				String[] tokens = reader.getNextLineTokens();
				
				for (String token : tokens)
				{
					helicopter.addEventHandler(EventHandlerFactory.getEventHandler(token));
				}
				
				currentState = 0;
			}break;
			
			
			}			
		}
	}

	public static Actor getHelicopter(String helicopterName)
	{

		Actor helicopter = helicopters.get(helicopterName);
		if(helicopter == null)
		{
			System.err.println("The helcopter with the following name doens't exist: " + helicopterName);
			Game.game.exitGame();
		}
		
		return helicopter.clone();
	}

}
