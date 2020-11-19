package com.aiyakeji.mytest.utils.rxjava

/**
 * @author CWQ
 * @date 2020/10/22
 * 观察者
 */
interface CwqObserver<T> {
    fun onSubscribe()
    fun onNext(item: T)
    fun onError(e: Throwable)
    fun onComplete()
}