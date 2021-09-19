package com.example.finapp.mvp.presenter

import com.example.finapp.di.App
import com.example.finapp.adapter.CurrenciesAdapter
import com.example.finapp.formatThousands
import com.example.finapp.mvp.contract.CurrenciesContract
import com.example.finapp.rest.CoinGeckoApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Внедряем зависимость — источник данных CoinGeckoApi при помощи аннотации @Inject Даггера.
 * Инициализируем его компоненты в теле функции init.
 * Функция makeList() отображает прогрессбар и запускает Rx-цепочку подписки на данные и обработки их в процессе
 *
 * Функция subscribe принимает объект CoinGeckoApi, получающий данные от сервера посредством вызова функции getCoinMarket(),
 * которая возвращает Observable<List<GeckoCoin>>
 *
 * Оператор subscribeOn(Schedulers.io()) определяет отдельный поток для отправки данных. По умолчанию Observable отправляет
 * свои данные в поток, в котором была объявлена подписка, т. е. где вызван оператор .subscribe. В Android это, как правило,
 * основной поток пользовательского интерфейса. Но получение данных в основном потоке приведет к зависанию интерфейса.
 * Чтобы не блокировать основной поток, можно использовать оператор subscribeOn() для определения другого Scheduler
 * где Observable должен выполняться и излучать свои данные
 *
 * В свою очередь оператор observeOn() мы используем вместе с планировщиком AndroidSchedulers.mainThread() для того,
 * чтобы обрабатывать результат в основном потоке и показать список валют в пользовательском интерфейсе приложения.
 *
 * Далее оператор flatMap. Функция fromIterable получает список и создает из него Observable с отдельными элементами списка.
 * Таким образом из Observable<List<GeckoCoin>> мы получим Observable<GeckoCoin>. Оператор flatMap раскрывает получившийся
 * Observable<GeckoCoin> и отправляет его элементы далее в поток.
 *
 * Оператор doOnNext вызывается каждый раз, когда источник Observable излучает очередной объект GeckoCoin.
 * В этом операторе мы наполняем поля элемента списка данными из объекта GeckoCoin и отдаем их адаптеру.
 *
 * Оператор doOnComplete вызывается при событии onComplete(), которое происходит при успешном возврате данных,
 * в отличие от события onError(), которое бросает исключение.
 *
 * Завершает Rx-цепочку оператор subscribe, который, по сути, связывает Observable и Observer, подписывая
 * наблюдатель на поток данных. Здесь мы скрываем прогрессбар и оповещаем адаптер об изменениях списка.
 *
 * Функция refreshList() будет обновлять список, вызывая функцию создания списка снова.
 *
 */

class CurrenciesPresenter : CurrenciesContract.Presenter() {

    //внедряем источник данных
    @Inject
    lateinit var geckoApi: CoinGeckoApi

    //инициализируем компоненты Даггера
    init {
        App.appComponent.inject(this)
    }

    //создаем список, загружая данные с помощью RxJava
    override fun makeList() {
        view.showProgress()

        //подписываемся на поток данных
        subscribe(geckoApi.getCoinMarket()

            //определяем отдельный поток для отправки данных
            .subscribeOn(Schedulers.io())

            //получаем данные в основном потоке
            .observeOn(AndroidSchedulers.mainThread())

            //преобразуем List<GeckoCoin> в Observable<GeckoCoin>
            .flatMap { Observable.fromIterable(it) }

            //наполняем поля элемента списка для адаптера
            .doOnNext {
                view.addCurrency(
                    CurrenciesAdapter.Currency(
                        it.id,
                        it.symbol,
                        it.name,
                        it.image,
                        it.current_price,
                        it.market_cap.formatThousands(),
                        it.market_cap_rank,
                        it.total_volume,
                        it.price_change_percentage_24h,
                        it.market_cap_change_percentage_24h,
                        it.circulating_supply,
                        it.total_supply,
                        it.ath,
                        it.ath_change_percentage
                    )
                )
            }

            //вызывается при вызове onComplete
            .doOnComplete {
                view.hideProgress()
            }

            //подписывает Observer на Observable
            .subscribe({
                view.hideProgress()
                view.notifyAdapter()
            }, {
                view.showErrorMessage(it.message)
                view.hideProgress()
                it.printStackTrace()
            })
        )
    }


    //обновляем список
    override fun refreshList() {
        view.refresh()
        makeList()
    }
}