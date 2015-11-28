package game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Pelaaja implements InputProcessor{

	Body pelaaja;
	World world;
	Vector2 hv;
	
	Ammus ammus;
	boolean jump;
	boolean up,left,right;
	
	public Pelaaja(World w, Body p) {
		super();
		pelaaja = p;
		world = w;
		hv = new Vector2(0, 400);
		jump = true;
		up = false;
		left = false;
		right = false;
		
	}
	
	public void update(){
		if (pelaaja.getUserData() == (Boolean)true){
			jump = true;
		}
		if (up && jump){
			pelaaja.applyLinearImpulse(hv,pelaaja.getPosition(), true);
			jump = false;
		}
		if (left && pelaaja.getLinearVelocity().x > -40){
			pelaaja.applyLinearImpulse(-30, 0, pelaaja.getPosition().x, pelaaja.getPosition().y+0.5f, true);
		}
		if (right && pelaaja.getLinearVelocity().x < 40){
			pelaaja.applyLinearImpulse(30, 0, pelaaja.getPosition().x, pelaaja.getPosition().y+0.5f, true);
		}
		if (ammus != null && ammus.isAlive){
			ammus.update();
		}
		
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		
		case Keys.W:
			up = true;
			break;

		case Keys.A:
			left = true;
			break;
		case Keys.D:
			right = true;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		
		case Keys.W:
			up = false;
			break;
		case Keys.A:
			left = false;
			break;
		case Keys.D:
			right = false;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (ammus == null || !ammus.isAlive){
			Vector2 vect = new Vector2(screenX/6f,(720-screenY)/6f);
			vect.sub(pelaaja.getPosition());

			ammus = new Ammus(world, pelaaja.getPosition().x, pelaaja.getPosition().y, vect);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
