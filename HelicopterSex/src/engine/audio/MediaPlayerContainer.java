package engine.audio;

import engine.GameTime;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaPlayerContainer
{
	MediaPlayer player;
	Duration mediaDuration;
	float mediaDurationCounter;
	float unknownThreshold = 2;
	String mediaName;
	
	public boolean isFinished = false;
	
	public MediaPlayerContainer(MediaPlayer player, Media media, String mediaName)
	{
		this.mediaName = mediaName;
		this.player = player;
		mediaDuration = media.getDuration();
		if(mediaDuration.equals(Duration.UNKNOWN))
		{
			mediaDurationCounter = unknownThreshold;
		}
		else
		{
			mediaDurationCounter = (float) mediaDuration.toSeconds();
		}
	}
	
	public void update(GameTime gameTime)
	{
		mediaDurationCounter -= gameTime.dt_s();
		if(mediaDurationCounter < 0)
		{
			this.isFinished = true;
//			player.
		}
	}
}
