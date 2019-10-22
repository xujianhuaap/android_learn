package com.skullmind.io.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.pm.PermissionInfo
import android.graphics.PixelFormat
import android.hardware.camera2.*
import android.hardware.camera2.CameraDevice.StateCallback
import android.hardware.camera2.CameraDevice.TEMPLATE_PREVIEW
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.skullmind.io.R

const val EXTRA_CAMERA: String = "EXTRA_CAMERA"
const val REQUEST_CODE_CAMERA:Int = 100

fun newIntentToCamera(context: AppCompatActivity, cameraName:String): Intent {
    val intent: Intent = Intent(context, CameraActivity::class.java)
    intent.putExtra(EXTRA_CAMERA,cameraName)
    return intent
}
class CameraActivity :AppCompatActivity(){

    @BindView(R.id.tv_camera_name)
    lateinit var view:TextView

    @BindView(R.id.sv_camera_device)
    lateinit var cameraDevice: SurfaceView

    lateinit var model: CameraViewModel

    var camera:Camera? = null

    var observer: Observer<String> = Observer{
        view.setText(it)
    }

    @OnClick(R.id.tv_take_photo)
    fun onClick() {
        camera?.openCamera("0",{checkCameraPermission()},{requestCameraPermission()})
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf<String>(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
    }

    private val surfaceCallback:SurfaceHolderCallBack = SurfaceHolderCallBack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        ButterKnife.bind(this)
        val name = intent.getStringExtra(EXTRA_CAMERA)

        camera = Camera(application)
        initSurfaceHolder()

        model = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        model.name.observe(this,observer)
        model.name.value = name

        if (!camera!!.checkCameraHardware()){
            model.name.value = "not support"
        }
    }

    private fun initSurfaceHolder() {
        cameraDevice.holder.setFixedSize(750, 750)
        cameraDevice.holder.setFormat(PixelFormat.RGB_888)
        surfaceCallback.camera = camera
        cameraDevice.holder.addCallback(surfaceCallback)
    }

    private fun checkCameraPermission():Boolean
            = PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(REQUEST_CODE_CAMERA == requestCode && grantResults[0] == PERMISSION_GRANTED){
           camera?.openCamera("0",{checkCameraPermission()},{requestCameraPermission()})
        }
    }


}







