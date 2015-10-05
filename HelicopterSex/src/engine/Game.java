package engine;

import factories.GunFactory;
import factories.ModelFactory;
import factories.ShotFactory;
import gamestates.GameOptionsState;
import gamestates.GameOverState;
import gamestates.GameStateMachine;
import gamestates.HelpGameState;
import gamestates.HighScoresState;
import gamestates.MainMenuState;
import gamestates.PlaySingleGameState;
import gamestates.TestState;
import input.Input;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.embed.swing.JFXPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import managers.AudioManager;
import score.Score;
import terrain.TerrainManager;
import content.Content;
import content.MyError;


public class Game
extends JPanel
{
	// Used only for JavaFX initialization.
	final JFXPanel fxPanel = new JFXPanel();
	
	
	// Fields
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	boolean isFullScreen = true;
	
	// (desired / world) * panel = true dimm 
	public Dimension panelDimension = new Dimension(1, 1);

	public Dimension worldDimension = new Dimension(1920, 1080);
	public Dimension desiredDimension = new Dimension(1366, 720);
	
	// Game time.
	private GameTime gameTime;
	
	
	public static Game game;
	
	
	// Game states.
	public GameStateMachine gameStateMachine;
	
	public MainMenuState mainMenuState;
	public PlaySingleGameState playSingleGameState;
	public GameOptionsState gameOptionsState;
	public HelpGameState helpGameState;
	public GameOverState gameOverState;
	public HighScoresState highScoresState;
	public TestState testState;
	
	
	// Game loop.
	private GameLoopThread gameLoopThread;
	
	// Difficulty fields - level goes from 3 to 7
	public int minDifficultyLevel = 0;
	public int maxDifficultyLevel = 7;
	public int difficultyLevel = 5;
	
	// Draw Image.
	BufferedImage drawImage, paintImage = new BufferedImage(worldDimension.width, worldDimension.height, BufferedImage.TYPE_INT_ARGB);
;
	
	
	// Constructors.
	public Game()
	{
		game = this;
		
		// Initialize.
		initializeWindow();
		initializeContentFactoriesAndScripts();
		initializeStates();

		gameLoopThread = new GameLoopThread(this, 60);
		gameTime = gameLoopThread.gameTime;
		gameLoopThread.start();
		
		showWindow();
	}
	
	public void update(GameTime gameTime)
	{
		Input.SwitchStates();
		
		gameStateMachine.update(gameTime);
		
	}
	
	public void draw()
	{
		drawImage = new BufferedImage(worldDimension.width, worldDimension.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) drawImage.getGraphics();
		gameStateMachine.draw(g2d);
		paintImage = drawImage;

	}
	
	@Override
	public void paint(Graphics myg)
	{
		
		
		Graphics2D myg2d = (Graphics2D)myg;
		
		super.paint(myg2d);
		myg2d.drawImage(
				paintImage,
				0, 0, panelDimension.width, panelDimension.height,
				0, 0, paintImage.getWidth(), paintImage.getHeight(),
				null);
	}
	
	
	// Helpers.
	private void initializeWindow()
	{
		panelDimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		initializeFrame();
		initializeKeyboardInput();
	}
	
	private void initializeFrame()
	{
		frame = new JFrame("Helicopter Sex");
		
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				exitGame();
			}
		});
		
		frame.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				panelDimension = getSize();
				// Resize Calculate scale factor.

			}
		});
		
		frame.getContentPane().add(this);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		
		if (isFullScreen == true)
		{
			desiredDimension = Toolkit.getDefaultToolkit().getScreenSize();
			panelDimension = desiredDimension;
		}
		frame.setSize(desiredDimension.width, desiredDimension.height);
		
		// Resize Calculate scale factor.
		
		frame.addKeyListener(Input.Instance());
		
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
	}
	
	private void showWindow()
	{
		frame.setVisible(true);
	}
	
	private void initializeKeyboardInput()
	{
		this.addKeyListener(Input.Instance());

		//Input.Instance().setFocusable(true);
		Input.Instance().requestFocusInWindow();
	}

	
	public void initializeStates()
	{
		gameStateMachine = new GameStateMachine();
		
		mainMenuState = new MainMenuState();
		playSingleGameState = new PlaySingleGameState();
		gameOptionsState = new GameOptionsState();
		helpGameState = new HelpGameState();
		gameOverState = new GameOverState();
		highScoresState = new HighScoresState();
		testState = new TestState();
		
		gameStateMachine.changeState(playSingleGameState);
	}
	
	public void initializeContentFactoriesAndScripts()
	{
		try
		{
			
			Content.initializeContent();
			AudioManager.loadMedia();
			AudioManager.setMasterVolume(1.0);
			ModelFactory.getInstance().loadModels("content/models.txt");
			ShotFactory.loadShotSprites("content/shots.txt");
			GunFactory.loadGuns("content/guns.txt");
			GunFactory.loadGunSlots("content/gunSlotCombinations.txt");
			TerrainManager.initialize();

			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Game.game.exitGame();
		} catch (MyError e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void exitGame()
	{
		// Save scores into file
		Score.printResultsToFile(Score.scoresByPoints);
		
		
		// kasnije run = false;
		gameLoopThread.stopGameLoop();
		try
		{
			gameLoopThread.interrupt();
			gameLoopThread.join();
		} catch (InterruptedException e)
		{
		}
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		new Game();
	}
	
	public void setGameTimeTimeScale(float timeScale)
	{
		gameTime.setTimeScale(timeScale);
	}
}



