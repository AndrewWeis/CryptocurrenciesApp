package com.example.finapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * AppModule предоставляет контекст для доступа к ресурсам в любой части приложения
 * Класс помечен аннотацией @Module, сообщающей Даггеру, что функции этого класса предоставляют зависимости.
 * Функция provideContext() помечена аннотацией @Provides как раз для этой цели.
 *
 * @Singletone означает, что Даггер при инициализации компонента создаст единственный экземпляр помеченной зависимости,
 * то есть синглтон. И при каждом затребовании данной зависимости будет предоставлять этот единственный экземпляр.
 * Это позволит избежать ненужного пересоздания объектов, утечек памяти и других проблем.
 */

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

}