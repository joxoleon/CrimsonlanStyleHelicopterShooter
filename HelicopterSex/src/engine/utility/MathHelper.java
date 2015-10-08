package engine.utility;

public class MathHelper
{
	// Fields.
	public static final float PI = 3.14159265358979323f;
	public static final float PIOverTwo = PI / 2;
	public static final float PIOverFour = PI / 4;
	public static final float TwoPI = 2 * PI;
	public static final float SQRT2 = (float)Math.sqrt(2);
	
	private static final float radToDeg = 180.0f / PI;
	private static final float degToRad = PI / 180.0f;
	
	
	
	
	
	// Methods.
	public static float 
	toDegrees(float radians)
	{
		return radians * radToDeg;
	}
	
	public static float 
	toRadians(float degrees)
	{
		return degrees * degToRad;
	}
	
	public static double 
	clamp(double value, double min, double max)
	{
		if(value > max)
			return max;
		else if (value < min)
			return min;
		else
			return value;
		
	}
	
	public static float 
	clamp(float value, float min, float max)
	{
		if(value > max)
			return max;
		else if (value < min)
			return min;
		else
			return value;
		
	}
	
	public static int 
	clamp(int value, int min, int max)
	{
		if(value > max)
			return max;
		else if (value < min)
			return min;
		else
			return value;
		
	}
	
	public static double
	clampAngle(double value)
	{
		if (value > MathHelper.PI)
			return value - MathHelper.TwoPI;
		else if (value < -MathHelper.PI)
			return value + MathHelper.TwoPI;
		else
			return value;
	}
	
	public static float
	clampAngle(float value)
	{
		if (value > MathHelper.PI)
			return value - MathHelper.TwoPI;
		else if (value < -MathHelper.PI)
			return value + MathHelper.TwoPI;
		else
			return value;
	}
	
	// Hidden constructor.
	private 
	MathHelper()
	{
	}

}
