package com.aiyakeji.mytest.utils.rxjava

/**
 * @author CWQ
 * @date 2020/10/22
 * 被观察者
 */
class CwqObservable<T> constructor() {

    private var source: CwqObservableOnSubscribe<T>? = null

    constructor(source: CwqObservableOnSubscribe<T>) : this() {
        this.source = source
    }

    companion object {
        fun <T> create(source: CwqObservableOnSubscribe<T>): CwqObservable<T> {
            return CwqObservable(source)
        }
    }

    fun setObserver(downStream: CwqObserver<T>) {
        downStream.onSubscribe()
        source?.setObserver(downStream)
    }


    fun <R> map(func: (T) -> R): CwqObservable<R> {
        val map = CwqMapObservable(this.source!!,func)
        return CwqObservable(map)
    }
}