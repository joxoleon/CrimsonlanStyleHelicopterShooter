package shooting;

import engine.Game;
import engine.audio.AudioManager;
import engine.utility.Vector2;
import factories.ShotFactory;

public class Gun
{
	// ******************** Fields ******************** 
	String gunName;
	public Vector2 scale = new Vector2(1,1);
	public float shotSpeed = 100;
	public float damage = 5;
	public ShotSprite shotSprite;
	private String shotSpriteName;
	public boolean isMinigun = true;
	public float maxRotationAngle = 0.06f;
	public String soundEffectName;
	
	// On hit fields.
	public String effectName;
	public float throwBackIntensity;
	
	
	
	
	// ******************** Constructors ******************** 
	public Gun(String gunName, String shotSpriteName, Vector2 scale, float shotSpeed, float damage, String effectName, float throwBackIntensity, String soundEffectName)
	{
		shotSprite = ShotFactory.shotSprites.get(shotSpriteName);
		if(shotSprite == null)
		{
			System.err.println("Error : failed to initialize gun. There is no shot sprite with name " + shotSpriteName + " Initialize ShotFactory before GunFactory!!!");
			Game.game.exitGame();
		}
		this.gunName = gunName;
		this.scale = scale;
		this.shotSpeed = shotSpeed;
		this.damage = damage;
		this.shotSpriteName = shotSpriteName;
		this.effectName = effectName;
		this.throwBackIntensity = throwBackIntensity;
		this.soundEffectName = soundEffectName;
	}
	
	
	
	
	// ******************** Methods ******************** 
	public void fire(Vector2 position, float rotation)
	{
		float gunRotation = rotation;
		if(isMinigun == true)
		{
			gunRotation +=  Math.random() * maxRotationAngle - maxRotationAngle / 2;
		}
				
		SimpleShot shot = new SimpleShot();
		shot.initialize(position, gunRotation, scale, shotSpeed, shotSprite, damage, effectName, throwBackIntensity);
		AudioManager.playOnceNoInterrupt(soundEffectName);
	}

	public Gun clone()
	{
		Gun gun = new Gun(gunName, shotSpriteName, scale.clone(), shotSpeed, damage, effectName, throwBackIntensity, soundEffectName);
		gun.isMinigun = isMinigun;
		gun.maxRotationAngle = maxRotationAngle;
		return gun;
	}
}
