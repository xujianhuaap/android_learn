package com.skullmind.io.main

import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import com.skullmind.io.R
import com.skullmind.io.github.GitHubActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowApplication

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class MainPresenterTest{
    @Before
    fun setUp(){

    }

    @Test
    fun clickToJumpGithub(){
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            it.findViewById<TextView>(R.id.tv_jump).performClick()
            val expect = Intent(it,GitHubActivity::class.java)
            val actual = ShadowApplication.getInstance().nextStartedActivity
            assertEquals(expect.component,actual.component)
        }


    }
}