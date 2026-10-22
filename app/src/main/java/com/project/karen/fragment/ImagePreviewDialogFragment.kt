package com.project.karen.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.project.base.ui.BaseDialogFragment
import com.project.base.ui.widget.ZoomImageView

class ImagePreviewDialogFragment : BaseDialogFragment() {
    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View =
        ZoomImageView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            setBackgroundColor(0xffff0000.toInt())
            scaleType = ImageView.ScaleType.FIT_XY
            arguments?.getString(IMAGE_URI)?.let { setImageURI(Uri.parse(it)) }
        }

    companion object {
        private const val IMAGE_URI = "image_uri"

        fun newInstance(uri: Uri) = ImagePreviewDialogFragment().apply {
            arguments = Bundle().apply { putString(IMAGE_URI, uri.toString()) }
            setDimAmount(1f)
            setHeight(-1)
        }
    }
}
