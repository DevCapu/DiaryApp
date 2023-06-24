package br.com.devcapu.diaryapp.navigation

import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.devcapu.diaryapp.navigation.Screen.Authentication
import br.com.devcapu.diaryapp.navigation.Screen.Home
import br.com.devcapu.diaryapp.navigation.Screen.Write
import br.com.devcapu.diaryapp.presentation.screens.auth.AuthenticationScreen
import br.com.devcapu.diaryapp.presentation.screens.auth.AuthenticationViewModel
import br.com.devcapu.diaryapp.presentation.screens.home.HomeScreen
import br.com.devcapu.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Home.route)
            }
        )

        homeRoute(navigateToWrite = {
            navController.navigate(Write.route)
        })

        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState

        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Authenticated successfully")
                        viewModel.setLoading(false)
                    },
                    onError = { message ->
                        println("1=================================")
                        println(message)
                        println("==================================")
                        messageBarState.addError(message)
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                println("2=================================")
                println(message)
                println("==================================")
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit
) {
    composable(route = Home.route) {
        val drawerState = rememberDrawerState(Closed)
        val scope = rememberCoroutineScope()
        HomeScreen(
            drawerState = drawerState,
            navigateToWrite = navigateToWrite,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = {

            }
        )
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}