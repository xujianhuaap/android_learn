package com.skullmind.io.main

import com.skullmind.io.constraint.toConstraintBasicActivity
import com.skullmind.io.constraint.toFlowActivity

class MainNavigation(val activity:MainActivity) {
    fun jumpToFlowActivity() = toFlowActivity(activity)
    fun jumpToConstraintBasicActivity() = toConstraintBasicActivity(activity)
}