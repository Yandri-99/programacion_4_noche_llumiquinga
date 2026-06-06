package com.transportapp.presentation.navigation

sealed class Screen(val route: String) {
    // Auth
    data object Login    : Screen("login")
    data object Register : Screen("register")

    // Public
    data object Home         : Screen("home")
    data object Catalog      : Screen("catalog")
    data class  Vehicle(val id: Int = 0) : Screen("vehicle/{id}") {
        fun createRoute(id: Int) = "vehicle/$id"
    }
    data object Reservations : Screen("reservations")

    // Client
    data object MyTrips          : Screen("my_trips")
    data class  TripDetail(val id: Int = 0) : Screen("my_trips/{id}") {
        fun createRoute(id: Int) = "my_trips/$id"
    }
    data object Profile : Screen("profile")

    // Admin
    data object AdminDashboard : Screen("admin")
    data object AdminRoutes    : Screen("admin/routes")
    data object AdminVehicles  : Screen("admin/vehicles")
    data object AdminTrips     : Screen("admin/trips")
    data object AdminDrivers   : Screen("admin/drivers")
}
