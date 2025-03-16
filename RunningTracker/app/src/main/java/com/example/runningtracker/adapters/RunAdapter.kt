package com.example.runningtracker.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningtracker.db.Run
import com.example.runningtracker.other.TrackingUtility
import com.example.runningtrackerapp.databinding.ItemRunBinding // ✅ Import View Binding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RunAdapter(
    private val onDeleteClick: (Run) -> Unit
) : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    // ViewHolder sử dụng View Binding
    inner class RunViewHolder(val binding: ItemRunBinding) : RecyclerView.ViewHolder(binding.root)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val binding = ItemRunBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RunViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        val binding = holder.binding

        binding.btnDeleteRun.setOnClickListener {
            onDeleteClick(run) // Gọi callback với `run` hiện tại
        }

        // Liên kết dữ liệu với View Binding
        Glide.with(binding.root).load(run.img).into(binding.ivRunImage)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        binding.tvDate.text = dateFormat.format(calendar.time)

        binding.tvAvgSpeed.text = "${run.avgSpeedInKMH} km/h"
        binding.tvDistance.text = "${run.distanceInMeters / 1000f} km"
        binding.tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)
        binding.tvCalories.text = "${run.caloriesBurned} kcal"
    }
}