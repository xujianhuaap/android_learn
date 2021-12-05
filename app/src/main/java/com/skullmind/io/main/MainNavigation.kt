package com.skullmind.io.main

import com.skullmind.io.constraint.toConstraintBasicActivity
import com.skullmind.io.constraint.toFlowActivity
import com.skullmind.io.constraint.toMotionActivity

class MainNavigation(val activity:MainActivity) {
    fun jumpToFlowActivity() = toFlowActivity(activity)
    fun jumpToConstraintBasicActivity() = toConstraintBasicActivity(activity)
    fun jumpToMotionActivity() = toMotionActivity(activity)
}