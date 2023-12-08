package com.example.suitcase.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

val Context.currentConnectivityState:ConnectionState
    get(){
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getcurrentConnectivityState(connectivityManager)
    }

private fun getcurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionState {
    val connected = connectivityManager.allNetworks.any{network: Network? ->
        connectivityManager.getNetworkCapabilities(network)?.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )?:false
    }
    return if (connected) ConnectionState.Available else ConnectionState.UnAvailable
}

fun NetworkCallback(callback: (ConnectionState) -> Unit):ConnectivityManager.NetworkCallback{
    return object :ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.UnAvailable)
        }
    }
}

sealed class ConnectionState{
    object Available:ConnectionState()
    object UnAvailable:ConnectionState()
}



@OptIn(ExperimentalCoroutinesApi::class)
fun Context.observeConnectivityAsFlow() = callbackFlow{
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = NetworkCallback { state->trySend(state) }
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()

    connectivityManager.registerNetworkCallback(networkRequest,callback)

    val presentState = getcurrentConnectivityState(connectivityManager)
    trySend(presentState)

    awaitClose{
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return produceState(context.currentConnectivityState){
        context.observeConnectivityAsFlow().collect{
            value = it
        }
    }
}
