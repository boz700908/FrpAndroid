package com.github.jing332.frpandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.github.jing332.frpandroid.model.ShortCuts
import com.github.jing332.frpandroid.ui.nav.BottomNavBar
import com.github.jing332.frpandroid.ui.nav.NavigationGraph
import kotlinx.coroutines.launch
import splitties.systemservices.powerManager

val LocalMainViewModel = staticCompositionLocalOf<MainViewModel> {
    error("No MainViewModel provided")
}

class MainActivity : BaseComposeActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            killBattery()
            ShortCuts.buildShortCuts(this@MainActivity)
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()

        if (vm.showUpdateDialog != null) {
            val data = vm.showUpdateDialog ?: return
            AppUpdateDialog(
                onDismissRequest = { vm.showUpdateDialog = null },
                content = data.content,
                version = data.version,
                downloadUrl = data.downloadUrl,
            )
        }

        LaunchedEffect(vm.hashCode()) {
            vm.checkAppUpdate()
        }
        CompositionLocalProvider(
            LocalMainViewModel provides vm
        ) {

            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavBar(navController)
                }
            ) {
                NavigationGraph(
                    navController = navController,
                    Modifier.padding(bottom = it.calculateBottomPadding())
                )
            }
        }
    }


    @SuppressLint("BatteryLife")
    private fun killBattery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
                kotlin.runCatching {
                    startActivity(Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                        data = Uri.parse("package:$packageName")
                    })
                }
            }
        }
    }
}