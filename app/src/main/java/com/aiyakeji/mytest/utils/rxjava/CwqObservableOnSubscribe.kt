package com.aiyakeji.mytest.utils.rxjava

/**
 * @author CWQ
 * @date 2020/10/22
 */
interface CwqObservableOnSubscribe<T> {

    fun setObserver(observer: CwqObserver<T>)
}