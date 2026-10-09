package com.project.karen

import android.graphics.Bitmap
import android.net.Uri
import com.project.karen.activity.MainActivity
import com.project.karen.fragment.CommonDialogFragment
import com.project.karen.fragment.ImagePreviewDialogFragment
import com.project.karen.fragment.MainFragment
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class FragmentLifecycleTest {
    @Test fun viewBindingIsReleasedAndRecreatedAfterDetach() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        try {
            val manager = controller.get().supportFragmentManager
            val fragment = manager.fragments.filterIsInstance<MainFragment>().single()
            val firstView = fragment.mBinding.root
            manager.beginTransaction().detach(fragment).commitNow()
            assertThrows(IllegalStateException::class.java) { fragment.mBinding }
            manager.beginTransaction().attach(fragment).commitNow()
            assertNotSame(firstView, fragment.mBinding.root)
        } finally {
            controller.pause().stop().destroy()
        }
    }

    @Test fun activityRecreationKeepsExactlyOneMainFragment() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        try {
            controller.recreate()
            assertEquals(1, controller.get().supportFragmentManager.fragments.filterIsInstance<MainFragment>().size)
        } finally {
            controller.pause().stop().destroy()
        }
    }

    @Test fun dialogsAndTheirActionsSurviveActivityRecreation() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        try {
            CommonDialogFragment().show(controller.get().supportFragmentManager)
            controller.get().supportFragmentManager.executePendingTransactions()
            controller.recreate()
            val manager = controller.get().supportFragmentManager
            val dialog = manager.fragments.filterIsInstance<CommonDialogFragment>().single()
            dialog.requireView().findViewById<android.view.View>(com.project.base.R.id.btn_left).performClick()
            manager.executePendingTransactions()
            assertFalse(dialog.isAdded)
        } finally {
            controller.pause().stop().destroy()
        }
    }

    @Test fun imageDialogRestoresItsArguments() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        try {
            val uri = Uri.parse("content://test/images/42")
            val imageBytes = ByteArrayOutputStream().use { output ->
                val bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                bitmap.recycle()
                output.toByteArray()
            }
            shadowOf(controller.get().contentResolver).registerInputStreamSupplier(uri) {
                ByteArrayInputStream(imageBytes)
            }
            ImagePreviewDialogFragment.newInstance(uri).show(controller.get().supportFragmentManager)
            controller.get().supportFragmentManager.executePendingTransactions()
            controller.recreate()
            val restored = controller.get().supportFragmentManager.fragments.filterIsInstance<ImagePreviewDialogFragment>().single()
            assertEquals(uri.toString(), restored.requireArguments().getString("image_uri"))
        } finally {
            controller.pause().stop().destroy()
        }
    }
}
