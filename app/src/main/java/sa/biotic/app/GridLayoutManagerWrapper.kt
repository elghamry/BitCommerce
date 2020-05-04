package sa.biotic.app

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class GridLayoutManagerWrapper(
    context: Context?,
    spanCount: Int,
    orientation: Int,
    reverseLayout: Boolean
) : GridLayoutManager(context, spanCount, orientation, reverseLayout) {


    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}