package com.aiyakeji.mytest.wallpaper;

/**
 * @Author:CWQ
 * @DATE:2022/9/1
 * @DESC:
 */
public class Boids {
    int mTotal = 0;
    int mWidth = 0;
    int mHeight = 0;
    public static float mBoidDist = 50.0f;
    public static float mMaxBoidSpeed = 12.0f;
    Boid[] mBoidList = null;
    int mCount = 5;
    int mMaxCount = 5;
    Position mPlace = new Position();
    float mPlaceFactor = 1.0f;
    float mBoxMinX = 1000, mBoxMaxX = 0, mBoxMinY = 1000,
            mBoxMaxY = 0;
    int mStates = 4;

    public Boids(int n, int w, int h, int st) {
        mTotal = n;
        mWidth = w;
        mHeight = h;
        mStates = st;

        init();
    }

    public void init() {
        mBoidList = null;
        mBoidList = new Boid[mTotal];

        for (int i = 0; i < mTotal; i++) {
            mBoidList[i] = new Boid(mStates);

            mBoidList[i].mPosition.x = (int)(200 * Math.random());
            mBoidList[i].mPosition.y = (int)(300 * Math.random());

            while (0 == mBoidList[i].mVelocity.x)
                mBoidList[i].mVelocity.x = (int)(2 * mMaxBoidSpeed *
                        Math.random()) - mMaxBoidSpeed;

            while (0 == mBoidList[i].mVelocity.y)
                mBoidList[i].mVelocity.y = (int)(2 * mMaxBoidSpeed *
                        Math.random()) - mMaxBoidSpeed;
        }
    }

    public void moveToNext() {
        for (int i = 0; i < mTotal; i++) {
            Velocity v1;
            Velocity v2;
            if (mCount <= mMaxCount && mPlace != null) {
                v1 = ruleTendToPlace(i, mPlaceFactor);
                v2 = new Velocity();
            } else {
                v1 = ruleFlyToCentroid(i, 0.05f);
                v2 = ruleKeepSmallDistance(i, mBoidDist);
            }
            float w1 = 1.0f;
            float w2 = 1.0f;

            Velocity v3 = ruleMatchNearVelocity(i, 0.125f);
            float w3 = 1.0f;

            mBoidList[i].mVelocity.x += (w1 * v1.x + w2 * v2.x + w3 *
                    v3.x);
            mBoidList[i].mVelocity.y += (w1 * v1.y + w2 * v2.y + w3 *
                    v3.y);

            ruleLimitVelocity(i, mMaxBoidSpeed);

            mBoidList[i].mPosition.x += mBoidList[i].mVelocity.x;
            mBoidList[i].mPosition.y += mBoidList[i].mVelocity.y;

            ruleBoundPosition(i, (float)(0.7f * mMaxBoidSpeed *
                    Math.random() + 0.3f * mMaxBoidSpeed));

            mBoidList[i].changeState();
        }

        if (mCount <= mMaxCount)
            mCount ++;
    }

    private void getBoundingBox() {
        mBoxMinX = mWidth; mBoxMaxX = 0;
        mBoxMinY = mHeight; mBoxMaxY = 0;
        for (int i = 0; i < mTotal; i++) {
            if (mBoidList[i].mPosition.x < mBoxMinX) mBoxMinX =
                    mBoidList[i].mPosition.x;
            if (mBoidList[i].mPosition.x > mBoxMaxX) mBoxMaxX =
                    mBoidList[i].mPosition.x;
            if (mBoidList[i].mPosition.y < mBoxMinY) mBoxMinY =
                    mBoidList[i].mPosition.y;
            if (mBoidList[i].mPosition.y > mBoxMaxY) mBoxMaxY =
                    mBoidList[i].mPosition.y;
        }
    }

    // Set a target place
    public void setTargetPlace(float x, float y, float f) {
        mPlace.x = x;
        mPlace.y = y;

        // If the touched point is within the boids, disperse
        // them away.
        // If it is outside the boids, make them follow it.
        getBoundingBox();
        if (x >= mBoxMinX && x <= mBoxMaxX && y >= mBoxMinY
                && y <= mBoxMaxY) {
            mPlaceFactor = -f;
            mCount  = 0;
            mMaxCount = 50;
        } else {
            mPlaceFactor = f;
            mCount  = 0;
            mMaxCount = (int)(Math.abs((mBoxMinX + mBoxMaxX) /
                    2.0f - x) + Math.abs((mBoxMinY + mBoxMaxY) /
                    2.0f - y));
        }
    }

