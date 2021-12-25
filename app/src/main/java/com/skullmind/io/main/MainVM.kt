package com.skullmind.io.main

import javax.inject.Inject

class MainVM @Inject constructor(
    private val navigation: MainNavigation
) {
    fun jumpToPersonActivity() {
        navigation.jumpToPersonActivity()
    }
}

interface MainNavigation {
    fun jumpToPersonActivity()
}