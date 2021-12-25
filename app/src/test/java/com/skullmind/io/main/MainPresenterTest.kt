package com.skullmind.io.main

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import com.skullmind.io.R
import com.skullmind.io.github.GitHubActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowApplication
import java.util.regex.Pattern

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [27])
class MainPresenterTest{
    @Before
    fun setUp(){

    }

    @Test
    fun clickToJumpGithub(){
        ActivityScenario.launch(PersonActivity::class.java).onActivity {
            it.findViewById<TextView>(R.id.tv_jump).performClick()
            val expect = Intent(it,GitHubActivity::class.java)
            val actual = ShadowApplication.getInstance().nextStartedActivity
            assertEquals(expect.component,actual.component)
        }
    }

    @Test
    fun clickTitle(){
        ActivityScenario.launch(PersonActivity::class.java).onActivity {
            val tvClickCount = it.findViewById<TextView>(R.id.tv_click_count)
            val btnTitle = it.findViewById<Button>(R.id.tv_title)
            assert(tvClickCount.visibility == View.VISIBLE)
            val charSequence = tvClickCount.text
            btnTitle.performClick()
            val actual = get_ckick_count_from_str(tvClickCount.text).toInt()
            val expect = get_ckick_count_from_str(charSequence).toInt().plus(1)
            assertEquals(expect,actual)

        }
    }

    fun get_ckick_count_from_str(source:CharSequence):String{
        val pattern = Pattern.compile("\\D+(\\d+)\\D+")
        val matcher = pattern.matcher(source)
        val isFind = matcher.find ()

        var result = ""
        if(isFind){
           result =  matcher.group(1)
        }
        return result
    }
}