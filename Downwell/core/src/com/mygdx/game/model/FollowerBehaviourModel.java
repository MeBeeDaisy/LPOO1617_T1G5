package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * Created by mc-guida on 30-04-2017.
 */

public class FollowerBehaviourModel extends BehaviourModel {

    private static final float ACTIVE_DISTANCE = 5f;
    private boolean active = false;

    public FollowerBehaviourModel() {

    }

    @Override
    public float[] act(float x, float y, Object obj) {
        HeroBody hero = (HeroBody) obj;
        double dist = Math.sqrt(Math.pow(x - hero.getX(),2) + Math.pow(y-hero.getY(),2));
        if(dist < ACTIVE_DISTANCE) {
            if(!active)
                active = true;
            float distX = x-hero.getX();
            float distY = y-hero.getY();
            float[] res = {(float)(distX * (2 / dist)),(float)(distY*(2/dist))};
            return res;
        }
        float[] res = new float[]{0, 0};
        return res;
    }

    public boolean isActive(){
        return active;
    }
}
