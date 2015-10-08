package engine.gamestates;

import java.awt.Graphics2D;

import engine.Game;
import engine.GameTime;
import engine.input.Input;
import engine.input.Keys;

public class HelpGameState
extends GameState
{

	@Override
	public void enterState()
	{
		System.out.println("Enter help game state.");
	}

	@Override
	public void update(GameTime gameTime)
	{
		if (Input.isKeyPressed(Keys.NumPad1))
		{
			Game.game.gameStateMachine.changeState(Game.game.mainMenuState);
		}
		
		if (Input.isKeyPressed(Keys.NumPad2))
		{
			Game.game.gameStateMachine.changeState(Game.game.playCampaignGameState);
		}
		
		if (Input.isKeyPressed(Keys.NumPad3))
		{
			Game.game.gameStateMachine.changeState(Game.game.gameOptionsState);
		}
		
		if (Input.isKeyPressed(Keys.NumPad4))
		{
			Game.game.gameStateMachine.changeState(Game.game.helpGameState);
		}
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		
	}

	@Override
	public void exitState()
	{
		System.out.println("Exit help game state.");
	}

}
