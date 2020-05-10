package sa.biotic.app.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.images_overlay_view.view.*
import sa.biotic.app.R

class ImagesOverLayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

//    var onDeleteClick: (Poster) -> Unit = {}

    init {
        View.inflate(context, R.layout.images_overlay_view, this)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun update(count: Int) {
        dots_indicator.initDots(count)
//        posterOverlayShareButton.setOnClickListener { context.sendShareIntent(poster.url) }
//        posterOverlayDeleteButton.setOnClickListener { onDeleteClick(poster) }
    }

    fun updatePosition(count: Int) {
        dots_indicator.setDotSelection(count)
//        posterOverlayShareButton.setOnClickListener { context.sendShareIntent(poster.url) }
//        posterOverlayDeleteButton.setOnClickListener { onDeleteClick(poster) }
    }
}