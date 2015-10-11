package engine.audio;

import javafx.scene.media.MediaPlayer;

public class EndOfMediaHandler
implements Runnable
{
	private MediaPlayer player;
	
	public EndOfMediaHandler(MediaPlayer player)
	{
		this.player = player;
	}

	@Override
	public void run()
	{
		player.dispose();
	}

}
