package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.BubbleModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.HeroModel;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.view.HeroView;


public class GameView extends ScreenAdapter{

    private static final boolean DEBUG_PHYSICS = false;
    public final static float PIXEL_TO_METER = 0.04f;
    private static final float VIEWPORT_WIDTH = 10;     //66 full map; 10 zoom
    //private static final float VIEWPORT_HEIGHT = 20;

    private final Downwell game;
    private final GameModel model;
    private final GameController controller;

    private final OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugCamera;
    private EnemyView[] enemyViews;

    public GameView(Downwell game, GameModel model, GameController controller) {
        this.game = game;
        this.model = model;
        this.controller = controller;

        loadAssets();

        camera = createCamera();
        EnemyModel enemyModel[] = model.getEnemies();
        enemyViews = new EnemyView[enemyModel.length];
        for(int i = 0; i < enemyModel.length; i++){
            if(enemyModel[i] instanceof BatModel)
                enemyViews[i] = new BatView(game);
            else if(enemyModel[i] instanceof BubbleModel)
                enemyViews[i] = new BubbleView(game);
        }
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        //OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT / PIXEL_TO_METER );

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        //camera.position.set(0,0,0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "1.png" , Texture.class);
        this.game.getAssetManager().load( "2.png" , Texture.class);
        this.game.getAssetManager().load( "3.png" , Texture.class);
        this.game.getAssetManager().load( "4.png" , Texture.class);
        this.game.getAssetManager().load( "5.png" , Texture.class);
        this.game.getAssetManager().load( "6.png" , Texture.class);
        this.game.getAssetManager().load( "7.png" , Texture.class);

        this.game.getAssetManager().load( "r1.png" , Texture.class);
        this.game.getAssetManager().load( "r2.png" , Texture.class);
        this.game.getAssetManager().load( "r3.png" , Texture.class);
        this.game.getAssetManager().load( "r4.png" , Texture.class);
        this.game.getAssetManager().load( "r5.png" , Texture.class);
        this.game.getAssetManager().load( "r6.png" , Texture.class);
        this.game.getAssetManager().load( "r7.png" , Texture.class);

        this.game.getAssetManager().load( "jump.png" , Texture.class);

        this.game.getAssetManager().load( "bat1.png" , Texture.class);
        this.game.getAssetManager().load( "bat2.png" , Texture.class);
        this.game.getAssetManager().load( "bat3.png" , Texture.class);
        this.game.getAssetManager().load( "bat4.png" , Texture.class);
        this.game.getAssetManager().load( "bat5.png" , Texture.class);

        this.game.getAssetManager().load( "berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
        this.game.getAssetManager().load( "big bullet.png", Texture.class);
        this.game.getAssetManager().load("dBlock.png", Texture.class);
        this.game.getAssetManager().load("sideWall.png", Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.set(camera.position.x-2, camera.position.y,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.set(camera.position.x+2, camera.position.y,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.set(camera.position.x, camera.position.y+2,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.set(camera.position.x, camera.position.y-2,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            controller.moveHeroLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            controller.moveHeroRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            controller.jumpHero();
        }
    }

    @Override
    public void render(float delta) {
        handleInputs(delta);

        controller.update(delta);

        camera.position.set(GameController.ARENA_WIDTH/2f / PIXEL_TO_METER, model.getHeroModel().getY() / PIXEL_TO_METER, 0);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    private void drawEntities() {

        MapTileModel[][] map =  model.getMap();
        for(int i = 0; i < model.getDepth(); i++){
            for(int j = 0; j < model.getWidth(); j++)
                if (map[i][j] != null) {
                    ElementView view = ViewFactory.makeView(game, map[i][j]);
                    view.update(map[i][j]);
                    view.draw(game.getBatch());
                }
        }

        EnemyModel[] enemies = model.getEnemies();
        for(int i = 0; i < enemyViews.length; i++){
            enemyViews[i].update(enemies[i]);
            enemyViews[i].act(0.1f); //pq 0.3 e nao outro...0.4 fica mt rapido na mesma
            enemyViews[i].draw(game.getBatch());
        }

        HeroModel hero = model.getHeroModel();
        ElementView view = ViewFactory.makeView(game, hero);
        view.update(hero);
        view.act(0.1f);
        view.draw(game.getBatch());

        /*BatModel bat = model.getBatModel();
        ElementView view2 = ViewFactory.makeView(game, bat);
        view2.update(bat);
        view2.act(0.1f);
        view2.draw(game.getBatch());

        BubbleModel bubble = model.getBubbleModel();
        ElementView view3 = ViewFactory.makeView(game, bubble);
        view3.update(bubble);
        view3.draw(game.getBatch());*/
    }

    private void drawBackground() {
        Texture background = game.getAssetManager().get("berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(GameController.ARENA_WIDTH / PIXEL_TO_METER), (int) (GameController.ARENA_HEIGHT / PIXEL_TO_METER));

    }
}
