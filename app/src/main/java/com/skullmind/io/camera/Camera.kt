package com.skullmind.io.camera

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.SensorManager.getOrientation
import android.hardware.camera2.*
import android.hardware.camera2.params.OutputConfiguration
import android.media.ImageReader
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import java.lang.RuntimeException
import android.hardware.camera2.CaptureRequest
import androidx.core.view.ViewCompat.getRotation
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Size


class Camera(private val mContext:Context){
    private val mCameraManager = getCameraManger()
    var mHandler:Handler? = null

    var mSurface:Surface ?= null
    var imageDimension: Size? = null
    private val imageReader: ImageReader
        get() = ImageReader.newInstance(750, 750, ImageFormat.JPEG, 2)

    lateinit var mPreviewRequest:CaptureRequest
    lateinit var mCaptureRequest:CaptureRequest
    var mSession:CameraCaptureSession? = null
    var cameraDevice:CameraDevice? = null

    private var isTakePhoto:Boolean = false
    private var isPreview:Boolean = true
    var cameraSessionCaptureCallBack: CameraCaptureSession.CaptureCallback
            = object: CameraCaptureSession.CaptureCallback(){
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
            if(isTakePhoto){
                Log.e("Camera","---->stopRepeat")
            }
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
    var cameraSessionStateCallback: CameraCaptureSession.StateCallback
            = object: CameraCaptureSession.StateCallback(){
        override fun onReady(session: CameraCaptureSession) {
            super.onReady(session)
            Log.e("Camera","--state-->onReady")
            if(isTakePhoto){
                isTakePhoto = false
                isPreview = false
                session.stopRepeating()
                session.abortCaptures()
                session.capture(mCaptureRequest,cameraSessionCaptureCallBack,mHandler)
            }else if(isPreview){
                session.setRepeatingRequest(mPreviewRequest,cameraSessionCaptureCallBack,mHandler)
            }
        }

        override fun onCaptureQueueEmpty(session: CameraCaptureSession) {
            super.onCaptureQueueEmpty(session)
            Log.e("Camera","--state-->onCaptureQueueEmpty")
        }

        override fun onConfigureFailed(session: CameraCaptureSession) {
            mSession = null
        }

        override fun onClosed(session: CameraCaptureSession) {
            super.onClosed(session)
            Log.e("Camera","--state-->onClosed")
            mSession = null
        }

        override fun onSurfacePrepared(session: CameraCaptureSession, surface: Surface) {
            super.onSurfacePrepared(session, surface)
            Log.e("Camera","--state-->onSurfacePrepared")
        }

        override fun onConfigured(session: CameraCaptureSession) {
            Log.e("Camera","--state-->onConfigured")
//            session.prepare(cameraDevice.holder.surface)
            mSession = session
            if(isTakePhoto){
                session.stopRepeating()
                session.capture(mCaptureRequest,cameraSessionCaptureCallBack,mHandler)
            }else if(isPreview){
                session.setRepeatingRequest(mPreviewRequest,cameraSessionCaptureCallBack,mHandler)
            }
        }

        override fun onActive(session: CameraCaptureSession) {
            super.onActive(session)
            Log.e("Camera","--state-->onActive")

        }

    }
    private val deviceStateCallBack: CameraDevice.StateCallback = object : CameraDevice.StateCallback(){
        override fun onOpened(camera: CameraDevice) {
            Log.e("Camera","--device state -->onOpened")
            cameraDevice = camera
            assert(mSurface?.isValid?:false)
            createCaptureSession(camera)
            buildPreviewRequest(camera)
            buildCaptureRequest(camera)
        }

        override fun onClosed(camera: CameraDevice) {
            super.onClosed(camera)
            Log.e("Camera","--device state -->onClosed")
            cameraDevice = null
        }

        override fun onDisconnected(camera: CameraDevice) {
            Log.e("Camera","--device state -->onDisconnected")
            cameraDevice = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice = null
            Log.e("Camera","--device state -->onError")
        }
    }

    private fun createCaptureSession(camera: CameraDevice) {
        val outputs = listOf(mSurface!!,imageReader.surface)
        camera.createCaptureSession(outputs, cameraSessionStateCallback, mHandler)
    }

    private fun buildPreviewRequest(camera: CameraDevice) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        builder.addTarget(mSurface)
        mPreviewRequest = builder.build()
    }
    private fun buildCaptureRequest(camera: CameraDevice) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        builder.addTarget(imageReader.surface)
        builder.set(CaptureRequest.CONTROL_AF_MODE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
        builder.set(CaptureRequest.JPEG_ORIENTATION, 0)
        mCaptureRequest = builder.build()
    }

    private fun checkCameraValid(cameraId:String):Boolean{
        if(TextUtils.isEmpty(cameraId)){ return false}
        for(item in mCameraManager.cameraIdList){
            if(cameraId == (item)){
                return true
            }
        }
        return false
    }



    @SuppressLint("MissingPermission")
    fun openCamera(cameraId: String, checkPermission:()-> Boolean,requestPermission:()->Unit){
        if(cameraEnable()) return
        if(!checkCameraValid(cameraId)) throw RuntimeException(" valid camera")
        if(checkPermission()){
            imageReader.setOnImageAvailableListener(ImageListener(),mHandler)
            updateCameraConfig(cameraId)
            mCameraManager.openCamera(cameraId,deviceStateCallBack,mHandler)
        }else{
            requestPermission()
        }
    }

    fun updateCameraConfig(cameraId: String) {
        val characteristics = mCameraManager.getCameraCharacteristics(cameraId)
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
        imageDimension = map!!.getOutputSizes(SurfaceTexture::class.java)[0]
    }

    fun previewPhoto(){
        isPreview = true
        assert(cameraDevice != null)
        if(mSession ==null) {
            createCaptureSession(cameraDevice!!)
        }else{
            mSession?.capture(mPreviewRequest,cameraSessionCaptureCallBack,mHandler)
        }
    }

    fun takePhoto(){
        isTakePhoto = true
    }

    private fun cameraEnable():Boolean = cameraDevice != null
    private fun getCameraManger():CameraManager
            = mContext.getSystemService(CAMERA_SERVICE) as CameraManager

    fun checkCameraHardware(): Boolean =
        mContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)


    fun releaseCamera(){
        imageReader?.close()
        mSession?.stopRepeating()
        mSession?.close()
        cameraDevice?.close()
    }
}



class ImageListener:ImageReader.OnImageAvailableListener{
    override fun onImageAvailable(reader: ImageReader?) {
        var image = reader?.acquireLatestImage()
        Log.d("Camera","--save image-->"+image?.format)
        image?.format
    }
}