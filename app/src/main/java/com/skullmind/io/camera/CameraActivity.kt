package com.skullmind.io.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraDevice.StateCallback
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.skullmind.io.R

val EXTRA_CAMERA: String = "EXTRA_CAMERA"

fun newIntentToCamera(context: AppCompatActivity, cameraName:String): Intent {
    val intent: Intent = Intent(context, CameraActivity::class.java)
    intent.putExtra(EXTRA_CAMERA,cameraName)
    return intent
}
class CameraActivity :AppCompatActivity(){

    @BindView(R.id.tv_camera_name)
    lateinit var view:TextView

    lateinit var model: CameraViewModel

    var observer: Observer<String> = Observer{
        view.setText(it)
    }
    var handler: Handler = Handler()
    var stateCallBack: StateCallback = object :StateCallback(){
        override fun onOpened(camera: CameraDevice) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onClosed(camera: CameraDevice) {
            super.onClosed(camera)
        }

        override fun onDisconnected(camera: CameraDevice) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(camera: CameraDevice, error: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        ButterKnife.bind(this)
        val name = intent.getStringExtra(EXTRA_CAMERA)
        model = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        model.name.observe(this,observer)
        model.name.value = name

        if (!checkCameraHardware(this)){
            model.name.value = "not support"
        }
        val manager:CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraIds = manager.cameraIdList
        if(checkCameraPermission()) manager.openCamera("0",stateCallBack,handler)
        val ca:CameraCharacteristics = manager.getCameraCharacteristics("0")
    }

    private fun checkCameraPermission():Boolean
            = PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA)
}

private fun checkCameraHardware(context: Context): Boolean =
    context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

//fun getCameraInstance(): CameraDevice? {
//
//}