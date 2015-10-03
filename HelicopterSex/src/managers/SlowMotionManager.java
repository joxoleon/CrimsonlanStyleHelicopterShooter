package managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import engine.Game;
import engine.GameTime;

public class SlowMotionManager
{
	// Configuration fields.
	private static boolean isHack = true;
	
	// Slow motion values
	private static float regularTimeRate = 1;
	private static float finalTimeRate = 0.5f;
	private static float currentTimeRate = 1;

	// Slow motion graphics.
	private static float maxAlpha = 0.3f;
	private static Color slomoColor = new Color(127.f / 255, 42.f / 255,
			210.f / 255, maxAlpha);
	private static Color transitionColor;
	private static BufferedImage slomoImage;

	// Slow motion flags.
	private static boolean isTransitionIn = false;
	private static boolean isTransitionOut = false;

	// Slow motion transition fields.
	private static float transitionDuration = 0.7f;
	private static float transitionDurationCounter = 0;

	// List of active songs (for transition).
	private static LinkedList<Long> playingSongIDs;

	public static void initialize()
	{
		slomoImage = new BufferedImage(Game.game.worldDimension.width,
				Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D) slomoImage.getGraphics();
		g2d.setPaint(slomoColor);
		g2d.fillRect(0, 0, slomoImage.getWidth(), slomoImage.getHeight());
		transitionColor = new Color(slomoColor.getRed() / 255,
				slomoColor.getGreen() / 255, slomoColor.getBlue() / 255, 0);

	}

	public static void update(GameTime gameTime)
	{

		if (isTransitionIn || isTransitionOut)
		{
			// Update counter
			transitionDurationCounter += gameTime.dt_s();

			// If the transition is finished - end transition.
			if (transitionDurationCounter > transitionDuration)
			{
				if (isTransitionOut == true)
				{
					setGameTimeScale(regularTimeRate);
					isTransitionOut = false;

				} else if (isTransitionIn == true)
				{
					setGameTimeScale(finalTimeRate);
					isTransitionIn = false;

				}
				transitionDurationCounter = 0;
				return;
			}

			// Calculate transition color.
			float alpha = transitionDurationCounter / transitionDuration
					* maxAlpha;
			if (isTransitionOut)
			{
				alpha = maxAlpha - alpha;
			}

			transitionColor = new Color((float) slomoColor.getRed() / 255,
					(float) slomoColor.getGreen() / 255,
					(float) slomoColor.getBlue() / 255, alpha);
			

			// Calculate transition timeScale.
			float newTimeRate = 1;
			float transitionRange = regularTimeRate - finalTimeRate;
			float percentage = transitionDurationCounter / transitionDuration;

			if (isTransitionIn == true)
			{
				newTimeRate = regularTimeRate - transitionRange * percentage;
			} else if (isTransitionOut == true)
			{
				newTimeRate = finalTimeRate + transitionRange * percentage;
			}

			setGameTimeScale(newTimeRate);

		}

	}

	public static void draw(Graphics2D g2d)
	{
		if (isTransitionIn == true || isTransitionOut == true)
		{
			g2d.setPaint(transitionColor);
			g2d.fillRect(0, 0, (int) (Game.game.worldDimension.getWidth()),
					(int) (Game.game.worldDimension.getHeight()));
		} else if (currentTimeRate != 1)
		{
			g2d.drawImage(slomoImage, 0, 0, Game.game.worldDimension.width,
					Game.game.worldDimension.height, 0, 0,
					slomoImage.getWidth(), slomoImage.getHeight(), null);

		}

	}

	public static void toggleTimeScale(LinkedList<Long> playingSongIDs)
	{
		SlowMotionManager.playingSongIDs = playingSongIDs;


		if (currentTimeRate == 1)
		{
			AudioManager.playOnceNoInterrupt("slomoSoundEffect01");
			isTransitionIn = true;
			isTransitionOut = false;
			transitionDurationCounter = 0;
		} else
		{
			isTransitionIn = false;
			isTransitionOut = true;
			transitionDurationCounter = 0;
//			hackAudioSpeedEarly();
			AudioManager.playOnceWithoutTimeAlter("slomoSoundEffectBackwards01");
		} 

	}

	private static void setGameTimeScale(float timeScale)
	{
		currentTimeRate = timeScale;
		Game.game.setGameTimeTimeScale(timeScale);


		AudioManager.playbackSpeed = timeScale;
		
		// Won't work because   me!
		for (Long long1 : playingSongIDs)
		{
			AudioManager.setPlaybackRate(long1, timeScale);
		}
		
	}

//	private static void setGameTimeScaleHack(float timeScale)
//	{
//		currentTimeRate = timeScale;
//		Game.game.setGameTimeTimeScale(timeScale);
//		
//	}
//	
//	private static void hackAudioSpeedEarly()
//	{
//		if(isHack == true)
//		{
//			AudioManager.playbackSpeed = regularTimeRate;
//			// Slowdown the currently playing songs
//			for (Long long1 : playingSongIDs)
//			{
//				AudioManager.setPlaybackRate(long1, regularTimeRate);
//			}
//		}
//	}
	
}
