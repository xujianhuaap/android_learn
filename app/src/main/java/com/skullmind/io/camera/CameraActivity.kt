package com.skullmind.io.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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

val EXTRA_CAMERA: String = "EXTRA_CAMERA"
val REQUEST_CODE_CAMERA:Int = 100

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

    var observer: Observer<String> = Observer{
        view.setText(it)
    }
    var handler: Handler = Handler()
    lateinit var request:CaptureRequest

    lateinit var surface:Surface
    
    @OnClick(R.id.tv_take_photo)
    fun onClick(view: View) {
        Toast.makeText(this,"sss",Toast.LENGTH_LONG).show()
        if(checkCameraPermission()){
            cameraManager.openCamera("0",deviceStateCallBack,handler)
        } else{
            requestPermissions(arrayOf<String>(Manifest.permission.CAMERA),REQUEST_CODE_CAMERA)
        }
    }
    
    var cameraSessionCaptureCallBack:CameraCaptureSession.CaptureCallback
            = object:CameraCaptureSession.CaptureCallback(){
        override fun onCaptureSequenceAborted(session: CameraCaptureSession, sequenceId: Int) {
            super.onCaptureSequenceAborted(session, sequenceId)
            Log.e("Camera","---->onCaptureSequenceAborted")
        }

        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            super.onCaptureCompleted(session, request, result)
            Log.e("Camera","---->onCaptureCompleted")
        }

        override fun onCaptureFailed(session: CameraCaptureSession, request: CaptureRequest, failure: CaptureFailure) {
            super.onCaptureFailed(session, request, failure)
            Log.e("Camera","---->onCaptureFailed")
        }

        override fun onCaptureSequenceCompleted(session: CameraCaptureSession, sequenceId: Int, frameNumber: Long) {
            super.onCaptureSequenceCompleted(session, sequenceId, frameNumber)
            Log.e("Camera","---->onCaptureSequenceCompleted")
        }

        override fun onCaptureStarted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            timestamp: Long,
            frameNumber: Long
        ) {
            super.onCaptureStarted(session, request, timestamp, frameNumber)
            Log.e("Camera","---->onCaptureStarted")
        }

        override fun onCaptureProgressed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            partialResult: CaptureResult
        ) {
            super.onCaptureProgressed(session, request, partialResult)
            Log.e("Camera","---->onCaptureProgressed")
        }

        override fun onCaptureBufferLost(
            session: CameraCaptureSession,
            request: CaptureRequest,
            target: Surface,
            frameNumber: Long
        ) {
            super.onCaptureBufferLost(session, request, target, frameNumber)
            Log.e("Camera","---->onCaptureBufferLost")
        }
    }
    var cameraSessionStateCallback:CameraCaptureSession.StateCallback
            = object:CameraCaptureSession.StateCallback(){
        override fun onReady(session: CameraCaptureSession) {
            super.onReady(session)
            Log.e("Camera","--state-->onReady")
        }

        override fun onCaptureQueueEmpty(session: CameraCaptureSession) {
            super.onCaptureQueueEmpty(session)
            Log.e("Camera","--state-->onCaptureQueueEmpty")
        }

        override fun onConfigureFailed(session: CameraCaptureSession) {

        }

        override fun onClosed(session: CameraCaptureSession) {
            super.onClosed(session)
            Log.e("Camera","--state-->onClosed")
        }

        override fun onSurfacePrepared(session: CameraCaptureSession, surface: Surface) {
            super.onSurfacePrepared(session, surface)
            Log.e("Camera","--state-->onSurfacePrepared")
        }

        override fun onConfigured(session: CameraCaptureSession) {
            Log.e("Camera","--state-->onConfigured")
            session.prepare(cameraDevice.holder.surface)
            session.setRepeatingRequest(request,cameraSessionCaptureCallBack,handler)
        }

        override fun onActive(session: CameraCaptureSession) {
            super.onActive(session)
            Log.e("Camera","--state-->onActive")

        }
    }

    private val deviceStateCallBack: StateCallback = object :StateCallback(){
        override fun onOpened(camera: CameraDevice) {
            Log.e("Camera","--device state -->onOpened")
            assert(cameraDevice.holder.surface.isValid)
            val outputs = listOf<Surface>(cameraDevice.holder.surface)
            camera.createCaptureSession(outputs,cameraSessionStateCallback,handler)
            val builder = camera.createCaptureRequest(TEMPLATE_PREVIEW)
            builder.addTarget(cameraDevice.holder.surface)
            request = builder.build()
        }

        override fun onClosed(camera: CameraDevice) {
            super.onClosed(camera)
            Log.e("Camera","--device state -->onClosed")
        }

        override fun onDisconnected(camera: CameraDevice) {
            Log.e("Camera","--device state -->onDisconnected")
        }

        override fun onError(camera: CameraDevice, error: Int) {
            Log.e("Camera","--device state -->onError")
        }
    }

    private val surfaceCallback:SurfaceHolder.Callback = SurfaceHolderCallBack()
    private val cameraManager: CameraManager
        get() {
            val manager: CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            return manager
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        ButterKnife.bind(this)
        val name = intent.getStringExtra(EXTRA_CAMERA)

        initSurfaceHolder()

        model = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        model.name.observe(this,observer)
        model.name.value = name

        if (!checkCameraHardware(this)){
            model.name.value = "not support"
        }
        val manager: CameraManager = cameraManager
        val cameraIds = manager.cameraIdList

        val ca:CameraCharacteristics = manager.getCameraCharacteristics("0")
    }

    private fun initSurfaceHolder() {
        cameraDevice.holder.setFixedSize(750, 750)
        cameraDevice.holder.setFormat(PixelFormat.RGB_888)
        cameraDevice.holder.addCallback(surfaceCallback)
    }

    private fun checkCameraPermission():Boolean
            = PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(REQUEST_CODE_CAMERA == requestCode){
            val manager: CameraManager = cameraManager
            if(checkCameraPermission()) manager.openCamera("0",deviceStateCallBack,handler)
        }
    }

    override fun onStop() {
        super.onStop()
    }


}
class SurfaceHolderCallBack:SurfaceHolder.Callback{
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.e("Camera","--surface-->surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.e("Camera","--surface-->surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.e("Camera","--surface-->surfaceCreated")
    }

}
private fun checkCameraHardware(context: Context): Boolean =
    context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)






