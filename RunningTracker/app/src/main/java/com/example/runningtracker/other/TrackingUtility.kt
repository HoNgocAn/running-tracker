package com.example.runningtracker.other

import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest

import android.location.Location
import androidx.fragment.app.Fragment
import com.example.runningtracker.other.Constants.LOCATION_REQUEST_CODE
import com.example.runningtracker.services.Polyline
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

        fragment.requestPermissions(
            permissions.toTypedArray(),
            LOCATION_REQUEST_CODE
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

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for (i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]
            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }
}