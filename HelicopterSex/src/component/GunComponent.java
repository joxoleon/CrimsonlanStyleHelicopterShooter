package component;

import input.Input;
import input.Keys;

import java.util.LinkedList;

import managers.AudioManager;
import shooting.GunSlot;
import engine.GameTime;
import factories.GunFactory;

public class GunComponent
extends ActorComponent
{
	public LinkedList<GunSlot> gunSlots = new LinkedList<GunSlot>();
	public int gunSlotsIndex = 0;

	
	@Override
	public void onAttach()
	{		
	}
	
	@Override
	public String getName()
	{
		return "gunComponent";
	}

	@Override
	public void update(GameTime gameTime)
	{
		testUpdateGunSlots();
		for (GunSlot gunSlot : gunSlots)
		{
			gunSlot.update(gameTime);
		}
	}

	@Override
	public void onDestroy()
	{
		
	}
	public void testUpdateGunSlots()
	{
		if(Input.isKeyPressed(Keys.I))
		{

			gunSlotsIndex += 1;
			gunSlotsIndex %= GunFactory.gunSlotCombinationNames.size();
			
			String key = GunFactory.gunSlotCombinationNames.get(gunSlotsIndex);
			gunSlots = GunFactory.getGunSlotCombination(key);
		}
		
		if(Input.isKeyPressed(Keys.O))
		{ 
			gunSlotsIndex += GunFactory.gunSlotCombinationNames.size() - 1;
			gunSlotsIndex %= GunFactory.gunSlotCombinationNames.size();
			
			String key = GunFactory.gunSlotCombinationNames.get(gunSlotsIndex);
			gunSlots = GunFactory.getGunSlotCombination(key);
		}
	}
	
	public void fire()
	{
		int shotSuccesfull = 0;

		for (GunSlot gunSlot : gunSlots)
		{
			shotSuccesfull += gunSlot.fire(parent.position, parent.rotation);
		}
		
		if(shotSuccesfull > 0)
		{
			AudioManager.playOnceNoInterrupt("gunShot01");
		}
	}
}