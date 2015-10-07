package factories;

import java.util.HashMap;
import java.util.Map;

import engine.Game;
import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import scripts.ScriptComponent;

public class ActorScriptFactory
{
	private static  Map<String, ScriptComponent> scripts = new HashMap<String, ScriptComponent>();
	
	public static void initialize()
	{
		PropellerScript propellerScript = new PropellerScript();
		scripts.put("propellerScript", propellerScript);
		
		PlayerFireScript fireScript = new PlayerFireScript();
		scripts.put("playerFireScript", fireScript);
		
		PlayerControlScript playerControlScript = new PlayerControlScript();
		scripts.put("playerControlScript", playerControlScript);
	}
	
	public static ScriptComponent getScript(String scriptName)
	{
		ScriptComponent script = scripts.get(scriptName);
		if(script == null)
		{
			System.err.println("There is no script with such name: " + scriptName);
			Game.game.exitGame();
		}
		return (ScriptComponent) script.clone();
	}
}
