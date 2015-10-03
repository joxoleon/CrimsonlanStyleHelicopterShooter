package utility;

public class Vector2
{
	// ******************** Fields ******************** 
	// Static fields.
	public static final Vector2 zero = new Vector2();
	public static final Vector2 up = new Vector2(0, -1);
	public static final Vector2 right = new Vector2(1, 0);
	
	
	// Vector fields.
	public float x;
	public float y;
	
	
	
	
	// ******************** Constructors ******************** 
	public Vector2()
	{
		
	}
	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	
	
	// ******************** Methods ******************** 
	public Vector2 add(Vector2 vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	public Vector2 sub(Vector2 vector)
	{
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	public Vector2 mul(Vector2 vector)
	{
		this.x *= vector.x;
		this.y *= vector.y;
		return this;
	}
	public Vector2 div(Vector2 vector)
	{
		this.x /= vector.x;
		this.y /= vector.y;
		return this;
	}
	public Vector2 mul(float value)
	{
		this.x *= value;
		this.y *= value;
		return this;
	}
	public Vector2 div(float value)
	{
		this.x /= value;
		this.y /= value;
		return this;
	}
	public Vector2 normalize()
	{
		if(x != 0 || y != 0)
		{
			float magnitude = this.magnitude();
			return this.div(magnitude);
		}
		else 
		{
			System.err.println("Vector2 Error: Can not normalize zero vector - division by zero");
			System.exit(1);
			return null;
		}
	}
	
	public static Vector2 mul(Vector2 v1, Vector2 v2)
	{
		return new Vector2(v1.x * v2.x, v1.y * v2.y);
	}
	public static Vector2 add(Vector2 v1, Vector2 v2)
	{
		return new Vector2(v1.x + v2.x, v1.y + v2.y);
	}
	public static Vector2 sub(Vector2 v1, Vector2 v2)
	{
		return new Vector2(v1.x - v2.x, v1.y - v2.y);
	}
	public static Vector2 div(Vector2 v1, Vector2 v2)
	{
		return new Vector2(v1.x / v2.x, v1.y / v2.y);
	}
	
	
	public static Vector2 add(Vector2 v1, float value)
	{
		return new Vector2(v1.x + value, v1.y + value);
	}
	public static Vector2 sub(Vector2 v1, float value)
	{
		return new Vector2(v1.x - value, v1.y - value);
	}
	public static Vector2 mul(Vector2 v1, float value)
	{
		return new Vector2(v1.x * value, v1.y * value);
	}
	public static Vector2 div(Vector2 v1, float value)
	{
		return new Vector2(v1.x / value, v1.y / value);
	}
	
	
	public static Vector2 normalize(Vector2 vector)
	{
		float magnitude = vector.magnitude();
		return new Vector2(vector.x / magnitude, vector.y / magnitude);
	}
	
	public Vector2 clone()
	{
		return new Vector2(this.x, this.y);
	}
	
    public void rotate(float angle)
    {
    	
    	float sin = (float)Math.sin(angle);
    	float cos = (float)Math.cos(angle);
    	
    	float tempx = x;
    	x = x * cos - y * sin;
    	y = tempx * sin + y * cos;

    }
    
    public static Vector2 rotate(Vector2 vector, float angle)
    {
    	
    	float sin = (float)Math.sin(angle);
    	float cos = (float)Math.cos(angle);
    	
    	float tempx = vector.x;
    	vector.x = vector.x * cos - vector.y * sin;
    	vector.y = tempx * sin + vector.y * cos;
    	
    	return vector;
    }
	
	public float magnitudeSquared()
	{
		return x * x + y * y;
	}
	public float magnitude()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
}
