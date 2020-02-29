package com.skullmind.io.camera

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.content.res.Configuration
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
import kotlin.math.sin


class Camera(private val mContext: Context) {
    val cameraOpenCloseLock = Semaphore(1)
    val saveImageLock = Semaphore(1)
    private val mCameraManager = getCameraManger()
    var mHandler: Handler? = null

    lateinit var mCameraId: String
    var mCurrentImage:ByteBuffer? = null
    var mSurface: Surface? = null
    var previewSize: Size? = null
    var imageReader: ImageReader? = null

    lateinit var mPreviewRequest: CaptureRequest
    lateinit var mCaptureRequest: CaptureRequest
    var mSession: CameraCaptureSession? = null
    var cameraDevice: CameraDevice? = null

    private var isTakePhoto: Boolean = false
    private var isPreview: Boolean = true
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
                if (isTakePhoto) {
                    Log.e("Camera", "---->stopRepeat")
                    mSession?.stopRepeating()
                    mSession?.abortCaptures()
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
    var cameraSessionStateCallback: CameraCaptureSession.StateCallback = object : CameraCaptureSession.StateCallback() {
        override fun onReady(session: CameraCaptureSession) {
            super.onReady(session)
            Log.e("Camera", "--state-->onReady")
            if (isTakePhoto) {
                isTakePhoto = false
                isPreview = false
//                session.capture(mPreviewRequest, cameraSessionCaptureCallBack, mHandler)
                session.capture(mCaptureRequest, cameraSessionCaptureCallBack, mHandler)
            } else if (isPreview) {
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
//            session.prepare(cameraDevice.holder.surface)
            mSession = session
            if (isTakePhoto) {
                session.stopRepeating()
                session.capture(mCaptureRequest, cameraSessionCaptureCallBack, mHandler)
            } else if (isPreview) {
                if(mPreviewRequest == null){
                    buildPreviewRequest(cameraDevice!!)
                }
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
            assert(mSurface?.isValid ?: false)
            cameraOpenCloseLock.release()
            createCaptureSession(camera)
            buildPreviewRequest(camera)
            buildCaptureRequest(camera)

        }

        override fun onClosed(camera: CameraDevice) {
            super.onClosed(camera)
            Log.e("Camera", "--device state -->onClosed")
            cameraDevice = null
        }

        override fun onDisconnected(camera: CameraDevice) {
            Log.e("Camera", "--device state -->onDisconnected")
            cameraOpenCloseLock.release()
            cameraDevice = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice = null
            Log.e("Camera", "--device state -->onError")
        }
    }

    private fun createCaptureSession(camera: CameraDevice) {
        val outputs = listOf(mSurface!!, imageReader?.surface)
        camera.createCaptureSession(outputs, cameraSessionStateCallback, mHandler)
    }

    private fun buildPreviewRequest(camera: CameraDevice) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        builder.addTarget(mSurface)
        mPreviewRequest = builder.build()
    }

    private fun buildCaptureRequest(camera: CameraDevice) {
        val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        builder.addTarget(imageReader?.surface)
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

        if (!checkCameraValid(mCameraId)) throw RuntimeException(" valid camera")

        updateCameraConfig(mCameraId)
        if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
            throw RuntimeException("Time out waiting to lock camera opening.")
        }
        mCameraManager.openCamera(mCameraId, deviceStateCallBack, mHandler)
    }

    fun setUpCameraOutputs(width: Int, height: Int, activity: CameraActivity)  {
        try {
            for (cameraId in mCameraManager.cameraIdList) {
                val characteristics = mCameraManager.getCameraCharacteristics(cameraId)

                // We don't use a front facing camera in this sample.
                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (cameraDirection != null &&
                    cameraDirection == CameraCharacteristics.LENS_FACING_FRONT
                ) {
                    continue
                }

                val map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
                ) ?: continue

                // For still image captures, we use the largest available size.
                val largest = Collections.max(
                    Arrays.asList(*map.getOutputSizes(ImageFormat.JPEG)),
                    CompareSizesByArea()
                )
                imageReader = ImageReader.newInstance(
                    largest.width, largest.height,
                    ImageFormat.JPEG, /*maxImages*/ 2
                )
                imageReader?.setOnImageAvailableListener(ImageListener(this), mHandler)

                // 0 屏幕未旋转,90 屏幕顺时针旋转90
                val displayRotation = activity.windowManager.defaultDisplay.rotation
                //[0,90,180,270]
                val sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)
                val swappedDimensions = Config.areDimensionsSwapped(displayRotation, sensorOrientation)

                val displaySize = Point()
                activity.windowManager.defaultDisplay.getSize(displaySize)
                val rotatedPreviewWidth = if (swappedDimensions) height else width
                val rotatedPreviewHeight = if (swappedDimensions) width else height
                var maxPreviewWidth = if (swappedDimensions) displaySize.y else displaySize.x
                var maxPreviewHeight = if (swappedDimensions) displaySize.x else displaySize.y

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) maxPreviewWidth = MAX_PREVIEW_WIDTH
                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) maxPreviewHeight = MAX_PREVIEW_HEIGHT

