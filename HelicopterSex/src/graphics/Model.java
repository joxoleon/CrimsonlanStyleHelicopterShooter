package graphics;

import java.awt.Graphics2D;
import java.util.LinkedList;


public class Model
implements IRenderable
{
	// Fields.
	private String name;
	private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
	
	
	//Constructors
	public 
	Model(String name)
	{
		this.name = name;
	}
	
	// Methods.
	public void 
	render(Graphics2D g2d)
	{
		for (Sprite sprite : sprites)
		{
			sprite.render(g2d, true);
		}

	}
	
	public Sprite 
	getSprite(String name)
	{
		for (Sprite sprite : sprites)
		{
			if(sprite.getName().equals(name))
				return sprite;
		}
		
		System.err.println("Sprite: " + name + " in model: " + this.name + " not found!");
		return null;
	}
	
	
	public void
	addSprite(Sprite sprite)
	{
		sprites.addLast(sprite);
	}
	
	public String 
	getName()
	{
		return name;
	}

	
	public String
	toString()
	{
		return new String("Model name: " + name + " number of Sprites: " + sprites.size());
	}
	
	public int
	numOfSprites()
	{
		return sprites.size();
	}

	@Override
	public Model getModel()
	{
		return this;
	}

	@Override
	public String getType()
	{
		return "model";
	}

	public Model clone()
	{
		Model clone = new Model(this.name);
		for (Sprite sprite : sprites)
		{
			sprite.clone();
		}
		
		return clone;
	}
}
