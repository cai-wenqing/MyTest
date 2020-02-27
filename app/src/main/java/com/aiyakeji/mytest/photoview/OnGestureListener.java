package com.aiyakeji.mytest.photoview;

/**
 * @author CWQ
 * @date 2020/2/26
 */
public interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);
}
