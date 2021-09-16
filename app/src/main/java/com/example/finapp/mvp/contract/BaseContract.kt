package com.example.finapp.mvp.contract

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Это базовый класс, в нем определен абстрактный презентер и его методы —
 * подписка/отписка на поток данных, прикрепление/открепление View.
 */
class BaseContract {

    interface View

    abstract class Presenter<V: View> {
        private val subscriptions = CompositeDisposable()
        protected lateinit var view: V


        fun subscribe(subscription: Disposable) {
            subscriptions.add(subscription)
        }

        fun unsubscribe() {
            subscriptions.clear()
        }

        fun attach(view: V) {
            this.view = view
        }

        fun detach() {
            unsubscribe()
        }

    }
}