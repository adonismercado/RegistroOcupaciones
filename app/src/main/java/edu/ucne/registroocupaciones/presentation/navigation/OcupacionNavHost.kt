package edu.ucne.registroocupaciones.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.registroocupaciones.presentation.edit.EditOcupacionScreen
import edu.ucne.registroocupaciones.presentation.list.ListOcupacionScreen

@Composable
fun TaskNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList
    ) {
        composable<Screen.OcupacionList> {
            ListOcupacionScreen(
                onAddOcupacion = {
                    navController.navigate(Screen.OcupacionForm(0))
                },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.OcupacionForm(id))
                }
            )
        }

        composable<Screen.OcupacionForm> {
            EditOcupacionScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}