package com.example.finapp.mvp.contract

import com.example.finapp.adapter.CurrenciesAdapter


/**
 * Интерфейс View содержит функции:
 * 1) добавления новых валют в список,
 * 2) оповещения адаптера об изменениях списка,
 * 3) отображении /скрытии прогрессбара,
 * 4) отображения ошибки и функцию обновления.
 * Презентер содержит функции создания и обновления списка криптовалют
 */
class CurrenciesContract {
    interface View : BaseContract.View {
        fun addCurrency(currency: CurrenciesAdapter.Currency)
        fun notifyAdapter()
        fun showProgress()
        fun hideProgress()
        fun showErrorMessage(error: String?)
        fun refresh()
    }

    abstract class Presenter: BaseContract.Presenter<View>() {
        abstract fun makeList()
        abstract fun refreshList()
    }
}