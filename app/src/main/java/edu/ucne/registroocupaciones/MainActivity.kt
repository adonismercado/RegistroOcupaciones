package edu.ucne.registroocupaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registroocupaciones.presentation.navigation.AppNavHost
import edu.ucne.registroocupaciones.presentation.navigation.DrawerItem
import edu.ucne.registroocupaciones.presentation.navigation.DrawerMenu
import edu.ucne.registroocupaciones.ui.theme.RegistroOcupacionesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroOcupacionesTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)

                DrawerMenu(
                    drawerState = drawerState,
                    navHostController = navController
                ) { AppNavHost(navController = navController) }
            }
        }
    }
}