    public void setTargetNone() {
        // If direction is "following", stop it.
        if (mPlaceFactor > 0) {
            mCount = mMaxCount - 10;
        }
    }

    public int getTotal() {
        return mTotal;
    }

    public Boid[] getBoids() {
        return mBoidList;
    }

    // RULE: fly to center of other boids
    private Velocity ruleFlyToCentroid(int id, float factor) {
        Velocity v = new Velocity();
        Position p = new Position();

        for (int i = 0; i < mTotal; i++) {
            if (i != id) {
                p.x += mBoidList[i].mPosition.x;
                p.y += mBoidList[i].mPosition.y;
            }
        }

        if (mTotal > 2) {
            p.x /= (mTotal - 1);
            p.y /= (mTotal - 1);
        }

        v.x = (p.x -  mBoidList[id].mPosition.x) * factor;
        v.y = (p.y -  mBoidList[id].mPosition.y) * factor;

        return v;
    }

    // RULE: keep a small distance from other boids
    private Velocity ruleKeepSmallDistance(int id, float dist) {
        Velocity v = new Velocity();

        for (int i = 0; i < mTotal; i++) {
            if (i != id) {
                if (Math.abs(mBoidList[id].mPosition.x -
                        mBoidList[i].mPosition.x) +
                        Math.abs(mBoidList[id].mPosition.y -
                                mBoidList[i].mPosition.y) < dist) {
                    v.x -= (mBoidList[i].mPosition.x -
                            mBoidList[id].mPosition.x);
                    v.y -= (mBoidList[i].mPosition.y -
                            mBoidList[id].mPosition.y);
                }
            }
        }

        return v;
    }

    // RULE: match velocity with near boids
    private Velocity ruleMatchNearVelocity(int id, float factor) {
        Velocity v = new Velocity();

        for (int i = 0; i < mTotal; i++) {
            if (i != id) {
                v.x += mBoidList[i].mVelocity.x;
                v.y += mBoidList[i].mVelocity.y;
            }
        }

        if (mTotal > 2) {
            v.x /= (mTotal - 1);
            v.y /= (mTotal - 1);
        }

        v.x = (v.x -  mBoidList[id].mVelocity.x) * factor;
        v.y = (v.y -  mBoidList[id].mVelocity.y) * factor;

        return v;
    }

    // RULE: consider wind speed
    private Velocity ruleReactToWind() {
        Velocity v = new Velocity();

        v.x = 1.0f;
        v.y = 0.0f;

        return v;
    }

    // RULE: tend to a place
    private Velocity ruleTendToPlace(int id, float factor) {
        Velocity v = new Velocity();

        v.x = (mPlace.x - mBoidList[id].mPosition.x) * factor;
        v.y = (mPlace.y - mBoidList[id].mPosition.y) * factor;

        return v;
    }

    // RULE: limit the velocity
    private void ruleLimitVelocity(int id, float vmax) {

        float vv = (float)Math.sqrt(mBoidList[id].mVelocity.x *
                mBoidList[id].mVelocity.x +
                mBoidList[id].mVelocity.y * mBoidList[id].mVelocity.y);

        if (vv > vmax) {
            mBoidList[id].mVelocity.x = (mBoidList[id].mVelocity.x /
                    vv) * vmax;
            mBoidList[id].mVelocity.y = (mBoidList[id].mVelocity.y /
                    vv) * vmax;
        }
    }

    // RULE: bound the position
    private void ruleBoundPosition(int id, float initv) {
        float pad = 10.0f;

        if (mBoidList[id].mPosition.x < pad) {
            mBoidList[id].mVelocity.x = initv;
        } else if (mBoidList[id].mPosition.x > mWidth - 2.0f * pad) {
            mBoidList[id].mVelocity.x = -initv;
        }

        if (mBoidList[id].mPosition.y < pad) {
            mBoidList[id].mVelocity.y = initv;
        } else if (mBoidList[id].mPosition.y > mHeight - 5.0f * pad) {
            mBoidList[id].mVelocity.y = -initv;
        }
    }
}
