package com.abrila.androidjetpackcompose.di

import com.abrila.androidjetpackcompose.data.DrinkRepository

object Injection {
    fun provideRepository(): DrinkRepository {
        return DrinkRepository.getInstance()
    }
}