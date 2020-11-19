package com.aiyakeji.mytest.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.utils.rxjava.CwqObservable
import com.aiyakeji.mytest.utils.rxjava.CwqObservableOnSubscribe
import com.aiyakeji.mytest.utils.rxjava.CwqObserver
import org.checkerframework.common.reflection.qual.NewInstance

/**
 * @author CWQ
 * @date 2020/10/20
 */
class HackerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hacker)


        initData()
    }


    private fun initData() {
        CwqObservable.create(object : CwqObservableOnSubscribe<Int> {
            override fun setObserver(observer: CwqObserver<Int>) {
                observer.onNext(10)
            }
        }).map { item ->
            "这是map转换后的数据：$item"
        }.setObserver(object : CwqObserver<String> {
            override fun onSubscribe() {
                println("onSubscribe")
            }

            override fun onNext(item: String) {
                println("onNext:$item")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }
        })
    }
}