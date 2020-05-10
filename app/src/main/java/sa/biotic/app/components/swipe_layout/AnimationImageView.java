
package sa.biotic.app.components.swipe_layout;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.ImageView;


/**
 * @author Elghamry Pythaghors
 */
class AnimationImageView extends ImageView {

  /**
   * AnimationのStartとEnd時にListenerにアレする
   */
  private Animation.AnimationListener mListener;

  /**
   *
   * {@inheritDoc}
   */
  public AnimationImageView(Context context) {
    super(context);
  }

  /**
   * {@link AnimationImageView#mListener} のセット
   *
   * @param listener {@link Animation.AnimationListener}
   */
  public void setAnimationListener(Animation.AnimationListener listener) {
    mListener = listener;
  }

  /**
   * ViewのAnimationのStart時にセットされたListenerの {@link Animation.AnimationListener#onAnimationStart(Animation)}
   * を呼ぶ
   */
  @Override public void onAnimationStart() {
    super.onAnimationStart();
    if (mListener != null) {
      mListener.onAnimationStart(getAnimation());
    }
  }

  /**
   * ViewのAnimationのEnd時にセットされたListenerの {@link Animation.AnimationListener#onAnimationEnd(Animation)}
   * (Animation)} を呼ぶ
   */
  @Override public void onAnimationEnd() {
    super.onAnimationEnd();
    if (mListener != null) {
      mListener.onAnimationEnd(getAnimation());
    }
  }
}