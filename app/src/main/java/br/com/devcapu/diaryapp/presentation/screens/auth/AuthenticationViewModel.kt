package br.com.devcapu.diaryapp.presentation.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.devcapu.diaryapp.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App.Companion.create
import io.realm.kotlin.mongodb.Credentials.Companion.jwt
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {
    var authenticated = mutableStateOf(false)
        private set
    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    fun signInWithMongoAtlas(
        tokenId: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(IO) {
                    create(APP_ID).login(jwt(tokenId)).loggedIn
                }
                withContext(Main) {
                    onSuccess(result)
                    delay(900)
                    authenticated.value = true
                }
            } catch (ex: Exception) {
                withContext(Main) {
                    onError(ex)
                }
            }
        }
    }
}