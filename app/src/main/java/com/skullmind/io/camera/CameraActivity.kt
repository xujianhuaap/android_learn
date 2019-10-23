package com.skullmind.io.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
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
    lateinit var cameraDevice: SurfaceView

    @Inject
    lateinit var model: CameraViewModel

    @Inject
    lateinit var camera: Camera

    private var observer: Observer<String> = Observer {
        view.text = it
    }

    @OnClick(value = [R.id.tv_photo_take,R.id.tv_photo_preview])
    fun onClick(view: View) {
        if(view.id == R.id.tv_photo_preview){
            openCamera()
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

    private val surfaceCallback: SurfaceHolderCallBack = SurfaceHolderCallBack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_camera)
        ButterKnife.bind(this)
        val name = intent.getStringExtra(EXTRA_CAMERA)

        initSurfaceHolder()

        initViewModel(name)
    }

    private fun initViewModel(name: String?) {
        model.name.observe(this, observer)
        model.name.value = name

        if (!camera.checkCameraHardware()) {
            model.name.value = "not support"
        }
    }

    private fun initSurfaceHolder() {
        cameraDevice.holder.setFixedSize(750, 750)
        cameraDevice.holder.setFormat(PixelFormat.RGB_888)
        surfaceCallback.camera = camera
        cameraDevice.holder.addCallback(surfaceCallback)
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

    private fun openCamera() {
        camera.openCamera("0", { checkCameraPermission() }, { requestCameraPermission() })
    }


}







