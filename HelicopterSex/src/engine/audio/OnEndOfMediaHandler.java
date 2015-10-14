package engine.audio;

import javafx.scene.media.MediaPlayer;

public class OnEndOfMediaHandler
implements Runnable
{
	MediaPlayer player;
	
	public OnEndOfMediaHandler(MediaPlayer player)
	{
		this.player = player;
	}

	@Override
	public void run()
	{
		player.dispose();
	}
}
