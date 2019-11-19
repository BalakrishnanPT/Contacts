package `in`.company.contacts.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build

import androidx.lifecycle.LiveData

class ConnectionStateMonitor(private val mContext: Context) : LiveData<Boolean>() {
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var networkReceiver: NetworkReceiver? = null
    private val connectivityManager: ConnectivityManager?

    init {
        connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = NetworkCallback(this)
        } else {
            networkReceiver = NetworkReceiver()
        }
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager!!.registerDefaultNetworkCallback(networkCallback!!)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val networkRequest = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build()
                connectivityManager!!.registerNetworkCallback(networkRequest, networkCallback!!)
            }
            else -> mContext.registerReceiver(networkReceiver, IntentFilter("CONNECTIVITY_ACTION"))
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager!!.unregisterNetworkCallback(networkCallback!!)
        } else {
            mContext.unregisterReceiver(networkReceiver)
        }
    }

    internal fun updateConnection() {
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
                postValue(true)
            } else {
                postValue(false)
            }
        }

    }

    internal inner class NetworkCallback(private val mConnectionStateMonitor: ConnectionStateMonitor) :
        ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            mConnectionStateMonitor.postValue(true)
        }

        override fun onLost(network: Network) {
            mConnectionStateMonitor.postValue(false)
        }
    }

    internal inner class NetworkReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "CONNECTIVITY_ACTION") {
                updateConnection()
            }
        }
    }
}