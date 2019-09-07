package com.skullmind.io.main

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.skullmind.io.R
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
        presenter.requestGithubService(object:retrofit2.Callback<GitHub>{
            override fun onFailure(call: Call<GitHub>, t: Throwable) {
                Log.d(MainActivity::class.simpleName,"githubservice -> "+t?.message)
            }

            override fun onResponse(call: Call<GitHub>, response: Response<GitHub>) {
                Log.d(MainActivity::class.simpleName,"githubservice -> "+response.body()?.code_search_url)
                presenter.refreshContentView(response.body()?.authorizations_url)
            }
        })
    }

    override fun initView() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshContentView(content: String?) {
        tvContent.setText(content)
    }

}
