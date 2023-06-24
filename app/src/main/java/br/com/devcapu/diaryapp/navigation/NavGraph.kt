package br.com.devcapu.diaryapp.navigation

import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import br.com.devcapu.diaryapp.presentation.components.DisplayAlertDialog
import br.com.devcapu.diaryapp.presentation.screens.auth.AuthenticationScreen
import br.com.devcapu.diaryapp.presentation.screens.auth.AuthenticationViewModel
import br.com.devcapu.diaryapp.presentation.screens.home.HomeScreen
import br.com.devcapu.diaryapp.util.Constants.APP_ID
import br.com.devcapu.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App.Companion.create
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        homeRoute(
            navigateToWrite = {
                navController.navigate(Write.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Authentication.route)
            }
        )

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
                        messageBarState.addError(message)
                        viewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit
) {
    composable(route = Home.route) {
        val drawerState = rememberDrawerState(Closed)
        var signOutDialogOpened by remember { mutableStateOf(false) }
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
                signOutDialogOpened = true
            }
        )

        DisplayAlertDialog(
            title = "Sign out",
            message = "Are you sure you want to Sign out from your google account?",
            dialogOpened = signOutDialogOpened,
            onCloseDialog = { signOutDialogOpened = false },
            onYesClick = {
                scope.launch(Dispatchers.IO) {
                    create(APP_ID).currentUser?.logOut()
                    withContext(Dispatchers.Main) {
                        navigateToAuth()
                    }
                }
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