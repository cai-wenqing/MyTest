package com.aiyakeji.mytest.utils.rxjava

/**
 * @author CWQ
 * @date 2020/10/22
 */
class CwqMapObservable<T, R>(
        private val source: CwqObservableOnSubscribe<T>,
        private val func: (T) -> R
) : CwqObservableOnSubscribe<R> {

    override fun setObserver(downStream: CwqObserver<R>) {
        val map = CwqMapObserver(downStream, func)
        source.setObserver(map)
    }


    class CwqMapObserver<T, R>(
            private val downStream: CwqObserver<R>,
            private val func: (T) -> R
    ) : CwqObserver<T> {
        override fun onSubscribe() {
            downStream.onSubscribe()
        }

        override fun onNext(item: T) {
            val result = func.invoke(item)
            downStream.onNext(result)
        }

        override fun onError(e: Throwable) {
            downStream.onError(e)
        }

        override fun onComplete() {
            downStream.onComplete()
        }

    }
}