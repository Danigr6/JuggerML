package ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.juggerml.ui.theme.JuggerMLTheme
import com.google.firebase.auth.FirebaseAuth
import ui.screens.HomeScreen
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.screens.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Inicializamos el controlador de navegación (NavController) para gestionar las pantallas
    var navController = rememberNavController()

    // Obtenemos la entrada actual en la pila de navegación para saber en qué ruta estamos
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determinamos dinámicamente el título de la barra superior (TopBar) según la ruta actual
    var tituloVentana = if (currentRoute == Screen.Login.route) Screen.Login.title
    else if (currentRoute == Screen.Register.route) Screen.Register.title
    else if (currentRoute == Screen.Home.route) Screen.Home.title
    else Screen.Settings.title

    // Comprobación de sesión activa: si el usuario ya está logueado en Firebase,
    // la app arranca directamente en el Home; si no, va a la pantalla de Login.
    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }

    JuggerMLTheme {
        Scaffold(topBar = {
            // Barra superior fija que muestra el título de la pantalla actual
            TopAppBar(
                title = {
                    Text(
                        stringResource(tituloVentana),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }) { innerPadding ->
            // Contenedor de navegación (NavHost) que define las rutas y transiciones entre pantallas
            NavHost(
                startDestination = startDestination,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Ruta de Inicio de Sesión (Login)
                composable(Screen.Login.route) {
                    LoginScreen(onLoginSuccess = {
                        // Si el login es exitoso, navegamos al Home y borramos el Login del historial (popUpTo)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }, onRegisterNewAccount = {
                        // Si pulsa registrarse, vamos a la pantalla de Registro limpiando el Login
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    })
                }

                // Ruta de Registro de cuenta nueva
                composable(Screen.Register.route) {
                    //Si nos registramos con éxito, nos desplazamos a la pantalla de Login para iniciar sesión
                    RegisterScreen(onRegisterSuccess = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                        //Si el register no es válido, nos desplazamos a la pantalla de login sin crear cuenta
                    }, onRegisterFailed = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                    }, onRegisterOut = {
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.Register.route) {
                                inclusive = true}
                        }
                    })
                }

                // Ruta principal (Home) de la app
                composable(Screen.Home.route) {
                    HomeScreen(onLogout = {
                        // Al cerrar sesión, limpiamos la pila, volvemos al Login y cerramos sesión en Firebase
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                        FirebaseAuth.getInstance().signOut()
                    }, onSettings = {
                        // Navegamos a la pantalla de ajustes
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    })
                }

                // Ruta de Ajustes (Settings)
                composable(Screen.Settings.route) {
                    //Si pulsamos en salir de ajustes, volvemos a la pantalla de Home
                    SettingsScreen(onSettingsOut = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Settings.route) {
                                inclusive = true
                            }
                        }
                    })
                }
            }
        }
    }
}