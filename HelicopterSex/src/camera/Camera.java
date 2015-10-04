package camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import managers.GraphicsManager;
import engine.Game;
import engine.GameTime;
import utility.Vector2;

public class Camera
{
	// ******************** Fields ******************** 
	public Vector2 position = new Vector2(0, 0);
	public float rotation = 0;
	public Vector2 scale = new Vector2(1, 1);
	
	public int cameraWidth = Game.game.worldDimension.width;
	public int cameraHeight = Game.game.worldDimension.height;
	
	private LinkedList<CameraScript> scripts = new LinkedList<CameraScript>();
	
	// Rotation Fields.
	public Vector2 cornerVector;
	public Vector2 rotatedCornerVector;
	
	// Camera graphics representation fields.
	public boolean isDrawCamera = true; 
	public Color color = Color.black;
	private LinkedList<Shape> shapes = new LinkedList<Shape>();
	private int rectangleWidth = 25;
	private int rectangleHeight = 35;
	
	
	
	public Camera()
	{
		cornerVector = new Vector2(cameraWidth / 2, cameraHeight / 2);
		rotatedCornerVector = cornerVector.clone();
		initCameraGraphics();
	}
	
	public Camera(Vector2 position, float rotation, Vector2 scale)
	{
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		initCameraGraphics();

	}
	
	public Camera(Vector2 position, float rotation, Vector2 scale, int cameraWidth, int cameraHeight)
	{
		this(position, rotation, scale);
		this.cameraWidth = cameraWidth;
		this.cameraHeight = cameraHeight;
	}
	
	
	
	// ******************** Methods ******************** 
	public void update(GameTime gameTime)
	{
		for (CameraScript script : scripts)
		{
			script.update(gameTime);
		}
		rotatedCornerVector = cornerVector.clone();
	}
	
	public void draw(Graphics2D g2d)
	{
		GraphicsManager.saveGraphicsContext(g2d);
		g2d.translate(Game.game.worldDimension.getWidth() / 2, Game.game.worldDimension.getHeight() / 2);
		g2d.rotate(rotation);
		g2d.scale(scale.x, scale.y);
		
		for (CameraScript script : scripts)
		{
			script.draw(g2d);
		}
		
		if(isDrawCamera == true)
		{
			drawCameraRepresentation(g2d);
		}
		GraphicsManager.restoreGraphicsContext(g2d);

	}
	
	private void drawCameraRepresentation(Graphics2D g2d)
	{
		g2d.setPaint(color);
		
		for (Shape shape : shapes)
		{
			g2d.fill(shape);
		}
	}

	private void initCameraGraphics()
	{
		Rectangle2D camRect = new Rectangle2D.Float(- rectangleWidth / 2, - rectangleHeight / 2, rectangleWidth, rectangleHeight);
		Polygon camTriangle = new Polygon();
		camTriangle.addPoint(0, 0);
		camTriangle.addPoint(-rectangleWidth / 2, -rectangleHeight);
		camTriangle.addPoint(rectangleWidth / 2, - rectangleHeight);
		
		shapes.add(camRect);
		shapes.add(camTriangle);
	}

	public void addScriptComponent(CameraScript cameraScript)
	{
		scripts.add(cameraScript);
		cameraScript.onAttach(this);
	}

}
