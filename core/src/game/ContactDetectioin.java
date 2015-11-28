package game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactDetectioin implements ContactListener{
	
	Fixture fxA;
	Fixture fxB;

	@Override
	public void beginContact(Contact contact) {
		fxA = contact.getFixtureA();
		fxB = contact.getFixtureB();
		fxA.getBody().setUserData(true);
		fxB.getBody().setUserData(true);

	}

	@Override
	public void endContact(Contact contact) {
		fxA = contact.getFixtureA();
		fxB = contact.getFixtureB();
		fxA.getBody().setUserData(false);
		fxB.getBody().setUserData(false);
		
	}

//	public boolean isBeingContacted(Body body){
//		if (fxA.getBody() == body || fxB.getBody() == body){
//			System.out.println("juu");
//			return true;
//		}
//		return false;
//	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
