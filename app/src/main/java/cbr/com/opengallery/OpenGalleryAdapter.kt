package cbr.com.opengallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.viewholder_folder.view.*
import java.io.File

/** Created by Dimitrios on 12/10/2017.*/
class OpenGalleryAdapter(private val listener: AdapterSelectionListener) : RecyclerView.Adapter<OpenGalleryViewHolder>() {
    
    private var items = mapOf<File, List<File>>()
    private var indexes = listOf<File>()
    
    override fun onBindViewHolder(holder: OpenGalleryViewHolder, position: Int) = holder.bind(getItemAt(position))
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenGalleryViewHolder? = OpenGalleryViewHolder.new(parent, listener)
    
    override fun getItemCount(): Int = indexes.size
    
    fun setItems(fileMap: Map<File, List<File>>) {
        items = fileMap
        indexes = fileMap.keys.toList()
        notifyDataSetChanged()
    }
    
    private fun getItemAt(position: Int) = items[indexes[position]]
}

interface AdapterSelectionListener {
    
    fun onItemSelected(file: File)
}

class OpenGalleryViewHolder(itemView: View, private val listener: AdapterSelectionListener) : RecyclerView.ViewHolder(itemView) {
    
    private val imageView = itemView.folderImageView
    private val titleTextView = itemView.folderTitleTextView
    
    companion object {
        fun new(viewGroup: ViewGroup, listener: AdapterSelectionListener) = OpenGalleryViewHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.viewholder_folder, viewGroup, false), listener)
    }
    
    fun bind(files: List<File>?) {
        (files?.isNotEmpty()).let {
            val firstChild = files!![0]
            val parent = File(firstChild.parent)
            titleTextView.text = parent.nameWithoutExtension
            imageView.loadImage(firstChild)
            itemView.setOnClickListener({ listener.onItemSelected(parent) })
        }
    }
}