package shooting;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import camera.Camera;
import utility.Vector2;
import engine.GameTime;

public class SimpleShot 
implements IShootable
{
	// ******************** Fields ******************** 
	
	public static AffineTransform backupTransform;
	
	// Movement
	public Vector2 position;
	public float rotation;
	public Vector2 scale = new Vector2(1, 1);
	public Vector2 direction;
	public float shotSpeed;
	public float damage = 0;
	
	// Direction
	public ShotSprite shotSprite;
	
	// Update fields.
	public boolean isActive = true;
	
	
	
	// ******************** Constructors ******************** 
	public void initialize(Vector2 position, float rotation, Vector2 scale, float shotSpeed, ShotSprite shotSprite, float damage)
	{		
		this.position = position;
		this.rotation = rotation;
		this.direction = new Vector2(0, -1);
		this.direction.rotate(rotation);
		this.shotSpeed = shotSpeed;
		this.shotSprite = shotSprite;
		this.scale = scale;
		this.isActive = true;
		this.damage = damage;
		
		// Add to shot manager.
		ShotManager.playerShots.addLast(this);
		
	}
	
	
	// ******************** Methods ******************** 
	public void update(GameTime gameTime)
	{
		if(isActive == true)
		{
			position.add(Vector2.mul(direction, shotSpeed * gameTime.dt_s()));
			if(position.x > 1980 || position.x < 0 || position.y > 1080 || position.y < 0)
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

}
