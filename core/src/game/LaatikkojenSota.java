package game;


import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LaatikkojenSota extends ApplicationAdapter {

	OrthographicCamera camera;
	float width, height;
	FPSLogger logger;
	
	World world;
	Box2DDebugRenderer renderer;
	
	Body ympyra;
	Body laatikko;
	Pelaaja pelaaja;
	ContactDetectioin contactDetection;
	
	RayHandler rayHandler;
	
	
	@Override
	public void create () {
		
		// TODO 
		// Korjaa resoluution handlaus
		// TODO
		// Tee uusi luokka pelille / mainmenulle jne.
		width = Gdx.graphics.getWidth() / 6;
		height = Gdx.graphics.getHeight() / 6;
		
		camera = new OrthographicCamera(width, height);
		camera.position.set(width * 0.5f, height * 0.5f, 0);
		camera.update();
		
		world = new World(new Vector2(0, -9.8f*10), false);
		contactDetection = new ContactDetectioin();
		world.setContactListener(contactDetection);
		
		renderer = new Box2DDebugRenderer();
		
		logger = new FPSLogger();
		
		// Valoilmiö
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.setBlurNum(3);
		rayHandler.setAmbientLight(0.2f);
		//new DirectionalLight(rayHandler, 1000, Color.YELLOW, -60);
        new PointLight(rayHandler, 800, Color.DARK_GRAY , width/1.2f, width/2f+100, height/2f);
        new PointLight(rayHandler, 800, Color.BLUE , width/1.2f, width/2f-100, height/2f);
		
		//YMPYRÄN MUODOSTUS
		
			BodyDef Pdef = new BodyDef();
			Pdef.type = BodyType.DynamicBody;
			Pdef.position.set(width/2, height/2);
		
			ympyra = world.createBody(Pdef);
	
			CircleShape ympyraMuoto = new CircleShape();
		
			ympyraMuoto.setRadius(3f);
		
			// Lisää objektien ominaisuuksien määrittelyä
			FixtureDef ympyraFixt = new FixtureDef();
			ympyraFixt.shape = ympyraMuoto;
			ympyraFixt.density = 0.4f;
			ympyraFixt.friction = 0.4f;
			ympyraFixt.restitution = 0.4f;
		
			ympyra.createFixture(ympyraFixt);
		
		// YMPYRÄN MUODOSTUS END
			
		// LAATIKKO
			
			BodyDef ldef = new BodyDef();
			ldef.type = BodyType.DynamicBody;
			ldef.position.set(width/2 + 2, height/2+10);
			
			laatikko = world.createBody(ldef);
			
			PolygonShape laatikkoShape = new PolygonShape();
			laatikkoShape.setAsBox(2.0f, 2.0f);
			
			FixtureDef laatikkoFix = new FixtureDef();
			laatikkoFix.shape = laatikkoShape;
			laatikkoFix.density = 0.4f;
			laatikkoFix.friction = 3.0f;
			laatikkoFix.restitution = 0.4f;
			laatikkoFix.filter.groupIndex = -2;
					
			laatikko.createFixture(laatikkoFix);
			
		// LAATIKKO END
			
			
		//Maa
		BodyDef maaDef = new BodyDef();
		
		maaDef.position.set(0,1);	
		Body maa = world.createBody(maaDef);
		
		maaDef.position.set(0,height-1);	
		Body mab = world.createBody(maaDef);
		
		maaDef.position.set(1,0);	
		Body mac = world.createBody(maaDef);
		
		maaDef.position.set(width-1,0);	
		Body mad = world.createBody(maaDef);
		
		PolygonShape maaBox = new PolygonShape();
		maaBox.setAsBox(camera.viewportWidth,1.0f );
		
		maa.createFixture(maaBox, 0.0f);
		mab.createFixture(maaBox, 0.0f);
		
		maaBox.setAsBox(1.0f,camera.viewportHeight );
		
		mac.createFixture(maaBox, 0.0f);
		mad.createFixture(maaBox, 0.0f);
		
		// Pelaajan luonti
		pelaaja = new Pelaaja(world, laatikko);
		Gdx.input.setInputProcessor(pelaaja);
	
	}
	

	@Override
	public void dispose () {
		world.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 255);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT); // GL10 -> GL30
		
		renderer.render(world, camera.combined);
		
		rayHandler.updateAndRender();
		
		world.step(1/60f, 6, 2);
		pelaaja.update();
		
		//logger.log();
		
	}
	
	@Override
	public void resize(int width, int height){
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}
	
}
