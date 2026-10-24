package ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.juggerml.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Preview(showSystemUi = true)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterNewAccount: () -> Unit = {}
) {
    // Contexto actual de Android y estados locales para guardar el email y contraseña introducidos
    var context = LocalContext.current
    var userGmail by rememberSaveable { mutableStateOf("") }
    var userPassword by rememberSaveable { mutableStateOf("") }

    // --- DISEÑO DE LA INTERFAZ (UI) ---
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Image(
            painterResource(R.drawable.icono_juggerml),
            contentDescription = "icono app",
            modifier = Modifier.size(130.dp)
        )
        Spacer(Modifier.height(30.dp))

        // Campo de texto para el correo electrónico (Gmail)
        TextField(
            value = userGmail,
            label = { Text(stringResource(R.string.CampoGmail)) },
            singleLine = true,
            onValueChange = { userGmail = it.trim() })

        Spacer(Modifier.height(20.dp))

        // Campo de texto oculto para la contraseña
        TextField(
            value = userPassword,
            label = { Text(stringResource(R.string.CampoContraseña)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { userPassword = it })

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Botón tradicional de inicio de sesión con Correo y Contraseña
            Button(onClick = {
                if (userGmail.isNotEmpty() && userPassword.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(userGmail, userPassword)
                        .addOnSuccessListener {
                            onLoginSuccess()
                            Toast.makeText(context, "Sesión iniciada con éxito", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Usuario o contraseña incorrectos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(
                    stringResource(R.string.Boton_InicioSesion),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Texto interactivo para redirigir a la pantalla de registro
            Text(
                text = stringResource(R.string.Texto_RegistrarCuenta),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        onRegisterNewAccount()
                    },
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(Modifier.height(15.dp))
    }
}