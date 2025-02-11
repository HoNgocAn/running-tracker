package com.example.runningtracker.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.runningtracker.db.RunDao
import com.example.runningtrackerapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.Manifest
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.runningtracker.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.appbar.MaterialToolbar
import timber.log.Timber
import android.content.pm.PackageManager



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🛡️ Kiểm tra quyền thông báo cho Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }

        navigateToTrackingFragmentIfNeeded(intent)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.navHostFragment) // Đổi tên biến cho rõ ràng

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                    bottomNavigationView.visibility = View.VISIBLE
                else ->
                    bottomNavigationView.visibility = View.GONE
            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }


    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        val navController = findNavController(R.id.navHostFragment)

        // Kiểm tra nếu intent chứa action đúng
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            // Kiểm tra xem đã đang ở TrackingFragment chưa để tránh lỗi điều hướng lặp
            if (navController.currentDestination?.id != R.id.trackingFragment) {
                navController.navigate(R.id.action_global_trackingFragment)
            }
        }
    }

    // Xử lý kết quả xin quyền thông báo
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Timber.d("✅ Notification permission granted!")
            } else {
                Timber.d("❌ Notification permission denied.")
            }
        }
    }
}
