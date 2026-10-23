package ui.screens

import android.app.Activity
import android.content.Intent
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Preview(showSystemUi = true)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterNewAccount: () -> Unit = {}
) {
    var context = LocalContext.current
    var userGmail by rememberSaveable { mutableStateOf("") }
    var userPassword by rememberSaveable { mutableStateOf("") }

    //Google Sing-in
    val webClienteId = "554556943892-vdklf09uj1qqtqrfg5ep8d9v15t51k17.apps.googleusercontent.com"
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClienteId)
            .requestEmail()
            .build()
    }

    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    // Iniciamos sesión en Firebase
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnSuccessListener { onLoginSuccess() }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Error en Firebase: ${it.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Error con Google: ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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
        TextField(
            value = userGmail,
            label = { Text(stringResource(R.string.CampoGmail)) },
            singleLine = true,
            onValueChange = { userGmail = it.trim() })

        Spacer(Modifier.height(20.dp))

        TextField(
            value = userPassword,
            label = { Text(stringResource(R.string.CampoContraseña)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { userPassword = it })

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                if (userGmail.isNotEmpty() && userPassword.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(userGmail, userPassword)
                        .addOnSuccessListener { onLoginSuccess() }
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

        OutlinedButton(onClick = {
            // Abre la ventana para seleccionar la cuenta de Google
            launcher.launch(googleSignInClient.signInIntent)
        }) {
            Text(stringResource(R.string.Boton_IniciarSesionGoogle))
        }

    }
}