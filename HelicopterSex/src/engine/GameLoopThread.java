package engine;

import engine.menu.Score;

public class GameLoopThread
extends Thread
{
	private boolean run = true;
	private Game game;
	public GameTime gameTime;
	
	
	public GameLoopThread(Game game, long targetRate)
	{
		this.game = game;
		this.gameTime = new GameTime(targetRate);
	}
	
	public void stopGameLoop()
	{
		run = false;
		interrupt();
	}



	@Override
	public void run()
	{
		// Initialization - that is mandatory in the thread.
		initializeInThread();
		
		run = true;
				
		while (run == true)
		{
			try
			{
				gameTime.beginCycle();
				
				game.update(gameTime);
				game.draw();
				game.repaint();
				
				long sleepTime = gameTime.calculateSleepTime();
				Thread.sleep(Math.max(0, sleepTime));
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	public static void initializeInThread()
	{
		Score.loadHighScoresFromFile();
	}

}
