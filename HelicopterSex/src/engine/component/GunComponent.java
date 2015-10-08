package engine.component;

import java.util.LinkedList;

import managers.AudioManager;
import shooting.GunSlot;
import engine.GameTime;
import engine.input.Input;
import engine.input.Keys;
import factories.GunFactory;

public class GunComponent
extends ActorComponent
{
	private String gunSlotCombinationName;
	private LinkedList<GunSlot> gunSlots = new LinkedList<GunSlot>();
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

	public void setGunSlotCombination(String name, LinkedList<GunSlot> slots)
	{
		this.gunSlotCombinationName = name;
		gunSlots = slots;
	}
	
	@Override
	public ActorComponent clone()
	{
		GunComponent clone = new GunComponent();
		clone.setGunSlotCombination(gunSlotCombinationName, GunFactory.getGunSlotCombination(gunSlotCombinationName));
		return clone;
	}
}
