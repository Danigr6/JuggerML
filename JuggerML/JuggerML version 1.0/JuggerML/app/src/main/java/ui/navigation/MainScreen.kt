package ui.navigation

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
import androidx.navigation.compose.rememberNavController
import com.example.juggerml.ui.theme.JuggerMLTheme
import ui.screens.HomeScreen
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.screens.SettingsScreen

@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var navController = rememberNavController()
    var currentScreen = rememberSaveable { mutableStateOf(Screen.Login.route) }

    var tituloVentana = if (currentScreen.value == Screen.Login.route) Screen.Login.title
    else if (currentScreen.value == Screen.Register.route) Screen.Register.title
    else if (currentScreen.value == Screen.Home.route) Screen.Home.title
    else Screen.Settings.title
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
                startDestination = Screen.Login.route,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(onLoginSuccess =  {
                        currentScreen.value = Screen.Login.route
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }, onRegisterNewAccount = {
                        currentScreen.value = Screen.Login.route
                        navController.navigate(Screen.Register.route){
                            popUpTo(Screen.Login.route) {inclusive = true}
                        }
                    })
                }
                composable(Screen.Register.route) {
                    RegisterScreen {
                        currentScreen.value = Screen.Register.route
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                    }
                }

                composable(Screen.Home.route) {
                    HomeScreen(onLogout = {
                        currentScreen.value = Screen.Login.route
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    }, onSettings = {
                        currentScreen.value = Screen.Settings.route
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    })
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(onSettingsOut = {
                        currentScreen.value = Screen.Home.route
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
