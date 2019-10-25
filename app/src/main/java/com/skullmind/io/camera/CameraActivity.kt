package com.skullmind.io.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.*
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.skullmind.io.R
import dagger.android.AndroidInjection
import javax.inject.Inject

const val EXTRA_CAMERA: String = "EXTRA_CAMERA"
const val REQUEST_CODE_CAMERA: Int = 100

fun newIntentToCamera(context: AppCompatActivity, cameraName: String): Intent {
    val intent = Intent(context, CameraActivity::class.java)
    intent.putExtra(EXTRA_CAMERA, cameraName)
    return intent
}

/***
 * 采用MVVM架构
 */
class CameraActivity : AppCompatActivity() {

    @BindView(R.id.tv_camera_name)
    lateinit var view: TextView

    @BindView(R.id.sv_camera_device)
    lateinit var cameraDevice: TextureView

    @Inject
    lateinit var  surfaceCallback: SurfaceHolderCallBack

    @Inject
    lateinit var model: CameraViewModel

    @Inject
    lateinit var camera: Camera

    var backgroundThread:HandlerThread? = null

    private var observer: Observer<String> = Observer {
        view.text = it
    }

    @OnClick(value = [R.id.tv_photo_take,R.id.tv_photo_preview])
    fun onClick(view: View) {
        if(view.id == R.id.tv_photo_preview){
            camera.previewPhoto()
        }else if(view.id == R.id.tv_photo_take){
            camera.takePhoto()
        }
    }

    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CODE_CAMERA
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_camera)
        ButterKnife.bind(this)
        val name = intent.getStringExtra(EXTRA_CAMERA)

        initSurfaceHolder()

        initViewModel(name)

        camera.updateCameraConfig("0")
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
    }

    override fun onPause() {
        super.onPause()
        camera.releaseCamera()
        stopBackgroundThread()
    }

    private fun initViewModel(name: String?) {
        model.name.observe(this, observer)
        model.name.value = name

        if (!camera.checkCameraHardware()) {
            model.name.value = "not support"
        }
    }

    private fun initSurfaceHolder() {
        cameraDevice.surfaceTextureListener =surfaceCallback
        cameraDevice.surfaceTextureListener
    }

    private fun checkCameraPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA)
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (REQUEST_CODE_CAMERA == requestCode && grantResults[0] == PERMISSION_GRANTED) {
            openCamera()
        }
    }

    fun openCamera() {
        camera.openCamera("0", { checkCameraPermission() }, { requestCameraPermission() })
    }


    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground").also { it.start() }
        camera.mHandler =  Handler(backgroundThread?.looper)
    }

    /**
     * Stops the background thread and its [Handler].
     */
    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            camera?.mHandler = null
        } catch (e: InterruptedException) {
            Log.e("Camera", e.toString())
        }

    }


}
class SurfaceHolderCallBack(val activity: CameraActivity,val camera:Camera):TextureView.SurfaceTextureListener{
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("Camera","--Surface state--> onSurfaceTextureSizeChanged")
        configureTransform(width,height)
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        Log.d("Camera","--Surface state--> onSurfaceTextureUpdated")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean = true

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("Camera","--Surface state--> onSurfaceTextureAvailable")
        surface?.also {
            surface.setDefaultBufferSize(activity.cameraDevice.width,activity.cameraDevice.height)
            val previewSurface = Surface(surface)
            camera.mSurface = previewSurface
            configureTransform(width,height)
            activity.openCamera()
        }
    }

    private fun configureTransform(viewWidth: Int, viewHeight: Int) {
        val rotation = activity.windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f,
            camera?.imageDimension?.width?.toFloat()!!,
            camera?.imageDimension?.height?.toFloat()!!)
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
            val scale = Math.max(
                viewHeight.toFloat() / camera?.imageDimension?.height?.toFloat()!!,
                viewWidth.toFloat() / camera?.imageDimension?.width?.toFloat()!!
            )
            matrix.postScale(scale, scale, centerX, centerY)
            matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180f, centerX, centerY)
        }
        activity.cameraDevice.setTransform(matrix)
    }
}







