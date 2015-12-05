package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ammus {
	
	World world;
	Body ammus;
	Vector2 oldV;
	protected boolean isAlive;
	
	Rajahdys rajahdys;
	int rDis;
	
	public Ammus(World world, float x, float y, Vector2 v){
		
		this.world = world;
		isAlive = true;
		rDis = -1;
		
		//v.setLength(7); //TODO Ei collisionia pelaaja ja ammuksen kanssa ja l�htee pelaajasta
		BodyDef adef = new BodyDef();
		adef.type = BodyType.DynamicBody;
		adef.position.set(new Vector2(x,y));
		
		ammus = world.createBody(adef);
		ammus.setBullet(true);
		
		PolygonShape laatikkoShape = new PolygonShape();
		laatikkoShape.setAsBox(1f, 0.5f);
		
		FixtureDef ammusFix = new FixtureDef();
		ammusFix.shape = laatikkoShape;
		ammusFix.filter.groupIndex = -2; // Pit�� olla eri muille pelaajille !!
		
		ammus.setGravityScale(0);				
		ammus.createFixture(ammusFix);
		
		v.setLength(120);
		ammus.setTransform(ammus.getPosition(), v.angleRad());
		ammus.setLinearVelocity(v);
		oldV = new Vector2(ammus.getLinearVelocity());
		
	}
	
	public void update(){
		
		
//		if (ammus.getUserData() == (Boolean) true || ammus.getPosition().x < 0 || ammus.getPosition().x > 1280/4 
//		|| ammus.getPosition().y < 0 || ammus.getPosition().y > 720/4){
		
//		if (!ammus.getLinearVelocity().equals(oldV)){
			
		if ((ammus.getLinearVelocity().x < oldV.x - 1 || ammus.getLinearVelocity().x > oldV.x + 1 ||
				ammus.getLinearVelocity().y > oldV.y + 1 || ammus.getLinearVelocity().y < oldV.y - 1) && rDis < 0){
			float x = ammus.getPosition().x;
			float y = ammus.getPosition().y;
			world.destroyBody(ammus);
			rajahdys = new Rajahdys(x,y,world);
			rDis = 50;
		}
		if (rDis == 40){
			rajahdys.setCategoryBits();
		}
		if (rDis >= 0){
			if (rDis == 0){
				rajahdys.dispose();
				isAlive = false;
			} else{
			rDis--;
			}
		}

		
	}
	

}
