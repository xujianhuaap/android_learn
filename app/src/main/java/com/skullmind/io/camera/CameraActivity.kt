package com.skullmind.io.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.*
import android.media.ImageReader
import android.os.*
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
    lateinit var cameraDevice: AutoFitTextureView

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
            if(camera.cameraEnable()){
                camera.previewPhoto()
            }else{
                openCamera()
            }

        }else if(view.id == R.id.tv_photo_take){
            camera.takePhoto()
        }
    }

    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermissions(
            arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE),
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
        if(cameraDevice.isAvailable){
            if(!camera.cameraEnable()){
                openCamera()
            }
        }else{

        }
    }

    override fun onStop() {
        super.onStop()
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
            for(item in PERMISSIONS){
                if(PERMISSION_DENIED == checkSelfPermission(item)){
                    return false
                }
            }
        }
        return true
    }


    fun openCamera() {
        camera.openCamera( {checkCameraPermission()},{requestCameraPermission()})
    }


    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground").also { it.start() }
        camera.getHandler(backgroundThread?.looper!!)
    }

    /**
     * Stops the background thread and its [Handler].
     */
    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            camera.mHandler?.removeCallbacksAndMessages(null)
            camera?.mHandler = null
        } catch (e: InterruptedException) {
            Log.e("Camera", e.toString())
        }

    }

    companion object{
        val PERMISSIONS = listOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


}
class SurfaceHolderCallBack(val activity: CameraActivity,val camera:Camera):TextureView.SurfaceTextureListener{
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.d("Camera","--Surface state--> onSurfaceTextureSizeChanged")
        camera.configureTransform(width,height,activity)
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
            camera.setUpCameraOutputs(width,height,activity)
            camera.configureTransform(width,height,activity)
            if(!camera.cameraEnable()){
                activity.openCamera()
            }
        }
    }


}