                // Danger, W.R.! Attempting to use too large a preview size could exceed the camera
                // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
                // garbage capture data.
                previewSize = chooseOptimalSize(
                    map.getOutputSizes(SurfaceTexture::class.java),
                    rotatedPreviewWidth, rotatedPreviewHeight,
                    maxPreviewWidth, maxPreviewHeight,
                    largest
                )

                // We fit the aspect ratio of TextureView to the size of preview we picked.
                if (activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    activity.cameraDevice.setAspectRatio(previewSize?.width!!, previewSize?.height!!)
                } else {
                    activity.cameraDevice.setAspectRatio(previewSize?.height!!, previewSize?.width!!)
                }

                // Check if the flash is supported.
                enableFlash =
                    characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true

                this.mCameraId = cameraId

                // We've found a viable camera and finished setting up member variables,
                // so we don't need to iterate through other available cameras.
                return
            }
        } catch (e: CameraAccessException) {
            Log.e("Camera", e.toString())
        } catch (e: NullPointerException) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            Log.e("Camera", e.toString())
        }

    }

    fun configureTransform(viewWidth: Int, viewHeight: Int, activity: CameraActivity) {
        val rotation = activity.windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f, previewSize?.height?.toFloat()!!, previewSize?.width?.toFloat()!!)
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()

        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            val scale = Math.max(
                viewHeight.toFloat() / previewSize?.height!!,
                viewWidth.toFloat() / previewSize?.width!!
            )
            with(matrix) {
                setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
                postScale(scale, scale, centerX, centerY)
                postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
            }
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180f, centerX, centerY)
        }
        activity.cameraDevice.setTransform(matrix)
    }

    fun updateCameraConfig(cameraId: String) {
        val characteristics = mCameraManager.getCameraCharacteristics(cameraId)
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
        previewSize = map!!.getOutputSizes(SurfaceTexture::class.java)[0]
    }

    fun previewPhoto() {
        isPreview = true
        assert(cameraDevice != null)
        if (mSession == null) {
            createCaptureSession(cameraDevice!!)
        } else {
            mSession?.capture(mPreviewRequest, cameraSessionCaptureCallBack, mHandler)
        }
    }

    fun takePhoto() {
        isTakePhoto = true
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
                    val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
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

    companion object {
        const val MESSAGE_SAVE_IMAGE = 1
        /**
         * Max preview width that is guaranteed by Camera2 API
         */
        private const  val MAX_PREVIEW_WIDTH = 1920

        /**
         * Max preview height that is guaranteed by Camera2 API
         */
        private const val MAX_PREVIEW_HEIGHT = 1080



        /***
         * @param choices 是根据Camera Manager 提供的对应CameraId 的CameraCharacteristics.从CameraCharacteristics中,
         * 提取可用的TextureViewSurface 的Size(包含width 和 height 信息)的Collections.
         */
        @JvmStatic
        private fun chooseOptimalSize(
            choices: Array<Size>,
            textureViewWidth: Int,
            textureViewHeight: Int,
            maxWidth: Int,
            maxHeight: Int,
            aspectRatio: Size
        ): Size {

            // Collect the supported resolutions that are at least as big as the preview Surface
            val bigEnough = ArrayList<Size>()
            // Collect the supported resolutions that are smaller than the preview Surface
            val notBigEnough = ArrayList<Size>()
            val w = aspectRatio.width
            val h = aspectRatio.height
            for (option in choices) {
                if (option.width <= maxWidth && option.height <= maxHeight &&
                    option.height == option.width * h / w
                ) {
                    if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                        bigEnough.add(option)
                    } else {
                        notBigEnough.add(option)
                    }
                }
            }

            // Pick the smallest of those big enough. If there is no one big enough, pick the
            // largest of those not big enough.
            if (bigEnough.size > 0) {
                return Collections.min(bigEnough, CompareSizesByArea())
            } else if (notBigEnough.size > 0) {
                return Collections.max(notBigEnough, CompareSizesByArea())
            } else {
                Log.e("Camera", "Couldn't find any suitable preview size")
                return choices[0]
            }
        }
    }
}


class ImageListener(val camera: Camera) : ImageReader.OnImageAvailableListener {
    override fun onImageAvailable(reader: ImageReader?) {
        var image = reader?.acquireLatestImage()
        Log.d("Camera", "--save image-->" + image?.planes?.size)

        val buffers = image?.planes?.get(0)?.buffer
        camera.mCurrentImage = buffers
        val msg = Message.obtain()
        msg.what = Camera.MESSAGE_SAVE_IMAGE
        camera.mHandler?.sendMessage(msg)
        camera.cameraOpenCloseLock.acquire()
//        Okio.buffer(buffers)
    }
}



/**
 * 比较工具类
 */
internal class CompareSizesByArea : Comparator<Size> {

    // We cast here to ensure the multiplications won't overflow
    override fun compare(lhs: Size, rhs: Size) =
        java.lang.Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)

}