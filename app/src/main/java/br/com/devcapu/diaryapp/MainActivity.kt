package br.com.devcapu.diaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.navigation.compose.rememberNavController
import br.com.devcapu.diaryapp.navigation.Screen.Authentication
import br.com.devcapu.diaryapp.navigation.Screen.Home
import br.com.devcapu.diaryapp.navigation.SetupNavGraph
import br.com.devcapu.diaryapp.ui.theme.DiaryAppTheme
import br.com.devcapu.diaryapp.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App.Companion.create

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setContent {
            DiaryAppTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController
                )
            }
        }
    }

    private fun getStartDestination(): String {
        val currentUser = create(APP_ID).currentUser
        return if (currentUser != null && currentUser.loggedIn) Home.route else Authentication.route
    }
}
