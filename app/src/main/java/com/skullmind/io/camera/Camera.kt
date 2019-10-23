package com.skullmind.io.camera

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import java.lang.RuntimeException

class Camera(private val mContext:Context){
    private val mCameraManager = getCameraManger()
    val mHandler:Handler = Handler(mContext.mainLooper)

    lateinit var mRequest:CaptureRequest

    var mSurface:Surface ?= null
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
//            session.prepare(cameraDevice.holder.surface)
            session.setRepeatingRequest(mRequest,cameraSessionCaptureCallBack,mHandler)
        }

        override fun onActive(session: CameraCaptureSession) {
            super.onActive(session)
            Log.e("Camera","--state-->onActive")

        }

    }
    private val deviceStateCallBack: CameraDevice.StateCallback = object : CameraDevice.StateCallback(){
        override fun onOpened(camera: CameraDevice) {
            Log.e("Camera","--device state -->onOpened")
            assert(mSurface?.isValid?:false)
            val outputs = listOf<Surface>(mSurface!!)
            camera.createCaptureSession(outputs,cameraSessionStateCallback,mHandler)
            val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            builder.addTarget(mSurface)
            mRequest = builder.build()
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
        if(!checkCameraValid(cameraId)) throw RuntimeException(" valid camera")
        if(checkPermission()){
            mCameraManager.openCamera(cameraId,deviceStateCallBack,mHandler)
        }else{
            requestPermission()
        }
    }

    private fun getCameraManger():CameraManager
            = mContext.getSystemService(CAMERA_SERVICE) as CameraManager

    fun checkCameraHardware(): Boolean =
        mContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)


}

class SurfaceHolderCallBack: SurfaceHolder.Callback{
    var camera:Camera? = null
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.e("Camera","--surface-->surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.e("Camera","--surface-->surfaceDestroyed")
        camera?.mSurface = null
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.e("Camera","--surface-->surfaceCreated")
        camera?.mSurface = holder?.surface
    }
}