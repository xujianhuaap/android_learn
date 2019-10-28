package com.skullmind.io.camera

enum class CameraState(val value:Int){
    STATE_UNOPEN(-1),
    STATE_OPENED(0),
    STATE_PREVIEW(3),
    STATE_LOCK(1),
    STATE_UNLOCK(2)
}