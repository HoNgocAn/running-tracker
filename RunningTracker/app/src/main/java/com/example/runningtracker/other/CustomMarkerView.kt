package com.example.runningtracker.other

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.runningtracker.db.Run
import com.example.runningtrackerapp.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("ViewConstructor")
class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    private val tvDate: TextView = findViewById(R.id.tvDate)
    private val tvDuration: TextView = findViewById(R.id.tvDuration)
    private val tvAvgSpeed: TextView = findViewById(R.id.tvAvgSpeed)
    private val tvDistance: TextView = findViewById(R.id.tvDistance)
    private val tvCaloriesBurned: TextView = findViewById(R.id.tvCaloriesBurned)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e == null) return

        val curRunId = e.x.toInt()
        val run = runs[curRunId]
        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        tvDate.text = dateFormat.format(calendar.time)

        tvAvgSpeed.text = "${run.avgSpeedInKMH}km/h"
        tvDistance.text = "${run.distanceInMeters / 1000f}km"
        tvDuration.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)
        tvCaloriesBurned.text = "${run.caloriesBurned}kcal"
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }
}