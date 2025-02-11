package com.example.runningtracker.other

import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest
import androidx.fragment.app.Fragment
import com.example.runningtracker.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import java.util.concurrent.TimeUnit

object TrackingUtility {
    fun hasLocationPermissions(context: Context): Boolean {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        return EasyPermissions.hasPermissions(context, *permissions.toTypedArray())
    }

    fun requestLocationPermissions(fragment: Fragment) {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        EasyPermissions.requestPermissions(
            fragment,
            "You need to accept location permissions to use this app",
            REQUEST_CODE_LOCATION_PERMISSION,
            *permissions.toTypedArray()
        )
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        val hours = TimeUnit.MILLISECONDS.toHours(ms)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(ms) % 60
        val milliseconds = (ms % 1000) / 10  // Lấy 2 chữ số đầu của mili-giây

        return if (includeMillis) {
            String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)
        } else {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}