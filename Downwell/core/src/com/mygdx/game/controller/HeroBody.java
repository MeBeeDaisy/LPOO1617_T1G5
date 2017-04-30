package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.HeroModel;
import com.badlogic.gdx.physics.box2d.World;


public class HeroBody extends ElementBody {

    private boolean JUMPING;

    public HeroBody(World world, HeroModel model){
        super(world,model, BodyDef.BodyType.DynamicBody);
        float density = 1f, friction = 0.4f, restitution = 0;
        float width = 0.4f, height = 0.4f;

        createRectangleFixture(body,width,height,density,friction,restitution);
    }

    public boolean getState() { return JUMPING; }
}