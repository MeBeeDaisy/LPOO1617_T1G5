package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * Created by mc-guida on 30-04-2017.
 */

public class FollowerBehaviourModel extends BehaviourModel {

    public FollowerBehaviourModel() {

    }

    @Override
    public float[] act(float x, float y, Object obj) {
        HeroBody hero = (HeroBody) obj;
        double dist = Math.sqrt(Math.pow(x - hero.getX(),2) + Math.pow(y-hero.getY(),2));
        if(dist < 5) {
            float distX = (float) ((x - hero.getX()) * 0.01);
            float distY = (float) ((y - hero.getY()) * 0.01);
            float[] res = {x - distX, y - distY};
            return res;
        }
        float[] res = new float[]{x, y};
        return res;
    }
}