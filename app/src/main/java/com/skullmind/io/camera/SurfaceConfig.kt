package com.skullmind.io.camera

import android.graphics.*
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Log
import android.util.Size
import android.view.Surface
import java.util.*
import kotlin.math.max

class  SurfaceConfig(private val mCameraManager: CameraManager) {
    var previewSize: Size? = null

    fun initFrontCamera(width: Int, height: Int, activity: CameraActivity, success:(CameraConfig, Size, Size?)->Unit) {
        try {
            for (cameraId in mCameraManager.cameraIdList) {
                val characteristics = mCameraManager.getCameraCharacteristics(cameraId)

                // We don't use a front facing camera in this sample.
                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (cameraDirection != null &&
                    cameraDirection == CameraCharacteristics.LENS_FACING_FRONT
                ) continue

                val map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
                ) ?: continue

                // For still image captures, we use the largest available size.
                val largest = Collections.max(
                    listOf(*map.getOutputSizes(ImageFormat.JPEG)),
                    CompareSizesByArea()
                )
                updatePreviewSize(activity, characteristics, height, width, map, largest)
                // Check if the flash is supported.
                val enableFlash =
                    characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
                success(CameraConfig(cameraId,enableFlash),largest,previewSize)
            }
        } catch (e: CameraAccessException) {
            Log.e("Camera", e.toString())
        } catch (e: NullPointerException) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            Log.e("Camera", e.toString())
        }


    }

    private fun updatePreviewSize(
        activity: CameraActivity,
        characteristics: CameraCharacteristics,
        height: Int,
        width: Int,
        map: StreamConfigurationMap,
        largest: Size
    ) {
        // 0 屏幕未旋转,90 屏幕顺时针旋转90
        val displayRotation = activity.windowManager.defaultDisplay.rotation
        //[0,90,180,270]
        val sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)
        val swappedDimensions = areDimensionsSwapped(displayRotation, sensorOrientation!!)

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
    }



    fun obtainCameraViewTransform(viewWidth: Int, viewHeight: Int, activity: CameraActivity):Matrix {
        val rotation = activity.windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f, previewSize?.height?.toFloat()!!, previewSize?.width?.toFloat()!!)
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()

        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            val scale = max(
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
         return matrix
    }


    fun updateCameraConfig(cameraId: String){
        val characteristics = mCameraManager.getCameraCharacteristics(cameraId)
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
        previewSize = map.getOutputSizes(SurfaceTexture::class.java)[0]
    }

    companion object{
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
            return when {
                bigEnough.size > 0 -> Collections.min(bigEnough, CompareSizesByArea())
                notBigEnough.size > 0 -> Collections.max(notBigEnough, CompareSizesByArea())
                else -> {
                    Log.e("Camera", "Couldn't find any suitable preview size")
                    choices[0]
                }
            }
        }

        /***
         * 是否交换高度与宽度
         */
        private fun areDimensionsSwapped(displayRotation: Int, sensorOrientation: Int): Boolean {
            var swappedDimensions = false
            when (displayRotation) {
                Surface.ROTATION_0, Surface.ROTATION_180 -> {
                    if (sensorOrientation == 90 || sensorOrientation == 270) {
                        swappedDimensions = true
                    }
                }
                Surface.ROTATION_90, Surface.ROTATION_270 -> {
                    if (sensorOrientation == 0 || sensorOrientation == 180) {
                        swappedDimensions = true
                    }
                }
                else -> {
                    Log.e("Camera", "Display rotation is invalid: $displayRotation")
                }
            }
            return swappedDimensions
        }
    }

}