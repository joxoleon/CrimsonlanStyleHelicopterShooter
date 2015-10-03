package shooting;

import engine.Game;
import factories.ShotFactory;
import utility.Vector2;

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
	
	// ******************** Constructors ******************** 
	public Gun(String gunName, String shotSpriteName, Vector2 scale, float shotSpeed, float damage)
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
		shot.initialize(position, gunRotation, scale, shotSpeed, shotSprite, damage);
	}

	public Gun clone()
	{
		Gun gun = new Gun(gunName, shotSpriteName, scale.clone(), shotSpeed, damage);
		gun.isMinigun = isMinigun;
		gun.maxRotationAngle = maxRotationAngle;
		return gun;
	}
}
