package br.com.devcapu.diaryapp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.devcapu.diaryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
    navigateToWrite: () -> Unit,
    onSignOutClicked: () -> Unit,
) {
    NavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked,
        content = {
            Scaffold(
                topBar = {
                    HomeTopBar(onMenuClicked)
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = navigateToWrite) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                }
            ) {
                Column(Modifier.padding(it)) { }
            }
        },
    )
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(90.dp)
                        .align(Alignment.CenterHorizontally),
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = "Sign out",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    selected = false,
                    onClick = onSignOutClicked
                )
            }
        },
        content = content
    )
}