package com.skullmind.io.camera

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.util.Size
import android.view.Surface
import okio.Okio
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit


class Camera(private val mContext: Context) {
    val cameraOpenCloseLock = Semaphore(1)
    val saveImageLock = Semaphore(1)
    private val mCameraManager = getCameraManger()
    var mHandler: Handler? = null

    private lateinit var mCameraId: String
    var mCurrentImage:ByteBuffer? = null
    var mSurface: Surface? = null
    private var mSurfaceConfig = SurfaceConfig(mCameraManager)
    private var imageReader: ImageReader? = null

    lateinit var mPreviewRequest: CaptureRequest
    lateinit var mCaptureRequest: CaptureRequest
    var mSession: CameraCaptureSession? = null
    var cameraDevice: CameraDevice? = null

    private lateinit var mState:CameraState
    private var enableFlash = false
    var cameraSessionCaptureCallBack: CameraCaptureSession.CaptureCallback =
        object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureSequenceAborted(session: CameraCaptureSession, sequenceId: Int) {
                super.onCaptureSequenceAborted(session, sequenceId)
                Log.e("Camera", "---->onCaptureSequenceAborted")
            }

            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                super.onCaptureCompleted(session, request, result)
                if (mState == CameraState.STATE_LOCK) {
                    Log.e("Camera", "---->stopRepeat")
                    mSession?.stopRepeating()
                    mSession?.abortCaptures()
                    mState = CameraState.STATE_UNLOCK
                }
                Log.e("Camera", "---->onCaptureCompleted")
            }

            override fun onCaptureFailed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                failure: CaptureFailure
            ) {
                super.onCaptureFailed(session, request, failure)
                Log.e("Camera", "---->onCaptureFailed")
            }

            override fun onCaptureSequenceCompleted(session: CameraCaptureSession, sequenceId: Int, frameNumber: Long) {
                super.onCaptureSequenceCompleted(session, sequenceId, frameNumber)
                Log.e("Camera", "---->onCaptureSequenceCompleted")
            }

            override fun onCaptureStarted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                timestamp: Long,
                frameNumber: Long
            ) {
                super.onCaptureStarted(session, request, timestamp, frameNumber)
                Log.e("Camera", "---->onCaptureStarted")
            }

            override fun onCaptureProgressed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                partialResult: CaptureResult
            ) {
                super.onCaptureProgressed(session, request, partialResult)
                Log.e("Camera", "---->onCaptureProgressed")
            }

            override fun onCaptureBufferLost(
                session: CameraCaptureSession,
                request: CaptureRequest,
                target: Surface,
                frameNumber: Long
            ) {
                super.onCaptureBufferLost(session, request, target, frameNumber)
                Log.e("Camera", "---->onCaptureBufferLost")
            }
        }
    private var cameraSessionStateCallback: CameraCaptureSession.StateCallback = object : CameraCaptureSession.StateCallback() {
        override fun onReady(session: CameraCaptureSession) {
            super.onReady(session)
            Log.e("Camera", "--state-->onReady")
            when(mState){
                CameraState.STATE_LOCK ->
                    session.capture(mCaptureRequest, cameraSessionCaptureCallBack, mHandler)
                CameraState.STATE_PREVIEW ->
                    session.setRepeatingRequest(mPreviewRequest, cameraSessionCaptureCallBack, mHandler)
            }
        }

        override fun onCaptureQueueEmpty(session: CameraCaptureSession) {
            super.onCaptureQueueEmpty(session)
            Log.e("Camera", "--state-->onCaptureQueueEmpty")
        }

        override fun onConfigureFailed(session: CameraCaptureSession) {
            mSession = null
            cameraDevice = null
        }

        override fun onClosed(session: CameraCaptureSession) {
            super.onClosed(session)
            Log.e("Camera", "--state-->onClosed")
            mSession = null
            cameraDevice = null
        }

        override fun onSurfacePrepared(session: CameraCaptureSession, surface: Surface) {
            super.onSurfacePrepared(session, surface)
            Log.e("Camera", "--state-->onSurfacePrepared")
        }

        override fun onConfigured(session: CameraCaptureSession) {
            Log.e("Camera", "--state-->onConfigured")
            mSession = session
            when(mState){
                CameraState.STATE_OPENED ->
                    session.setRepeatingRequest(mPreviewRequest, cameraSessionCaptureCallBack, mHandler)
            }
        }

        override fun onActive(session: CameraCaptureSession) {
            super.onActive(session)
            Log.e("Camera", "--state-->onActive")

        }

    }
    private val deviceStateCallBack: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            Log.e("Camera", "--device state -->onOpened")
            cameraDevice = camera
            mState = CameraState.STATE_OPENED
            assert(mSurface?.isValid ?: false)
            cameraOpenCloseLock.release()
            createCaptureSession(camera)
            buildPreviewRequest(camera)
            buildCaptureRequest(camera)

        }

        override fun onClosed(camera: CameraDevice) {
            super.onClosed(camera)
            Log.e("Camera", "--device state -->onClosed")
            releaseCamera()
            mState = CameraState.STATE_UNOPEN
        }

        override fun onDisconnected(camera: CameraDevice) {
            Log.e("Camera", "--device state -->onDisconnected")
            cameraOpenCloseLock.release()
            releaseCamera()
            mState = CameraState.STATE_UNOPEN
        }

        override fun onError(camera: CameraDevice, error: Int) {
            releaseCamera()
            mState = CameraState.STATE_UNOPEN
            Log.e("Camera", "--device state -->onError")
        }
    }

    private fun createCaptureSession(camera: CameraDevice) {
        val outputs = listOf(mSurface!!, imageReader?.surface)
        camera.createCaptureSession(outputs, cameraSessionStateCallback, mHandler)
    }

    private fun buildPreviewRequest(camera: CameraDevice) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        builder.addTarget(mSurface!!)
        mPreviewRequest = builder.build()
    }

    private fun buildCaptureRequest(camera: CameraDevice) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        builder.addTarget(imageReader?.surface!!)
        builder.set(
            CaptureRequest.CONTROL_AF_MODE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
        )
        builder.set(CaptureRequest.JPEG_ORIENTATION, 0)
        mCaptureRequest = builder.build()
    }

    private fun checkCameraValid(cameraId: String): Boolean {
        if (TextUtils.isEmpty(cameraId)) {
            return false
        }
        for (item in mCameraManager.cameraIdList) {
            if (cameraId == (item)) {
                return true
            }
        }
        return false
    }


    @SuppressLint("MissingPermission")
    fun openCamera(checkPermission: () -> Boolean, requestPermission: () -> Unit) {
        if( !checkPermission()){
            requestPermission()
            return
        }
        if (cameraEnable()) return
        if (!checkCameraValid(mCameraId))
            throw RuntimeException(" valid camera $mCameraId")
        if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS))
            throw RuntimeException("Time out waiting to lock camera opening.")

        try {
            mSurfaceConfig.updateCameraConfig(mCameraId)
            Log.e("Camera","--open Camera Id $mCameraId")
            mCameraManager.openCamera(mCameraId, deviceStateCallBack, mHandler)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            releaseCamera()
        }
    }



    private fun initImageReader(largest: Size) {
        imageReader = ImageReader.newInstance(
            largest.width, largest.height,
            ImageFormat.JPEG, /*maxImages*/ 2
        )
        imageReader?.setOnImageAvailableListener(ImageListener(this), mHandler)
    }


    fun previewPhoto() {
        if(mState == CameraState.STATE_OPENED || mState == CameraState.STATE_UNLOCK){
            mState = CameraState.STATE_PREVIEW
        }
        assert(cameraDevice != null)
        if (mSession == null) {
            createCaptureSession(cameraDevice!!)
        } else {
            mSession?.capture(mPreviewRequest, cameraSessionCaptureCallBack, mHandler)
        }
    }

    fun takePhoto() {
       mState = CameraState.STATE_LOCK
    }

    fun cameraEnable(): Boolean = cameraDevice != null
    private fun getCameraManger(): CameraManager = mContext.getSystemService(CAMERA_SERVICE) as CameraManager

    fun checkCameraHardware(): Boolean =
        mContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)


    fun releaseCamera() {
        imageReader?.close()
        mSession?.stopRepeating()
        mSession?.close()
        cameraDevice?.close()
        mSurface?.release()

    }

    private fun initHandler(looper:Looper)= object:Handler(looper){
        @SuppressLint("MissingPermission")
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                MESSAGE_SAVE_IMAGE ->{
                   val dir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
                    val imgName = simpleDateFormat.format(Date())+".jpg"
                    val imgFile = File(dir,imgName)
                    if(imgFile.exists()){
                        imgFile.delete()
                    }
                    if(!imgFile.exists()) imgFile.createNewFile()
                    val sink = Okio.sink(imgFile)
                    val buffer = Okio.buffer(sink)
                    buffer.write(mCurrentImage)
                    buffer.close()
                    saveImageLock.release()
                }
            }
        }
    }

    fun getHandler(looper: Looper){
        mHandler = initHandler(looper)
    }

    fun updateCameraViewTransform(width:Int, height:Int, activity:CameraActivity){
        val matrix = mSurfaceConfig.obtainCameraViewTransform(width,height,activity)
        activity.updateCameraViewMatrix(matrix)
    }
    fun initFrontCamera(width: Int, height: Int, activity: CameraActivity,success:(previewSize:Size?)->Unit){

        mSurfaceConfig.initFrontCamera(width,height,activity){
                cameraConfig,largestSize,previewSize ->
            mCameraId = cameraConfig.cameraId
            enableFlash = cameraConfig.enableFlash
            success(previewSize)
            initImageReader(largestSize)}

    }


    companion object {
        const val MESSAGE_SAVE_IMAGE = 1
    }
}


class ImageListener(val camera: Camera) : ImageReader.OnImageAvailableListener {
    override fun onImageAvailable(reader: ImageReader?) {
        val image = reader?.acquireLatestImage()
        Log.d("Camera", "--save image-->" + image?.planes?.size)
        val buffers = image?.planes?.get(0)?.buffer
        camera.mCurrentImage = buffers
        val msg = Message.obtain()
        msg.what = Camera.MESSAGE_SAVE_IMAGE
        camera.saveImageLock.acquire()
        camera.mHandler?.sendMessage(msg)

    }
}



/**
 * 比较工具类
 */
internal class CompareSizesByArea : Comparator<Size> {

    override fun compare(lhs: Size, rhs: Size) =
        java.lang.Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)

}