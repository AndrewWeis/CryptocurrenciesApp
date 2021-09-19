package com.example.finapp.mvp.presenter

import com.example.finapp.di.App
import com.example.finapp.rest.CoinGeckoApi
import com.example.finapp.mvp.contract.LatestChartContract

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Здесь все аналогично предыдущему презентеру. Единственное отличие — используется оператор map вместо flatMap, поскольку функция
 * geckoApi.getCoinMarketChart(id) возвращает не список, а Observable<GeckoCoinChart>, и нам не нужно его разворачивать.
 */

class LatestChartPresenter : LatestChartContract.Presenter() {

    @Inject
    lateinit var geckoApi: CoinGeckoApi


    init {
        App.appComponent.inject(this)
    }

    override fun makeChart(id: String) {

        subscribe(geckoApi.getCoinMarketChart(id)


            .map { it.prices }

            .flatMap { Observable.fromIterable(it) }

            .doOnComplete {

                view.hideProgress()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.hideProgress()
                view.addEntryToChart(it[0], it[1])

            }, {
                view.hideProgress()
                view.showErrorMessage(it.message)
                it.printStackTrace()
            })
        )

    }

    override fun refreshChart() {
        view.refresh()

    }

}