package ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.juggerml.R

@Preview(showBackground = true)
@Composable
fun HomeScreen(onLogout: () -> Unit = {}, onSettings: () -> Unit = {}) {
    // Usamos una Column principal que ocupe toda la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LazyColumn(
            modifier = Modifier
                .weight(1f) // Esto hace que la lista ceda espacio abajo para los botones
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.height(10.dp))
            }

            // NOTA PRO: En LazyColumn, los bucles grandes 'for' dentro de un solo 'item'
            // pierden eficiencia. Es mejor usar items() si vas a mostrar muchos elementos,
            // pero mantenemos tu estructura de bucle aquí para que coincida con tu código:
            items(50) { i -> // O tu bucle for tradicional

                Column(
                    Modifier.border(
                        1.dp,
                        shape = RectangleShape,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .width(300.dp)
                            .fillMaxWidth()
                            .padding(
                                top = 15.dp,
                                start = 10.dp
                            ), // Espaciado limpio entre elementos
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // En lugar de tu Image actual, usa este Box:
                        Box(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // 1. El círculo de fondo (mantiene su tamaño fijo de 50.dp)
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )

                            // 2. La imagen superpuesta, con el tamaño libre que tú quieras (ej. 70.dp o más)
                            Image(
                                painter = painterResource(R.drawable.icono_partido),
                                contentDescription = "icono partido",
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(top = 5.dp)// Aquí puedes hacerla tan grande como quieras sin que el círculo te limite
                            )
                        }
                        Text(
                            text = "${stringResource(R.string.Mensaje_PartidoJugger)} ${i + 1}",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Jugador 1")
                        Spacer(Modifier.width(40.dp))
                        Text("Jugador 2")
                    }
                    Spacer(Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Jugador 3")
                        Spacer(Modifier.width(40.dp))
                        Text("Jugador 4")
                    }
                    var context = LocalContext.current
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            Toast.makeText(context, "Te has unido con éxito", Toast.LENGTH_LONG)
                                .show()
                        }) {
                            Text("Unirse a partido", color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                    Spacer(Modifier.height(10.dp))


                }
                Spacer(Modifier.height(10.dp))
            }
        }

        Spacer(Modifier.height(10.dp))

        // 2. LOS BOTONES (Fijos en la parte inferior, fuera de la zona de scroll)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los botones ordenadamente
        ) {
            Button(onClick = { onLogout() }) {
                Text(
                    stringResource(R.string.Boton_CerrarSesion),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Button(onClick = { onSettings() }) {
                Text(
                    stringResource(R.string.Boton_AbrirAjustes),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}