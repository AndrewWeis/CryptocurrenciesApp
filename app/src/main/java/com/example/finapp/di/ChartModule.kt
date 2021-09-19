package com.example.finapp.di

import com.example.finapp.chart.LatestChart
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Модуль ChartModule будет использоваться для работы с графиком
 */

@Module
class ChartModule {
    @Provides
    @Singleton
    fun provideLatestChart() = LatestChart()


    @Provides
    @Singleton
    fun provideYearFormatter() = com.example.finapp.formatters.YearValueFormatter()

    @Provides
    @Singleton
    fun valueFormatter() = com.example.finapp.formatters.MyXAxisFormatter()
}
