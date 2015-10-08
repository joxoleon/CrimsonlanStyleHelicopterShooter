package factories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import engine.Game;
import engine.utility.Vector2;
import shooting.Gun;
import shooting.GunSlot;

public class GunFactory
{
	// Guns.
	public static Map<String, Gun> guns = new HashMap<String, Gun>();

	// Gun slot predefined combinations.
	public static Map<String, LinkedList<GunSlot>> gunSlotCombinations = new HashMap<String, LinkedList<GunSlot>>();
	public static ArrayList<String> gunSlotCombinationNames = new ArrayList<String>();

	public static void loadGuns(String path)
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(path));

			String line = br.readLine();

			while (line != null)
			{
				if (line.charAt(0) == '#')
				{
					continue;
				}
				String[] tokens = line.split(" ");

				if (tokens.length != 6)
				{
					System.err.println("Error: invalid line in guns.txt");
					Game.game.exitGame();
				}

				String gunName = tokens[0];
				String shotSpriteName = tokens[1];
				float scaleX = Float.parseFloat(tokens[2]);
				float scaleY = Float.parseFloat(tokens[3]);
				float shotSpeed = Float.parseFloat(tokens[4]);
				float damage = Float.parseFloat(tokens[5]);

				Gun gun = new Gun(gunName, shotSpriteName, new Vector2(scaleX,
						scaleY), shotSpeed, damage);
				guns.put(gunName, gun);

				line = br.readLine();
			}

		} catch (Exception e)
		{
			System.err.println("Could not read from file guns.txt");
			Game.game.exitGame();
		}
	}

	public static void loadGunSlots(String path)
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(path));

			String line = br.readLine();

			int currentState = 0;
			int gunSlotCount = 0;
			int gunSlotCounter = 0;
			LinkedList<GunSlot> slots = null;
			
			
			while (line != null)
			{
				if (line.length() == 0 || line.startsWith("#"))
				{
					line = br.readLine();
					continue;
				}
				String[] tokens = line.split(" ");

				switch (currentState)
				{
				// Read the name of the slotCombinatio and the number of slots.
				case 0:
				{
					if(tokens.length != 2)
					{
						System.err.println("Error: invalid number of arguments in gunSlots.txt. Line: " + line);
						Game.game.exitGame();
					}
					
					
					if(tokens[0].equals("dekiPantelic09"))
					{
						@SuppressWarnings("unused")
						int x = 5;
					}
					
					String combinationName = tokens[0];
					gunSlotCount = Integer.parseInt(tokens[1]);
					
					// Insert combination name into the array list.
					gunSlotCombinationNames.add(combinationName);
					slots = new LinkedList<GunSlot>();
					gunSlotCombinations.put(combinationName, slots);
					
					// Pass onto the next combination.
					currentState = 1;

				}
					break;

				// Read the GunSlot
				case 1:
				{
					if(tokens.length != 5)
					{
						System.err.println("Error: invalid number of arguments in gunSlots.txt. Line: " + line);
						Game.game.exitGame();
					}
					
					Gun gun = guns.get(tokens[0]);
					if(gun == null)
					{
						System.err.println("Error: there is no gun by such name in gunSlots.txt. Line: " + line);
						Game.game.exitGame();
					}
					
					GunSlot slot = new GunSlot();
					float offsetX = Float.parseFloat(tokens[1]);
					float offsetY = Float.parseFloat(tokens[2]);
					float rotation = Float.parseFloat(tokens[3]);
					float shotPeriod = Float.parseFloat(tokens[4]);
					
					slot.gun = gun;
					slot.offset = new Vector2(offsetX, offsetY);
					slot.rotation = rotation;
					slot.shotPeriod = shotPeriod;
					
					slots.add(slot);
					
					gunSlotCounter++;
					if(gunSlotCounter == gunSlotCount)
					{
						gunSlotCounter = 0;
						currentState = 0;
					}

				}
					break;

				}

				line = br.readLine();
			}

		} catch (Exception e)
		{
			System.err.println("Could not read from file gunSlots.txt");
			Game.game.exitGame();
		}

	}

	public static LinkedList<GunSlot> getGunSlotCombination(String combinationName)
	{
		LinkedList<GunSlot> newSlotCombination = new LinkedList<GunSlot>();
		LinkedList<GunSlot> combination = gunSlotCombinations.get(combinationName);
		
		if(combination == null)
		{
			System.err.println("Error: there is no gun slot combination with that name.");
			Game.game.exitGame();
		}
		
		for (GunSlot gunSlot : combination)
		{
			newSlotCombination.add(gunSlot.clone());
		}
		
		return newSlotCombination;
	}
}
