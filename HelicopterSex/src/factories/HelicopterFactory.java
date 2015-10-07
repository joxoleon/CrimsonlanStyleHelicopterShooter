package factories;

import java.util.HashMap;
import java.util.Map;

import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import component.GraphicsComponent;
import component.PhysicsComponent;
import engine.Actor;

public class HelicopterFactory
{
	// ******************** Fields ********************
	public static Map<String, Actor> helicopters = new HashMap<String, Actor>();
	
	
	
	// ******************** Constructors ********************
	
	
	
		
	// ******************** Methods ********************
	public static void initialize()
	{
		
	}
	
	private static void loadHelicopters()
	{
		Actor helicopter = new Actor();
		
		// Add graphics component.
		GraphicsComponent gc = new GraphicsComponent();
		gc.renderable = ModelFactory.getInstance().getFlyweightModel("helicopter03");
		helicopter.graphicsComponent = gc;
		
		// Add physics component.
		PhysicsComponent pc = new PhysicsComponent();
		helicopter.addBasicComponent(pc);
		
		// Add script components ****
		// Propeller script.
		helicopter.addScriptComponent(new PropellerScript());
		// Second pass script for firing.
		PlayerFireScript fireScript = new PlayerFireScript();
		helicopter.addScriptComponent(fireScript);
		// Player control script.
		helicopter.addScriptComponent(new PlayerControlScript());
		
		
		
	}
}
