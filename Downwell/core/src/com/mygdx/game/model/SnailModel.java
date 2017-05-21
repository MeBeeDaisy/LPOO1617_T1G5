package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

public class SnailModel extends EnemyModel {

    int direction; //0 is down, 1 is up

    public SnailModel(float x, float y){
        super(x,y,new WallWalkerBehaviourModel());
        direction = 0;
    }

    public ModelType getType(){
        return ModelType.SNAIL;
    }

    public int getDirection() { return direction; }

    public void changeDirection() {
        if(direction == 0)
            direction = 1;
        else
            direction = 0;
    }

    public float[] update(HeroBody hero){
        String dir;
        if(direction == 0)
            dir = "down";
        else
            dir = "up";
        float[] res = behaviour.act(this.getX(), this.getY(), dir);
        if(res[1] < 0){
            changeDirection();
            res[1] = 0;
        }
        return res;
    }
}