package com.skullmind.io.main

import javax.inject.Inject

class MainVM @Inject constructor(
    private val model: MainModel,
    private val navigation: MainNavigation
) {
    fun clickContent1(){
        navigation.jumpToFlowActivity()
    }

    fun clickContent2(){
        navigation.jumpToConstraintBasicActivity()
    }

    fun clickContent3(){
        navigation.jumpToMotionActivity()
    }
}