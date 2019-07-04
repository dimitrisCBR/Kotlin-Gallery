package cbr.com.opengallery.ui

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import cbr.com.opengallery.R

class GalleryDecorator(context: Context) : RecyclerView.ItemDecoration() {
    
    private val padding = context.resources.getDimension(R.dimen.xsmall)
    
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        
        val position = parent?.getChildAdapterPosition(view)
        
        outRect?.top = if (position == 0) padding.toInt() else (padding / 2).toInt()
        outRect?.bottom = (padding / 2).toInt()
    }
}