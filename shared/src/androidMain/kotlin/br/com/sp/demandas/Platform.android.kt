package br.com.sp.demandas

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
import br.com.sp.demandas.core.app.KeyValueStorage
import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.design.theme.MaxTheme
import br.com.sp.demandas.ui.App
import br.com.sp.demandas.ui.login.makeLogin.LoginScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.liftric.kvault.KVault
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Application() {
    val context = LocalContext.current
    Navigator(
        App()
    ) { navigator ->
        MaxTheme {
            CurrentScreen() }
        HandleOnNewIntent(context, navigator)

    }
}

@Composable
fun HandleOnNewIntent(context: Context, navigator: Navigator) {
    LaunchedEffect(Unit) {
        callbackFlow<Intent> {
            val componentActivity = context as ComponentActivity
            val consumer = Consumer<Intent> { trySend(it) }
            componentActivity.addOnNewIntentListener(consumer)
            awaitClose { componentActivity.removeOnNewIntentListener(consumer) }
        }.collectLatest { handleIntent() }
    }
}

fun handleIntent(){

}

class AndroidKeyValueStorage(private val context: Context) : KeyValueStorage {
    override fun getStore(): KVault {
        return KVault(context)
    }
}

class AndroidPlatform : Platform {
    override val name: String = "Android"
}


