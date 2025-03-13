package com.example.apinote.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apinote.app.core.utils.Constants
import com.example.apinote.app.ui.SplashScreen
import com.example.apinote.app.ui.navigation.type.createNavType
import com.example.apinote.app.ui.screens.MenuScreen
import com.example.apinote.app.ui.screens.api.DetailScreen
import com.example.apinote.app.ui.screens.api.ListScreen
import com.example.apinote.app.ui.screens.notes.NotesScreen
import com.example.apinote.app.ui.screens.parcelable.CharacterParcelableScreen
import com.example.apinote.app.ui.screens.parcelable.ReceivingDataScreen
import androidx.navigation.toRoute
import kotlin.reflect.typeOf


@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Splash) {

        composable<Splash> {
            SplashScreen(navigateToMenu = {
                navController.navigate(Menu) {
                    popUpTo(Splash) {
                        inclusive = true    //Delete Splash from the stack
                    }
                }
            })
        }
        composable<Menu> {
            MenuScreen(
                navigateToListScreen = { navController.navigate(List) },
                navigateToNotesScreen = { navController.navigate(Notes) },
                navigateToCharacterParcelableScreen = { navController.navigate(CharacterParcelable) })
        }


        composable<List> {
            ListScreen { characterId -> navController.navigate(Detail(characterId = characterId)) }
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            DetailScreen(characterId = detail.characterId)
        }


        composable<Notes> {
            NotesScreen()
        }


        composable<CharacterParcelable> {
            CharacterParcelableScreen(
                navigateToReceivingDataScreen = { navController.navigate(ReceivingData(it)) },
                navigateBack = { navController.navigateUp() }//Keeps the previous app in the stack if there is a deepLink
            )
        }
        composable<ReceivingData>(typeMap = mapOf(typeOf<ReceivingDataInfo>() to createNavType<ReceivingDataInfo>())) { backStackEntry ->
            val receivingData: ReceivingData = backStackEntry.toRoute()
            ReceivingDataScreen(receivingData.receivingDataInfo)
        }

    }
}