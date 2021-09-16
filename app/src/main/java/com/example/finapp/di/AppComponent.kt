package com.example.finapp.di

import com.example.finapp.activities.MainActivity
import com.example.finapp.fragments.CurrenciesListFragment
import com.example.finapp.mvp.presenter.CurrenciesPresenter
import com.example.finapp.mvp.presenter.LatestChartPresenter
import dagger.Component
import javax.inject.Singleton


/**
 * Данной аннотацией мы говорим Даггеру, что AppComponent содержит четыре модуля: AppModule, ChartModule, MvpModule и RestModule.
 * Зависимости, которые провайдит каждый из этих модулей, доступны для всех остальных модулей, объединенных в компоненте AppComponent.
 */

@Component(modules = [AppModule::class, RestModule::class, MvpModule::class, ChartModule::class])
@Singleton
interface AppComponent {

    /** Функция inject(mainActivity: MainActivity) сообщает Даггеру класс, в который мы хотим внедрять зависимости. */
    fun inject(mainActivity: MainActivity)

    fun inject(presenter: CurrenciesPresenter)
    fun inject(presenter: LatestChartPresenter)
    fun inject(fragment: CurrenciesListFragment)

}