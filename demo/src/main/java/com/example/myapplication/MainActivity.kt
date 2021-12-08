package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.strongswan.android.data.VpnProfile
import org.strongswan.android.data.VpnProfileDataSource
import org.strongswan.android.data.VpnType
import org.strongswan.android.logic.VpnStateService
import org.strongswan.android.logic.VpnStateService.ErrorState.*
import org.strongswan.android.logic.VpnStateService.State.*
import org.strongswan.android.ui.VpnProfileControlActivity
import org.strongswan.android.ui.VpnStateFragment
import java.util.*

class MainActivity : AppCompatActivity(), VpnStateFragment.VpnErrorStateListener,
    VpnStateFragment.VpnStateListener {

    private lateinit var dataSource: VpnProfileDataSource
    private var stateFragment: VpnStateFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataSource = VpnProfileDataSource(this)
        dataSource.open()
        stateFragment =
            supportFragmentManager.findFragmentById(R.id.vpn_state_fragment) as? VpnStateFragment
        stateFragment?.setVpnErrorStateListener(this)
        stateFragment?.setVpnStateListener(this)
    }

    fun connect(view: android.view.View) {
//        val profile = VpnProfile().apply {
//            gateway = "frede.netvpp.com"
//            localId = "v.netvpp.com"
//            name = "DE - Free"
//            password = "y5rVSTUsn36YHED4"
//            remoteId = "v.netvpp.com"
//            username = "umr-96289536"
//            vpnType = VpnType.IKEV2_EAP
//            uuid = UUID.randomUUID()
//            flags = 0
//            selectedAppsHandling = VpnProfile.SelectedAppsHandling.SELECTED_APPS_DISABLE
//        }
        val profile = findProfileByGateway("freede.netvpp.com")

        val intent = Intent(this, VpnProfileControlActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = VpnProfileControlActivity.START_PROFILE
        intent.putExtra(
            VpnProfileControlActivity.EXTRA_VPN_PROFILE_ID,
            profile?.uuid.toString()
        )
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataSource.close()
    }

    fun findProfileByGateway(view: android.view.View) {
        val profile = dataSource.getVpnProfileByGateway("frede.netvpp.com")
        val message = if (profile != null) "找到了" else "没找到"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun findProfileByGateway(gateway: String): VpnProfile? {
        return dataSource.getVpnProfileByGateway(gateway)
    }

    fun saveProfile(view: android.view.View) {
        val profile = VpnProfile().apply {
            gateway = "freede.netvpp.com"
            localId = "v.netvpp.com"
            name = "DE - Free"
            password = "y5rVSTUsn36YHED4"
            remoteId = "v.netvpp.com"
            username = "umr-96289536"
            vpnType = VpnType.IKEV2_EAP
            uuid = UUID.randomUUID()
            flags = 0
            selectedAppsHandling = VpnProfile.SelectedAppsHandling.SELECTED_APPS_DISABLE
        }
        dataSource.insertProfile(profile)
        Toast.makeText(this, "存完了", Toast.LENGTH_SHORT).show()
    }

    override fun onVpnErrorState(state: VpnStateService.ErrorState?) {
        when (state) {
            NO_ERROR -> {
                Log.d(TAG, "onVpnErrorState: NO_ERROR")
            }
            AUTH_FAILED -> {
                Log.d(TAG, "onVpnErrorState: AUTH_FAILED")
            }
            PEER_AUTH_FAILED -> {
                Log.d(TAG, "onVpnErrorState: PEER_AUTH_FAILED")
            }
            LOOKUP_FAILED -> {
                Log.d(TAG, "onVpnErrorState: LOOKUP_FAILED")
            }
            UNREACHABLE -> {
                Log.d(TAG, "onVpnErrorState: UNREACHABLE")
            }
            GENERIC_ERROR -> {
                Log.d(TAG, "onVpnErrorState: GENERIC_ERROR")
            }
            PASSWORD_MISSING -> {
                Log.d(TAG, "onVpnErrorState: PASSWORD_MISSING")
            }
            CERTIFICATE_UNAVAILABLE -> {
                Log.d(TAG, "onVpnErrorState: CERTIFICATE_UNAVAILABLE")
            }
            null -> {
                Log.d(TAG, "onVpnErrorState: unknown")
            }
        }
    }

    override fun onVpnStateChanged(state: VpnStateService.State?) {
        when (state) {
            DISABLED -> {
                Log.d(TAG, "onVpnStateChanged: DISABLED")
            }
            CONNECTING -> {
                Log.d(TAG, "onVpnStateChanged: CONNECTING")
            }
            CONNECTED -> {
                Log.d(TAG, "onVpnStateChanged: CONNECTED")
            }
            DISCONNECTING -> {
                Log.d(TAG, "onVpnStateChanged: DISCONNECTING")
            }
            null -> {
                Log.d(TAG, "onVpnStateChanged: unknown")
            }
        }
    }

    fun disconnect(view: android.view.View) {
        stateFragment?.disconnect()
    }

}