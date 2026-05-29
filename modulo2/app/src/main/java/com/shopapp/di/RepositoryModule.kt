// di/RepositoryModule.kt
package com.shopapp.di

import com.shopapp.data.repository.AuthRepositoryImpl
import com.shopapp.data.repository.CategoryRepositoryImpl
import com.shopapp.domain.repository.AuthRepository
import com.shopapp.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}