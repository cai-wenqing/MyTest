package com.aiyakeji.mytest.photoview;

import android.graphics.RectF;

/**
 * @author CWQ
 * @date 2020/2/26
 */
public interface OnMatrixChangedListener {
    /**
     * Callback for when the Matrix displaying the Drawable has changed. This could be because
     * the View's bounds have changed, or the user has zoomed.
     *
     * @param rect - Rectangle displaying the Drawable's new bounds.
     */
    void onMatrixChanged(RectF rect);
}
