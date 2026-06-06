// di/RepositoryModule.kt
package com.transportapp.di

import com.transportapp.data.repository.*
import com.transportapp.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton abstract fun bindAuthRepository   (impl: AuthRepositoryImpl   ): AuthRepository
    @Binds @Singleton abstract fun bindRouteRepository  (impl: RouteRepositoryImpl  ): RouteRepository
    @Binds @Singleton abstract fun bindVehicleRepository(impl: VehicleRepositoryImpl): VehicleRepository
    @Binds @Singleton abstract fun bindTripRepository   (impl: TripRepositoryImpl   ): TripRepository
    @Binds @Singleton abstract fun bindDriverRepository (impl: DriverRepositoryImpl ): DriverRepository
}
