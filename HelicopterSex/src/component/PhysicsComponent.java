package component;

import utility.Vector2;
import engine.GameTime;

public class PhysicsComponent 
extends ActorComponent
{
	public enum Direction {FORWARD, BACKWARD, LEFT, RIGHT};


	// ******************** Fields ******************** 
	
	// Translation fields.
	public Vector2 velocity = new Vector2();
	public Vector2 acceleration = new Vector2();
	public float translationDragFactor = 0.95f;
	public float translationThresholdSquared = 2;
	public float mass = 5; // Configurable
	
	// Rotation Fields.
	public float angularVelocity = 0;
	public float angularAcceleration = 0;
	public float rotationDragFactor = 0.93f;
	public float angularSqrtThreshold = 0.2f;
	public float momentOfInertia = 3f; // Configurable
	
	// Movement Fields.
	public float accelerationIntensity = 8000; // Configurable
	public float angularAccelerationIntensity = 40; // Configurable
	
	
	// ******************** Constructors ******************** 

		
		
		
		
		
	// ******************** Methods ******************** 
	@Override
	public String getName()
	{
		return "physicsComponent";
	}
	
	@Override
	public void onAttach()
	{		
	}
	
	@Override
	public void update(GameTime gameTime)
	{
		// *** Translation!!!
		// Handle drag, and velocity below the threshold.
		velocity.mul(translationDragFactor);
		if(velocity.magnitudeSquared() < translationThresholdSquared)
		{
			velocity.x = 0;
			velocity.y = 0;
		}
		
		// Calculate velocity.
		velocity.add(Vector2.mul(acceleration, gameTime.dt_s()));
		// Update position.
		parent.position.add(Vector2.mul(velocity, gameTime.dt_s()));
		
		// Reset acceleration.
		acceleration.x = 0;
		acceleration.y = 0;
		
		
		// *** Rotation!!!
		// Handle drag and angular velocity below the threshold.
		angularVelocity *= rotationDragFactor;
		if(Math.abs(angularVelocity) < angularSqrtThreshold)
		{
			angularVelocity = 0;
		}
		
		// Calculate velocity.
		angularVelocity += angularAcceleration * gameTime.dt_s();
		// Update rotation.
		parent.rotation += angularVelocity * gameTime.dt_s();
		handleRotation();
		
		// Reset angular acceleration
		angularAcceleration = 0;

	}

	@Override
	public void onDestroy()
	{		
	}

	public Vector2 getForwardVector()
	{
		Vector2 forwardVector = new Vector2(0, -1);
		if(parent.rotation != 0)
		{
			forwardVector.rotate(parent.rotation);
		}
		
		return forwardVector;
	}
	
	public void accelerate(Direction direction)
	{
		
		Vector2 forward = getForwardVector();
		forward.mul(accelerationIntensity / mass);
		


		if(direction == Direction.LEFT)
		{
			forward.rotate((float)(-Math.PI / 2));
		}
		else if(direction == Direction.RIGHT)
		{

			forward.rotate((float)(Math.PI / 2));
		}
		else if(direction == Direction.BACKWARD)
		{
			forward.mul(-1);
		}
		
		acceleration.add(forward);
	}
	
	public void rotate(Direction direction)
	{
		if(direction == Direction.LEFT)
		{
			angularAcceleration = angularAccelerationIntensity / momentOfInertia; 
		}
		else
		{
			angularAcceleration = - angularAccelerationIntensity / momentOfInertia;
		}
	}

	private void handleRotation()
	{
		if(parent.rotation < 0)
		{
			parent.rotation += 2 * Math.PI;
		}
		else if(parent.rotation > 2 * Math.PI)
		{
			parent.rotation -= 2 *  Math.PI;
		}
	}
	
}
