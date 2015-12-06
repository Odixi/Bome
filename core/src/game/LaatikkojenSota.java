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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
	OrthographicCamera camera2;
	float width, height;
	FPSLogger logger;
	
	World world;
	Box2DDebugRenderer renderer;
	
	Pelaaja pelaaja;
	ContactDetectioin contactDetection;
	
	RayHandler rayHandler;
	
	OrthogonalTiledMapRenderer mapRenderer;
	TiledMap map;
	
	
	@Override
	public void create () {
		
		// TODO 
		// Korjaa resoluution handlaus
		// TODO
		// Tee uusi luokka pelille / mainmenulle jne.
		width = Gdx.graphics.getWidth() / 6;
		height = Gdx.graphics.getHeight() / 6;
		
		camera = new OrthographicCamera(width, height);
		camera2 = new OrthographicCamera(width*6,height*6);
		camera.position.set(width * 0.5f, height * 0.5f, 0);
		camera2.position.set(width * 0.5f*6, height * 0.5f*6, 0);
		camera.update();
		camera2.update();
		
		world = new World(new Vector2(0, -9.8f*10), false);
		contactDetection = new ContactDetectioin();
		world.setContactListener(contactDetection);
		
		renderer = new Box2DDebugRenderer();
		
		logger = new FPSLogger();
		
		// Tile map jutut
		
		map = new TmxMapLoader().load("map1.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		TileObjectUtil.parseTileObjectLayer(world, map.getLayers().get("collision").getObjects());
		
		// Valoilmi�
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.setBlurNum(3);
		rayHandler.setAmbientLight(0.2f);
        new PointLight(rayHandler, 800, Color.DARK_GRAY , width/1.2f, width/2f+50, height/2f);
        new PointLight(rayHandler, 800, Color.DARK_GRAY , width/1.2f, width/2f-50, height/2f);
        PointLight tausta = new PointLight(rayHandler, 10, Color.DARK_GRAY , width/0.8f, width/2f, height/2f);
		tausta.setXray(true);
		
		//Maa
//		BodyDef maaDef = new BodyDef();
//		
//		maaDef.position.set(0,1);	
//		Body maa = world.createBody(maaDef);
//		
//		maaDef.position.set(0,height-1);	
//		Body mab = world.createBody(maaDef);
//		
//		maaDef.position.set(1,0);	
//		Body mac = world.createBody(maaDef);
//		
//		maaDef.position.set(width-1,0);	
//		Body mad = world.createBody(maaDef);
//		
//		PolygonShape maaBox = new PolygonShape();
//		maaBox.setAsBox(camera.viewportWidth,1.0f );
//		
//		maa.createFixture(maaBox, 0.0f);
//		mab.createFixture(maaBox, 0.0f);
//		
//		maaBox.setAsBox(1.0f,camera.viewportHeight );
//		
//		mac.createFixture(maaBox, 0.0f);
//		mad.createFixture(maaBox, 0.0f);
		
		// Pelaajan luonti
		pelaaja = new Pelaaja(world);
		Gdx.input.setInputProcessor(pelaaja);
	
	}
	

	@Override
	public void dispose () {
		world.dispose();
		map.dispose();
		mapRenderer.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 255);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT); // GL10 -> GL30
		
		mapRenderer.setView(camera2);
		mapRenderer.render();
		
		renderer.render(world, camera.combined);
		
		rayHandler.updateAndRender();
		
		world.step(1/60f, 6, 2);
		pelaaja.update();
		
		logger.log();
		
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
