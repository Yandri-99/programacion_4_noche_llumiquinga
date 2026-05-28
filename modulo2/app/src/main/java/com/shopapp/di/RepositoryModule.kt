// di/RepositoryModule.kt
package com.shopapp.di

import com.shopapp.data.repository.CategoryRepositoryImpl
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
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl,
    ): CategoryRepository

    // Los demás repositorios se añaden en los módulos siguientes
}