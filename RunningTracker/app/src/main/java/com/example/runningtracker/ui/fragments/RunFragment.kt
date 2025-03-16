package com.example.runningtracker.ui.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runningtracker.adapters.RunAdapter
import com.example.runningtracker.db.Run
import com.example.runningtracker.di.SortType
import com.example.runningtracker.other.TrackingUtility
import com.example.runningtracker.ui.viewmodels.MainViewModel
import com.example.runningtrackerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment: Fragment(R.layout.fragment_run),  EasyPermissions.PermissionCallbacks {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    private lateinit var btnDeleteRun: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        runAdapter = RunAdapter { run ->
            deleteRunAndRemoveFromDB(run)
        }
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val spFilter = view.findViewById<Spinner>(R.id.spFilter)

        btnDeleteRun = view.findViewById(R.id.btnDeleteRun)


        setupRecyclerView(view)


        when (viewModel.sortType) {
            SortType.DATE -> spFilter.setSelection(0)
            SortType.RUNNING_TIME -> spFilter.setSelection(1)
            SortType.DISTANCE -> spFilter.setSelection(2)
            SortType.AVG_SPEED -> spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> spFilter.setSelection(4)
        }

        viewModel.runs.observe(viewLifecycleOwner, Observer { runs ->
            runAdapter.submitList(runs)
        })

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                when (pos) {
                    0 -> viewModel.sortRuns(SortType.DATE)
                    1 -> viewModel.sortRuns(SortType.RUNNING_TIME)
                    2 -> viewModel.sortRuns(SortType.DISTANCE)
                    3 -> viewModel.sortRuns(SortType.AVG_SPEED)
                    4 -> viewModel.sortRuns(SortType.CALORIES_BURNED)
                }
            }
        }

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun deleteRunAndRemoveFromDB(run: Run) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Run")
            .setMessage("Are you sure you want to delete this run?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteRun(run)
                Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Run deleted successfully.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
    private fun setupRecyclerView(view: View) {
        val rvRuns = view.findViewById<RecyclerView>(R.id.rvRuns)
        rvRuns.apply {
            adapter = runAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }



    private fun requestPermissions() {
        if (!TrackingUtility.hasLocationPermissions(requireContext())) {
            TrackingUtility.requestLocationPermissions(this)
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).setThemeResId(R.style.AlertDialogTheme).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}