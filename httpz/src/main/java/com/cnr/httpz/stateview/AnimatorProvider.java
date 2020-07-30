package com.cnr.httpz.stateview;

import android.animation.Animator;
import android.view.View;

/**
 * @author Nukc.
 */

public interface AnimatorProvider {

    Animator showAnimation(View view);

    Animator hideAnimation(View view);
}