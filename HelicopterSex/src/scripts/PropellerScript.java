package scripts;

import engine.GameTime;
import engine.component.ActorComponent;
import engine.graphics.Model;
import engine.graphics.Sprite;

public class PropellerScript 
extends ScriptComponent
{
	// Fields.
	private Model model;
	private int numberOfPropellers;
	private Sprite[] propellers;
	private double[] propellerSpeeds;
	private double[] propellerRotationAngles;
	private double propellerBaseSpeed = 12;
	private boolean isInitialized = false;

	

	@Override
	public void onAttach()
	{
		if(parent.graphicsComponent != null)
		{
			model = parent.graphicsComponent.renderable.getModel();
			numberOfPropellers = model.numOfSprites() - 1;
			propellers = new Sprite[numberOfPropellers];
			propellerSpeeds = new double[numberOfPropellers];
			propellerRotationAngles = new double[numberOfPropellers];
			for (int i = 0; i < propellers.length; i++)
			{
				propellers[i] = model.getSprite("propeller0" + (i + 1));
				propellerSpeeds[i] = propellerBaseSpeed + 10 * i;
				
			}
			
			for (Sprite propeller : propellers)
			{
				propeller.isAutonomouslyRotating = true;
			}
			isInitialized = true;
		}
		
	}
	
	@Override
	public String getName()
	{
		return "scriptComponent";
	}
	
	@Override
	public void update(GameTime gameTime)
	{
		if(isInitialized == true)
		{
			for (int i = 0; i < propellers.length; i++)
			{
				propellerRotationAngles[i] += propellerSpeeds[i] * gameTime.dt_s();
				propellers[i].setRotation((float)propellerRotationAngles[i]);
			}
		}
		else
		{
			System.err.println("Error: The propeller script has not been initialized. You need to add the script after the graphics component!");
		}
		
	}

	@Override
	public void onDestroy()
	{
		
	}



	@Override
	public ActorComponent clone()
	{
		return new PropellerScript();
	}



}
