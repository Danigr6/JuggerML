package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

        // 1. LA LISTA (Ocupa todo el espacio flexible con .weight(1f))
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
                Row(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(vertical = 10.dp), // Espaciado limpio entre elementos
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(R.drawable.icono_partido),
                        contentDescription = "icono partido",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                    Spacer(Modifier.width(10.dp)) // Espacio entre imagen y texto
                    Text(text = "${stringResource(R.string.Mensaje_PartidoJugger)} ${i + 1}")
                }
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
