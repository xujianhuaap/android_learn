package com.skullmind.io.main

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.skullmind.io.R
import com.skullmind.io.data.bean.GitHub
import dagger.android.AndroidInjection
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainView{
    @BindView(R.id.tv_content)
    lateinit var tvContent:TextView

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.tv_content) fun clickContent(){
        presenter.showMessage("Hello Dagger")
        presenter.requestGithubService{
            refreshContentView(it?.code_search_url)
        }
    }

    override fun initView() {
        tvContent.setText("hello dagger")
    }

    override fun refreshContentView(content: String?) {
        tvContent.setText(content)
    }

}
