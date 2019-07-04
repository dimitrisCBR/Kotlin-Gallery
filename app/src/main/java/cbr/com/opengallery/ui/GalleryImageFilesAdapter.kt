package cbr.com.opengallery.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cbr.com.opengallery.R
import cbr.com.opengallery.util.loadImage
import kotlinx.android.synthetic.main.viewholder_image_file.view.*
import java.io.File

class GalleryImageFilesAdapter(private val listener: FileSelectionListener) : RecyclerView.Adapter<GalleryImageFilesAdapter.ImageViewHolder>() {

    private var mFiles = mutableListOf<File>()

    fun setItems(files: List<File>) {
        mFiles = files.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mFiles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder.new(parent, listener)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(mFiles[position])
    }

    class ImageViewHolder(itemView: View, private val listener: FileSelectionListener) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.viewholderImageView

        companion object {
            fun new(viewGroup: ViewGroup, listener: FileSelectionListener) = ImageViewHolder(LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.viewholder_image_file, viewGroup, false), listener)
        }

        fun bind(file: File) {
            imageView.loadImage(file)
            imageView.setOnClickListener({ listener.onFileSelected(file) })
        }
    }
}

