package utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import content.MyError;
import engine.Game;

public class MyFileReader
{
	// ******************** Fields ********************
	String path;
	BufferedReader br;
	String line;
	public boolean hasMore = false;
	
	
	// ******************** Constructors ******************** 
	public MyFileReader(String path)
	{
		this.path = path;
		try
		{
			br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			if(line != null)
			{
				hasMore = true;
			}
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Game.game.exitGame();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Game.game.exitGame();
		}
	}
	
	public String[] getNextLineTokens()
	{
		try
		{
			String[] tokens = line.split(" ");
			
			line = br.readLine();
			while(line.length() == 0 || line.startsWith("#"))
			{
				line = br.readLine();
			}
			if(line == null)
			{
				hasMore = false;
			}
			
			return tokens;

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Game.game.exitGame();
		}
		
		return null;
	}
	
	public String[] getNextLineTokens(int numberOfTokens)
	{
		try
		{
			String[] tokens = line.split(" ");
			if(tokens.length != numberOfTokens)
			{
				throw new MyError("Invalid input at: " + path);
			}
			
			line = br.readLine();
			if(line == null)
			{
				hasMore = false;
				return tokens;
			}
			while(line.length() == 0 || line.startsWith("#"))
			{
				line = br.readLine();
			}

			
			return tokens;

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Game.game.exitGame();
		} catch (MyError e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e);
			Game.game.exitGame();
		}
		
		return null;
	}
	
	
	// ******************** Methods ******************** 
}
