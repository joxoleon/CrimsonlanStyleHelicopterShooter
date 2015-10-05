import java.io.BufferedReader;
import java.io.FileReader;

import content.MyError;
import engine.Game;
import terrain.TerrainScrollDown;

public class Template
{
	// ******************** Fields ********************

	// ******************** Constructors ********************

	// ******************** Methods ********************
	private static void loadScrollDownTerrains()
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(
					"content/terrain/terrains.txt"));

			String line = br.readLine();

			int currentState = 0;
			int counter = 0;

			TerrainScrollDown terrain = null;

			while (line != null)
			{
				// Skip if it is a comment or an empty line
				if (line.length() == 0 || line.startsWith("#"))
				{
					line = br.readLine();
					continue;
				}
				String[] tokens = line.split(" ");
				if (tokens.length == 0)
				{
					line = br.readLine();
					continue;
				}

				// Simple state machine
				try
				{
					switch (currentState)
					{
					// State 0 :Read terrain name and add terrain into map.
					case 0:
					{
						if (tokens.length != 3)
						{
							throw new MyError(
									"Terrain read error: invalid terrain name");
						}
						// terrain = new TerrainScrollDown(tokens[0]);
						// scrollDownTerrains.put(tokens[0], terrain);
						// scrollDownTerrainNames.add(tokens[0]);
						// terrain.terrainSpeed = Integer.parseInt(tokens[2]);
						// terrain.numberOfTerrainPanels =
						// Integer.parseInt(tokens[1]);
						// currentState = 1;
					}
						break;

					// State 1: Read tile name.
					case 1:
					{
						if (tokens.length > 1)
						{
							throw new MyError(
									"Terrain read error: invalid terrain name");
						}
						terrain.terrainTileName = tokens[0];
						currentState = 2;

					}
						break;
					// State 2: Read number of assets.
					case 2:
					{
						if (tokens.length > 1)
						{
							throw new MyError(
									"Terrain read error: invalid number of tokens");
						}
						counter = Integer.parseInt(tokens[0]);
						currentState = 3;

					}
						break;

					// State 2: Read asset per line and when you came to the end
					// then switch back to state 0.
					case 3:
					{
						String assetName = tokens[0];
						int numOnPanel = Integer.parseInt(tokens[1]);
						float maxDeviation = Float.parseFloat(tokens[2]);

						// If the width, height are not specfied
						if (tokens.length == 3)
						{
							terrain.addAsset(assetName, numOnPanel,
									maxDeviation);
						}
						// If the width and height are specified
						else if (tokens.length == 5)
						{
							int width = Integer.parseInt(tokens[3]);
							int height = Integer.parseInt(tokens[4]);

							terrain.addAsset(assetName, numOnPanel,
									maxDeviation, width, height);

						} else
						{
							throw new MyError(
									"Terrain read error: invalid asset line.");
						}

						counter--;
						if (counter == 0)
						{
							currentState = 0;
						}
					}
						break;

					}

				} catch (MyError e)
				{
					System.err.println(e);
				}

				line = br.readLine();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Could not read from file assets.txt");
			Game.game.exitGame();
		}
	}
}
