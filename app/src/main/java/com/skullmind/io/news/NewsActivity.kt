package com.skullmind.io.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity.CENTER_VERTICAL
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skullmind.io.R
import kotlinx.coroutines.*


fun newIntentToNewsActivity(context: AppCompatActivity): Intent {
    return Intent(context, NewsActivity::class.java)
}

class NewsActivity : AppCompatActivity() {
    private val vm: NewsVM by lazy {
        NewsVM()
    }

    private val contentView: ContentView by lazy {
        ContentView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        vm.newsTitle.observe(this) {
            contentView.refreshTitle(it)
        }

        vm.newsContent.observe(this) {
            contentView.refreshContent(it)
        }
        vm.getNews()
    }
}

class ContentView(context: Context) : ConstraintLayout(context) {
    private val titleView = TextView(context).apply {
        id = generateViewId()
        gravity = CENTER_VERTICAL
        text = " 123"
        textSize = 20f
    }

    private val contentView = TextView(context).apply {
        id = generateViewId()
        text = ""
        textSize = 22f
    }

    private val switch = CheckBox(context).apply {
        id = generateViewId()
        setOnCheckedChangeListener { _, isChecked ->
            contentView.visibility = if (isChecked) VISIBLE else GONE
        }
    }

    init {
        addView(titleView)
        addView(contentView)
        addView(switch)
        ConstraintSet().apply {
            connect(titleView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(titleView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            constrainMinHeight(titleView.id, resources.getDimensionPixelOffset(R.dimen.dimen_50_dp))
            constrainPercentWidth(titleView.id, 0.9f)

            connect(switch.id, ConstraintSet.START, titleView.id, ConstraintSet.END)
            connect(switch.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(switch.id, ConstraintSet.TOP, titleView.id, ConstraintSet.TOP)
            connect(switch.id, ConstraintSet.BOTTOM, titleView.id, ConstraintSet.BOTTOM)

            connect(
                contentView.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(contentView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(contentView.id, ConstraintSet.TOP, titleView.id, ConstraintSet.BOTTOM)
            connect(
                contentView.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constrainMinHeight(
                contentView.id,
                resources.getDimensionPixelOffset(R.dimen.dimen_50_dp)
            )

            applyTo(this@ContentView)
        }


    }

    fun refreshTitle(title: String = "default") {
        titleView.text = title
    }

    fun refreshContent(content: String = "default") {
        contentView.text = content
    }
}

class NewsVM : ViewModel() {
    val newsTitle by lazy {
        MutableLiveData<String>()
    }

    val newsContent by lazy {
        MutableLiveData<String>()
    }

    fun getNews() {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(10) {
                delay(500)
                val news = NewsModel("南航日报", "南航欢迎你 $it")
                withContext(Dispatchers.Main) {
                    newsTitle.value = news.title
                    newsContent.value = news.content
                }
            }
        }
    }
}

data class NewsModel(val title: String, val content: String)