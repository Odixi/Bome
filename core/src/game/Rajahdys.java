package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Rajahdys {
	
	World world;
	Body[] partikkelit;
	
	public Rajahdys(float x, float y, World world){
		
		this.world = world;
		partikkelit = new Body[50];
		
		BodyDef pdef = new BodyDef();
		pdef.type = BodyType.DynamicBody;
		pdef.position.set(x,y);
		pdef.fixedRotation = true;
		pdef.bullet = true;
		pdef.linearDamping = 2f;
		pdef.gravityScale = 0.5f;
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.5f);
		
		FixtureDef fix;
		
		for (int i = 0; i < 50; i++){
			
			fix  = new FixtureDef();
			
			partikkelit[i] = world.createBody(pdef);
			
			fix.shape = shape;
			fix.density = 20;
			fix.friction = 0;
			fix.restitution = 0.99f;
			fix.filter.categoryBits = 0x0001;
			fix.filter.groupIndex = -1;
			
			partikkelit[i].createFixture(fix);
			
			partikkelit[i].setLinearVelocity((float)(-120 + Math.random()*240),(float)(-120 + Math.random()*240));
//			p.setLinearVelocity(120*(float)Math.sin(i*360),120*(float)Math.cos(i*360));
			
		} // for
	}
	public void dispose(){
		for (int i = 0; i < partikkelit.length; i++){
			world.destroyBody(partikkelit[i]);
		}
	}
	public void setCategoryBits(){
		Filter fil =partikkelit[0].getFixtureList().get(0).getFilterData();
		for (int i = 0; i < partikkelit.length; i++){
			fil.categoryBits = 0x0010;
			partikkelit[i].getFixtureList().get(0).setFilterData(fil);
		}
	}

}
