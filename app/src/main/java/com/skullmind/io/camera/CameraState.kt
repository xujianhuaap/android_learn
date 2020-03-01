package com.skullmind.io.camera

enum class CameraState(val value:Int){
    STATE_UNOPEN(-1),
    STATE_OPENED(0),
    STATE_PREVIEW(3),
    STATE_CAPTURE_LOCK(1),
    STATE_CAPTURE_UNLOCK(2)
}