package managers;

import java.awt.Color;
import java.util.LinkedList;

import shooting.ShotManager;
import shooting.SimpleShot;
import engine.Actor;
import engine.GameTime;
import engine.component.SphereCollider;
import engine.utility.DebugRenderer;
import engine.utility.DebugVector2;
import engine.utility.Vector2;

public class CollisionManager
{
	
	public static void update(GameTime gameTime)
	{
//		// test shit.
//		Vector2 position = new Vector2(100, 100);
//		Vector2 vector = new Vector2 (200, 200);
//		DebugRenderer.addDebugVector(position, vector);
		
		handlePlayerToEnemyCollisions();
		handleActorToShotCollisions(EnemyManager.enemies, ShotManager.playerShots);
	}
	
	
	
	private static void handlePlayerToEnemyCollisions()
	{
		SphereCollider playerCollider = PlayerActorManager.playerActor.collider;
		
		for (Actor enemy : EnemyManager.enemies)
		{
			playerCollider.checkForCollision(enemy.collider);
		}
	}
	
	
	
	
	private static void handleActorToShotCollisions(LinkedList<Actor> actors, LinkedList<SimpleShot> shots)
	{
		for (SimpleShot shot : shots)
		{
			Vector2 shotMoveVector = Vector2.sub(shot.position, shot.previousPosition);
			float shotMoveMangitudeSquared = shotMoveVector.magnitudeSquared();
			Vector2 shootMoveOrt = Vector2.normalize(shotMoveVector);
			
			for (Actor actor : actors)
			{
				float radiusSquared = actor.collider.radius * actor.collider.radius;
				
				Vector2 prevPositionToCircle = Vector2.sub(actor.position, shot.previousPosition);				

				Vector2 closest = Vector2.mul(shootMoveOrt, Vector2.dot(prevPositionToCircle, shootMoveOrt));
				closest.add(shot.previousPosition);
								
				Vector2 closestToActor = Vector2.sub(actor.position, closest);
				
				
				if(closestToActor.magnitudeSquared() < radiusSquared)
				{
					if(
							Vector2.sub(closest, shot.previousPosition).magnitudeSquared() < shotMoveMangitudeSquared 	||
							Vector2.sub(actor.position, shot.position).magnitudeSquared() < radiusSquared				||
							Vector2.sub(actor.position, shot.previousPosition).magnitudeSquared() < radiusSquared
							)
					{
						System.out.println("collision!!!!");
					}
				}

			}
		}
	}
	
	
	// **** Leave this comment for debug purposes ****** debug mode for this method.
//	private static void handleActorToShotCollisions(LinkedList<Actor> actors, LinkedList<SimpleShot> shots)
//	{
//		for (SimpleShot shot : shots)
//		{
//			// Draw shot move vector
//			Vector2 shotMoveVector = Vector2.sub(shot.position, shot.previousPosition);
//			DebugRenderer.addDebugVector(shot.previousPosition, shot.position, Color.red);
//			float shotMoveMangitudeSquared = shotMoveVector.magnitudeSquared();
//			
//			Vector2 shootMoveOrt = Vector2.normalize(shotMoveVector);
//			
//			for (Actor actor : actors)
//			{
//				float radiusSquared = actor.collider.radius * actor.collider.radius;
//				
//				Vector2 prevPositionToCircle = Vector2.sub(actor.position, shot.previousPosition);
//				DebugRenderer.addDebugVector(shot.previousPosition, actor.position, Color.green);
//				
//
//				Vector2 closest = Vector2.mul(shootMoveOrt, Vector2.dot(prevPositionToCircle, shootMoveOrt));
//				closest.add(shot.previousPosition);
//				
//				DebugRenderer.addDebugVector(shot.previousPosition, closest, Color.cyan);
//				
//				Vector2 distance = Vector2.sub(actor.position, closest);
//				DebugRenderer.addDebugVector(closest, actor.position, Color.magenta);
//				
//				
//				if(distance.magnitudeSquared() < radiusSquared)
//				{
////					Vector2 prevPositionToClosest = Vector2.sub(closest, shot.previousPosition);
////					Vector2 positionToClosest = Vector2.sub(closest, shot.position);
////					
////					if(Vector2.dot(shotMoveVector, prevPositionToClosest) * Vector2.dot(shotMoveVector, positionToClosest) < 0)
////					{
////						System.out.println("collision!!!!!!!!");
////					}
////					System.out.println("checking if the magnitude is acceptable");
////					System.out.println("shotMoveMagnitudeSquared = " + shotMoveMangitudeSquared);
////					System.out.println("closes.magnitudeSquared = " + closest.magnitudeSquared());
//					if(
//							Vector2.sub(closest, shot.previousPosition).magnitudeSquared() < shotMoveMangitudeSquared 	||
//							Vector2.sub(actor.position, shot.position).magnitudeSquared() < radiusSquared				||
//							Vector2.sub(actor.position, shot.previousPosition).magnitudeSquared() < radiusSquared
//							)
//					{
//						System.out.println("collision!!!!");
//					}
//				}
//
//			}
//		}
//	}

	
}
