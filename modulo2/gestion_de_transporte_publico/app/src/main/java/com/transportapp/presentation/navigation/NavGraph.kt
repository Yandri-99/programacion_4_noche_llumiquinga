package com.transportapp.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.ui.admin.AdminScaffold
import com.transportapp.presentation.ui.routes.RoutesAdminScreen
import com.transportapp.presentation.ui.dashboard.DashboardScreen
import com.transportapp.presentation.ui.trips.TripAdminDetailScreen
import com.transportapp.presentation.ui.trips.TripsAdminScreen
import com.transportapp.presentation.ui.vehicles.VehiclesAdminScreen
import com.transportapp.presentation.ui.drivers.DriversAdminScreen
import com.transportapp.presentation.ui.auth.LoginScreen
import com.transportapp.presentation.ui.auth.RegisterScreen
import com.transportapp.presentation.ui.trips.TripDetailScreen
import com.transportapp.presentation.ui.trips.TripsScreen
import com.transportapp.presentation.ui.profile.ProfileScreen
import com.transportapp.presentation.ui.cart.ReservationBottomSheet
import com.transportapp.presentation.ui.uipublic.catalog.CatalogScreen
import com.transportapp.presentation.ui.uipublic.home.HomeScreen
import com.transportapp.presentation.ui.vehicles.VehicleDetailScreen
import com.transportapp.presentation.viewmodel.AuthViewModel
import com.transportapp.presentation.viewmodel.CartViewModel
import com.transportapp.presentation.viewmodel.TripsAdminViewModel
import com.transportapp.theme.Surface
import com.transportapp.theme.TextSecondary

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel = hiltViewModel(),
) {
    val navController     = rememberNavController()
    val isCheckingSession by authViewModel.isCheckingSession.collectAsState()
    val isAuthenticated   by authViewModel.isAuthenticated.collectAsState()
    val isStaff           by authViewModel.isStaff.collectAsState()
    val cartCount         by cartViewModel.totalItems.collectAsState()
    val currentUser       by authViewModel.currentDriver.collectAsState()

    var showReservations by remember { mutableStateOf(false) }
    var confirmedTripId  by remember { mutableStateOf<Int?>(null) }

    if (isCheckingSession) {
        LoadingScreen("Iniciando TransportApp...")
        return
    }

    val startDestination = when {
        !isAuthenticated -> Screen.Login.route
        isStaff          -> Screen.AdminDashboard.route
        else             -> Screen.Home.route
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Catalog.route,
        Screen.MyTrips.route,
        Screen.Profile.route,
    )

    Scaffold(
        containerColor = Surface,
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    navController = navController,
                    cartCount     = cartCount,
                    onReservationsClick = { showReservations = true },
                )
            }
        },
    ) { innerPadding ->

        if (showReservations) {
            ReservationBottomSheet(
                cartViewModel  = cartViewModel,
                isAuthenticated = isAuthenticated,
                onDismiss       = { showReservations = false },
                onLoginRequired = {
                    showReservations = false
                    navController.navigate(Screen.Login.route)
                },
                onTripSuccess = { tripId ->
                    confirmedTripId = tripId
                    showReservations = false
                },
            )
        }

        NavHost(
            navController    = navController,
            startDestination = startDestination,
            modifier         = Modifier.padding(innerPadding),
        ) {

            // ── LOGIN ───────────────────────────────
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        if (isStaff) {
                            navController.navigate(Screen.AdminDashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                    viewModel            = authViewModel,
                )
            }

            // ── REGISTER ────────────────────────────
            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = {
                        if (isStaff) {
                            navController.navigate(Screen.AdminDashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() },
                    viewModel         = authViewModel,
                )
            }

            // ── HOME ───────────────────────────────
            composable(Screen.Home.route) {
                HomeScreen(
                    onVehicleClick = { id -> navController.navigate("vehicle/$id") },
                    onCatalogClick = { navController.navigate(Screen.Catalog.route) },
                )
            }

            // ── CATALOG ────────────────────────────
            composable(Screen.Catalog.route) {
                CatalogScreen(
                    onVehicleClick = { id -> navController.navigate("vehicle/$id") },
                )
            }

            // ── VEHICLE DETAIL ─────────────────────
            composable(
                route     = "vehicle/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                VehicleDetailScreen(
                    vehicleId   = id,
                    onBack      = { navController.popBackStack() },
                    cartViewModel = cartViewModel,
                )
            }

            // ── MY TRIPS ───────────────────────────
            composable(Screen.MyTrips.route) {
                if (!isAuthenticated) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                } else {
                    TripsScreen(
                        onTripClick = { id -> navController.navigate("my_trips/$id") },
                    )
                }
            }

            // ── TRIP DETAIL CLIENT ─────────────────
            composable(
                route     = "my_trips/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                TripDetailScreen(
                    tripId = id,
                    onBack = { navController.popBackStack() },
                )
            }

            // ── PROFILE ────────────────────────────
            composable(Screen.Profile.route) {
                if (!isAuthenticated) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                } else {
                    ProfileScreen(
                        authViewModel = authViewModel,
                        onLogout = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                    )
                }
            }

            // ── ADMIN DASHBOARD ────────────────────
            composable(Screen.AdminDashboard.route) {
                if (!isStaff) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route) { popUpTo(0) }
                    }
                    return@composable
                }

                AdminScaffold(
                    currentRoute = Screen.AdminDashboard.route,
                    user         = currentUser,
                    title        = "Dashboard",
                    onNavClick   = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState    = true
                        }
                    },
                    onStoreClick = { navController.navigate(Screen.Home.route) },
                    onLogout     = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        DashboardScreen(
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                }
            }

            // ── ADMIN ROUTES ───────────────────────
            composable("admin/routes") {
                if (!isStaff) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route) { popUpTo(0) }
                    }
                    return@composable
                }

                AdminScaffold(
                    currentRoute = "admin/routes",
                    user         = currentUser,
                    title        = "Rutas",
                    onNavClick   = { route ->
                        navController.navigate(route) { launchSingleTop = true }
                    },
                    onStoreClick = { navController.navigate(Screen.Home.route) },
                    onLogout     = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        RoutesAdminScreen()
                    }
                }
            }

            // ── ADMIN VEHICLES ─────────────────────
            composable("admin/vehicles") {
                if (!isStaff) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route) { popUpTo(0) }
                    }
                    return@composable
                }

                AdminScaffold(
                    currentRoute = "admin/vehicles",
                    user         = currentUser,
                    title        = "Vehículos",
                    onNavClick   = { route ->
                        navController.navigate(route) { launchSingleTop = true }
                    },
                    onStoreClick = { navController.navigate(Screen.Home.route) },
                    onLogout     = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        VehiclesAdminScreen()
                    }
                }
            }

            // ── ADMIN TRIPS ────────────────────────
            composable("admin/trips") {
                if (!isStaff) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route) { popUpTo(0) }
                    }
                    return@composable
                }

                val tripsAdminVm: TripsAdminViewModel = hiltViewModel()

                AdminScaffold(
                    currentRoute = "admin/trips",
                    user         = currentUser,
                    title        = "Viajes",
                    onNavClick   = { route ->
                        navController.navigate(route) { launchSingleTop = true }
                    },
                    onStoreClick = { navController.navigate(Screen.Home.route) },
                    onLogout     = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        TripsAdminScreen(
                            onTripDetail = { id ->
                                navController.navigate("admin/trips/$id")
                            },
                            viewModel = tripsAdminVm,
                        )
                    }
                }
            }

            // ── ADMIN TRIP DETAIL ──────────────────
            composable(
                route     = "admin/trips/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable

                if (!isStaff) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route) { popUpTo(0) }
                    }
                    return@composable
                }

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("admin/trips")
                }

                val tripsAdminVm: TripsAdminViewModel = hiltViewModel(parentEntry)

                AdminScaffold(
                    currentRoute = "admin/trips",
                    user         = currentUser,
                    title        = "Detalle viaje #$id",
                    onNavClick   = { route ->
                        navController.navigate(route) { launchSingleTop = true }
                    },
                    onStoreClick = { navController.navigate(Screen.Home.route) },
                    onLogout     = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        TripAdminDetailScreen(
                            tripId  = id,
                            onBack  = { navController.popBackStack() },
                            onStatusChange = { tripId, newStatus ->
                                tripsAdminVm.changeStatus(tripId, newStatus)
                            },
                        )
                    }
                }
            }

            // ── ADMIN DRIVERS ──────────────────────
            composable("admin/drivers") {
                if (!isStaff) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route) { popUpTo(0) }
                    }
                    return@composable
                }

                AdminScaffold(
                    currentRoute = "admin/drivers",
                    user         = currentUser,
                    title        = "Conductores",
                    onNavClick   = { route ->
                        navController.navigate(route) { launchSingleTop = true }
                    },
                    onStoreClick = { navController.navigate(Screen.Home.route) },
                    onLogout     = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        DriversAdminScreen()
                    }
                }
            }
        }
    }
}
