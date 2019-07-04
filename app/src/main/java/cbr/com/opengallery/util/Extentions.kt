package cbr.com.opengallery.util

import android.widget.ImageView
import cbr.com.opengallery.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

fun ImageView.loadImage(file: File) {
    Glide.with(this)
            .load(file)
            .apply(RequestOptions()
                    .placeholder(R.drawable.shadow_gradient)
                    .centerCrop())
            .into(this)
}