package engine.gamestates;

import java.awt.Graphics2D;

import content.BackgroundPanel;
import engine.GameTime;

public abstract class GameState
{
	protected BackgroundPanel backgroundPanel;
	
	public abstract void enterState();
	public abstract void update(GameTime gameTime);
	public abstract void draw(Graphics2D g2d);
	public abstract void exitState();
}
