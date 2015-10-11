package shooting;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import terrain.TerrainManager;
import engine.GameTime;
import engine.camera.Camera;
import engine.utility.Vector2;

public class SimpleShot 
implements IShootable
{
	// ******************** Fields ******************** 
	
	public static AffineTransform backupTransform;
	
	// Movement
	public Vector2 previousPosition;
	public Vector2 position;
	public float rotation;
	public Vector2 scale = new Vector2(1, 1);
	public Vector2 direction;
	public float shotSpeed;
	public float damage = 0;
	
	// Sprite
	public ShotSprite shotSprite;
	
	// Update fields.
	public boolean isActive = true;
	
	// On hit fields.
	public String effectName;
	private float throwBackIntensity;
	
	// ******************** Constructors ******************** 
	public void initialize(Vector2 position, float rotation, Vector2 scale, float shotSpeed, ShotSprite shotSprite, float damage, String effectName, float throwbackIntensity)
	{		
		this.position = position;
		this.previousPosition = position.clone();
		this.rotation = rotation;
		this.direction = new Vector2(0, -1);
		this.direction.rotate(rotation);
		this.shotSpeed = shotSpeed;
		this.shotSprite = shotSprite;
		this.scale = scale;
		this.isActive = true;
		this.damage = damage;
		this.effectName = effectName;
		this.throwBackIntensity = throwbackIntensity;
		
		// Add to shot manager.
		ShotManager.playerShots.addLast(this);
		
	}
	
	
	// ******************** Methods ******************** 
	public void update(GameTime gameTime)
	{
		if(isActive == true)
		{
			// Set previous position.
			previousPosition.x = position.x;
			previousPosition.y = position.y;

			
			// Calculate
			position.add(Vector2.mul(direction, shotSpeed * gameTime.dt_s()));
			if(position.x > TerrainManager.terrainWidth || position.x < 0 || position.y > TerrainManager.terrainHeight || position.y < 0)
			{
				isActive = false;
			}
		}
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		// Initialize graphics context.
		backupTransform = g2d.getTransform();
		g2d.translate(position.x - (camera.position.x - camera.rotatedCornerVector.x), position.y - (camera.position.y - camera.rotatedCornerVector.y));
		g2d.rotate(rotation);
		g2d.scale(scale.x, scale.y);
		
		shotSprite.draw(g2d);		
		
		// Restore graphics context.
		g2d.setTransform(backupTransform);
		
	}

	public Vector2 getThrowbackVector()
	{
		return Vector2.mul(direction, throwBackIntensity);
	}
}
