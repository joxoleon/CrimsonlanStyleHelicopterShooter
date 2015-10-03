package shooting;

import engine.GameTime;
import utility.Vector2;

public class GunSlot
{
	public Gun gun;
	public Vector2 offset = new Vector2();
	public float rotation;
	public float shotPeriod = 0.3f;
	public float lastShotElapsed = 0;
	
	
	public void update(GameTime gameTime)
	{
		lastShotElapsed += gameTime.dt_s();
	}
	
	public int fire(Vector2 position, float rotation)
	{
		if(gun != null && lastShotElapsed > shotPeriod)
		{
			Vector2 rotatedOffset = offset.clone();
			rotatedOffset.rotate(rotation);
			Vector2 shotPosition = Vector2.add(position, rotatedOffset);
			lastShotElapsed = 0;
			gun.fire(shotPosition, rotation + this.rotation);
			return 1;
		}
		
		return 0;
	}
	
	public GunSlot clone()
	{
		GunSlot slot = new GunSlot();
		slot.gun = gun.clone();
		slot.rotation = rotation;
		slot.offset = offset.clone();
		slot.shotPeriod = shotPeriod;
		
		return slot;
	}
	
}
