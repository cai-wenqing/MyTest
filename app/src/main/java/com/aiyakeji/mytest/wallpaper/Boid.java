package com.aiyakeji.mytest.wallpaper;

/**
 * @Author:CWQ
 * @DATE:2022/9/1
 * @DESC:
 */
public class Boid {
    public Position mPosition = null;
    public Velocity mVelocity = null;
    public boolean mPerching = false;
    public int mState = 0;
    public int mStates = 4;

    public Boid() {
        mPosition = new Position();
        mVelocity = new Velocity();
    }

    public Boid(int s) {
        mPosition = new Position();
        mVelocity = new Velocity();
        initStates(s);
    }

    public void initStates(int s) {
        mStates = s;
        mState = (int)(mStates * Math.random());
    }

    public void changeState() {
        mState = (mState + 1) % mStates;
    }
}
