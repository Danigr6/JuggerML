package ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    var navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var tituloVentana = if (currentRoute == Screen.Login.route) Screen.Login.title
    else if (currentRoute == Screen.Register.route) Screen.Register.title
    else if (currentRoute == Screen.Home.route) Screen.Home.title
    else Screen.Settings.title

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }
    JuggerMLTheme {
        Scaffold(topBar = {
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
            NavHost(
                startDestination = startDestination,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }, onRegisterNewAccount = {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    })
                }
                composable(Screen.Register.route) {
                    RegisterScreen(onRegisterSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                    }, onRegisterFailed = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                    })
                }

                composable(Screen.Home.route) {
                    HomeScreen(onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                        FirebaseAuth.getInstance().signOut()
                    }, onSettings = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    })
                }

                composable(Screen.Settings.route) {
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