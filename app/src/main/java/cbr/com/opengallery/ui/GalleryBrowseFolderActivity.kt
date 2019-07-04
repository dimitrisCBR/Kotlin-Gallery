package cbr.com.opengallery.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import cbr.com.opengallery.R
import cbr.com.opengallery.util.getColumnCount
import cbr.com.opengallery.util.getImagesFromFolder
import io.reactivex.Single
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.io.File

class GalleryBrowseFolderActivity : BaseActivity(), FileSelectionListener {

    companion object {


        private const val INTENT_EXTRA_FOLDER = "INTENT_EXTRA_FOLDER"

        fun newIntent(context: Context, file: File): Intent {
            val intent = Intent(context, GalleryBrowseFolderActivity::class.java)
            intent.putExtra(INTENT_EXTRA_FOLDER, file)
            return intent
        }

    }

    private val mAdapter = GalleryImageFilesAdapter(this@GalleryBrowseFolderActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)
        setupUi()
    }

    private fun setupUi() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView.layoutManager = GridLayoutManager(this@GalleryBrowseFolderActivity,
                getColumnCount(this@GalleryBrowseFolderActivity, resources.getDimension(R.dimen.gallery_image_height).toInt()))
        recyclerView.adapter = mAdapter
        refreshContent(intent.getSerializableExtra(INTENT_EXTRA_FOLDER) as File)
    }

    private fun refreshContent(folder: File) {
        compositeDisposable.add(
                Single.just(getImagesFromFolder(this@GalleryBrowseFolderActivity, folder.absolutePath))
                        .subscribe(
                                { files -> mAdapter.setItems(files) },
                                { throwable: Throwable? -> handleError(throwable) }
                        )
        )
    }

    override fun onFileSelected(file: File) = Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show()
}