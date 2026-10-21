package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.juggerml.R

@Preview(showBackground = true)
@Composable
fun HomeScreen(onLogout: () -> Unit = {}, onSettings: () -> Unit = {}) {
   Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
       Row(Modifier.padding(bottom = 20.dp)) {
           Button(onClick = { onLogout()}) {
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Este es la ventana donde tienes elementos generales")

    }
}