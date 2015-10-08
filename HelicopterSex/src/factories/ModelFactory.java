package factories;

import engine.graphics.Model;
import engine.graphics.Sprite;
import engine.utility.MyFileReader;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import content.Content;

public class ModelFactory
{
	//Fields
	private HashMap<String, Model> models = new HashMap<String, Model>();
	private static ModelFactory instance;
	
	
	//Methods
	private 
	ModelFactory()
	{
	}
	
	public static ModelFactory 
	getInstance()
	{
		if(instance == null)
			instance = new ModelFactory();
		return instance;
	}
	
	// A method that returns a clone of a model.
	public Model 
	getFlyweightModel(String name)
	{
		return models.get(name);
	}

	public void
	addModel(String name, Model model)
	{
		if (name != null && name != "" && model != null)
		{
			models.put(name, model);
		}
	}
	
	public void
	loadModels(String resourcePath)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(resourcePath));
			
			String line = reader.readLine();
			
			// 1 - read head line (Model name and Sprite number)
			// 2 - read Sprite line (Sprite name and transform)
			int state = 1;
			
			String modelName = null;
			int spriteNum = 0;
			int spriteCnt = 0;
			Model model = null;
			
			while(line != null)
			{
				if (line.equals("") || line.startsWith("#"))
				{
					line = reader.readLine();
					continue;
				}
				
				String[] tokens = line.split(" ");
				
				switch (state)
				{
					case 1:
					{
						if (tokens.length == 2)
						{
							modelName = tokens[0];
							spriteNum = Integer.parseInt(tokens[1]);
							spriteCnt = 0;
							
							model = new Model(modelName);
							
							state = 2;
						}
						else
						{
							System.err.println("Unexpected input in models resource file! File path: " + resourcePath);
							return;
						}
					}
					break;
					
					case 2:
					{
						if (tokens.length == 4 || tokens.length == 9 || tokens.length == 10)
						{

							BufferedImage img = Content.images.get(tokens[0]);
							if(img == null)
							{
								System.err.println("PUSI KURAC! " + tokens[0]);
								System.exit(-1);
							}

							Sprite sprite = new Sprite(
								tokens[1],
								Content.images.get(tokens[0]),
								Integer.parseInt(tokens[2]),
								Integer.parseInt(tokens[3]));
							
							model.addSprite(sprite);
							spriteCnt++;
							
							if(tokens.length >= 9)
							{
								sprite.setPosition(Float.parseFloat(tokens[4]), Float.parseFloat(tokens[5]));
								sprite.setRotation(Float.parseFloat(tokens[6]));
								sprite.setScale(Float.parseFloat(tokens[7]), Float.parseFloat(tokens[8]));
							
							
								if(tokens.length == 10)
								{
									if(tokens[9].startsWith("tr"))
									{
										sprite.isAutonomouslyRotating = true;
									}
								}
							}
							
							if (spriteCnt == spriteNum)
							{
								addModel(modelName, model);
								state = 1;
							}
						}
						else
						{
							System.err.println("Unexpected input in models resource file! File path: " + resourcePath);
							return;
						}
					}
					break;
				}
				
				line = reader.readLine();			
			}
			
			reader.close();
		}
		catch (NumberFormatException e)
		{
			System.err.println("Could not parse an integer from images resource file! File path: " + resourcePath);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Could not load image resource file! File path: " + resourcePath);
		}
		catch(IOException e)
		{
			System.err.println("Error reading file line! File path: " + resourcePath);
		}
	}
	
}
