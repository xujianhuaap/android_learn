package com.skullmind.io.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.skullmind.io.Net
import com.skullmind.io.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

private const val CASE_ZIP = 0

private const val CASE_AMB = 1

private const val CASE_JUST = 2

private const val CASE_CONCAT = 4

private const val CASE_MERGE = 5

private const val CASE_MAP = 6

private const val CASE_FILTER = 7

private const val CASE_WINDOW = 8


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            functionDescButton()
        }

    }

    @Composable
    fun functionDescButton() {
        Button(onClick = { testRxjava() }) {
            Text(text = "test rxjava")
        }
    }


    private fun testRxjava() {
        when (CASE_FILTER) {
            CASE_ZIP ->
                zipExample()
            CASE_AMB ->
                ambExample()
            CASE_JUST ->
                justExample()
            CASE_CONCAT ->
                concatExample()
            CASE_MERGE ->
                mergeExample()
            CASE_MAP ->
                mapExample()
            CASE_FILTER ->
                filterExample()
            CASE_WINDOW ->
                windowExample()
            else ->
                zipExample()
        }
    }

    private fun windowExample() {
        Observable.merge(listOf(getInfoObservable(), getInfoObservable("xujianhuaap")))
            .window(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{


            }
    }

    private fun filterExample() {
        val info = getInfoObservable()
        val infoForXu = getInfoObservable("xujianhuaap")
        Observable.merge(listOf(info, infoForXu)).filter {
            it.get("login").asString != "xujianhuaap"
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
    }

    /***
     * map 转换
     */

    private fun mapExample() {
        getInfoObservable("xujianhuaap").concatMap<String> {
            Observable.just(it.get("login").asString)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Log.d("-->", "$it")
        }
    }

    /***
     * 多个操作异步并行执行，那个先返回先执行，
     */
    private fun mergeExample() {
        val info = getInfoObservable()

        val infoForXu = getInfoObservable("xujianhuaap")

        Observable.merge(listOf(info, infoForXu)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
    }

    private fun justExample() {
        Observable.just(JsonObject()).subscribe(MyObserver())
    }

    /***
     * 操作结果按照操作的发起先后顺序执行
     */
    private fun concatExample() {
        val info = getInfoObservable()
        val infoForXu = getInfoObservable("xujianhuaap")
        Observable.concat(listOf(info, infoForXu)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
    }

    /**
     * 只接受一个异步操作，其他的会遗弃不再处理
     */
    private fun ambExample() {
        val info = getInfoObservable()
        val infoForXu = getInfoObservable("xujianhuaap")
        Observable.amb(listOf(info, infoForXu)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
    }

    /***
     * 若干个异步操作，返回的数据流， 重新组装为新的数据
     */
    private fun zipExample() {
        val info1 = getInfoObservable()
        val repos1 = getRepoObservable()
        Observable.zip(info1, repos1, this::zipOperator, true).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(MyObserver())
    }


    private fun zipOperator(info: JsonObject, repo: JsonArray): JsonObject = JsonObject().apply {
        this.add("info", info)
        this.add("repo", repo)
    }


    private fun getRepoObservable(name: String = "defunkt"): @NonNull Observable<JsonArray> {
        return Net.createService(User::class.java).getRepos(name).subscribeOn(Schedulers.io())
    }

    private fun getInfoObservable(name: String = "defunkt"): @NonNull Observable<JsonObject> {
        return Net.createService(User::class.java).getInfo(name).subscribeOn(Schedulers.io())
    }

}

class MyObserver : Observer<JsonObject> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: JsonObject) {
        Log.d("-->", "MyObserver:success ->" + t)
    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {
        Log.d("-->", "MyObserver:onComplete ->" + Thread.currentThread().name)
    }
}
