package score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.ListIterator;

public class Score
{
	// Class members.
	public String name;
	public long points;
	public long timeOfPlaying;
	
	private static final long totalNameChars = 16;
	private static final long totalPointsChars = 7;

	
	// Member methods.
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(name);
		sb.append("                     ", 0, (int)(totalNameChars - name.length()));
		sb.append(points);
		sb.append("                     ", 0, (int)(totalPointsChars - ("" + points).length()));
		sb.append(timeOfPlaying);
		
		return sb.toString();
	}
	
	
	// Static members.
	public static LinkedList<Score> scoresByPoints = new LinkedList<Score>();
	public static LinkedList<Score> scoresByTime = new LinkedList<Score>();

		
	// Static methods.
	public static void insertScore(String name, long points, long time)
	{
		Score newScore = new Score();
		newScore.points = points;
		newScore.timeOfPlaying = time;
		newScore.name = name;
		
		
		// Inserting by points.
		ListIterator<Score> pointsIterator = scoresByPoints.listIterator();
		
		if(scoresByPoints.isEmpty() == true)
		{
			scoresByPoints.add(newScore);
		}
		else
		{
			boolean isInserted = false;
			while (pointsIterator.hasNext() == true)
			{
				Score score = pointsIterator.next();
				
				if (newScore.points > score.points)
				{
					pointsIterator.previous();
					pointsIterator.add(newScore);
					isInserted = true;
					break;
				}
			}
			
			if(isInserted == false)
			{
				scoresByPoints.addLast(newScore);
			}
		}
		
		
		
		// Inserting by time.
		ListIterator<Score> timeIterator = scoresByTime.listIterator();
		
		
		if(scoresByTime.isEmpty() == true)
		{
			scoresByTime.add(newScore);
		}
		else
		{
			boolean isInserted = false;
			while (timeIterator.hasNext() == true)
			{
				Score score = timeIterator.next();
				
				if (newScore.timeOfPlaying > score.timeOfPlaying)
				{
					timeIterator.previous();
					timeIterator.add(newScore);
					isInserted = true;
					break;
				}
			}
			
			if(isInserted == false)
			{
				scoresByTime.addLast(newScore);
			}
		}
		
		
		while(scoresByPoints.size() > 10)
		{
			scoresByPoints.removeLast();
			scoresByTime.removeLast();
		}

	}
	
	

	
	public static LinkedList<String> printResultsToString(LinkedList<Score> scores)
	{
		LinkedList<String> results = new LinkedList<String>();
		
		int i = 1;
		for (Score score : scores)
		{
			results.addLast(i + ".   " + score.toString());
			++i;
		}
		
		return results;
	}
	
	
	// Print results into file
	public static void printResultsToFile(LinkedList<Score> scores)
	{
		try
		{
		File file = new File("content/results.txt");
		
		if(file.exists() == false)
		{
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		LinkedList<String> results = GetResultListForFile(scores);
		for (String string : results)
		{
			bw.write(string + "\n");
		}		

		bw.close();
		
		}
		catch (Exception e)
		{
			
		}
		
	}
	private static LinkedList<String> GetResultListForFile(LinkedList<Score> scores)
	{
		LinkedList<String> results = new LinkedList<String>();
		
		int counter = 0;
		for (Score score : scores)
		{
			String newScore = new String(score.name + " " + score.points + " " +score.timeOfPlaying);
			results.add(newScore);
			counter++;
			if(counter >= 10 )
				break;
		}
		
		return results;
	}
	
	// Get results from file
	public static void loadHighScoresFromFile()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("content/results.txt"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				String[] tokens = line.split(" ");
				String name = tokens[0];
				long points = Long.parseLong(tokens[1]);
				long timeOfPlaying = Long.parseLong(tokens[2]);
				
				insertScore(name, points, timeOfPlaying);
				
				line = br.readLine();
			}
			
			br.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: Could not load high scores from file!!!");
		}
	}
	
	// Print high scores.
	public static LinkedList<String> printNamesToString(LinkedList<Score> scores)
	{
		LinkedList<String> results = new LinkedList<String>();
		
		int i = 1;
		for (Score score : scores)
		{
			results.addLast(i + ".   " + score.name);
			++i;
		}
		
		
		return results;
	}
	
	public static LinkedList<String> printScoresToString(LinkedList<Score> scores)
	{
		LinkedList<String> results = new LinkedList<String>();
		
		for (Score score : scores)
		{
			results.addLast("" + score.points);
		}
		
		
		return results;
	}
	
	public static LinkedList<String> printTimesToString(LinkedList<Score> scores)
	{
		LinkedList<String> results = new LinkedList<String>();
		
		for (Score score : scores)
		{
			results.addLast("" + score.timeOfPlaying);
		}
		
		
		return results;
	}
	
	
}
