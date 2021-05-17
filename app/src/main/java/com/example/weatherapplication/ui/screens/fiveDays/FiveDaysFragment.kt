package com.example.weatherapplication.ui.screens.fiveDays

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.di.component.DaggerFragmentComponent
import com.example.weatherapplication.di.module.FragmentModule
import com.example.weatherapplication.model.fiveDays.FiveDaysObject
import com.example.weatherapplication.util.PERMISSION_REQUEST_CODE
import javax.inject.Inject

class FiveDaysFragment: Fragment(), FiveDaysContract.View {

    @Inject
    lateinit var presenter: FiveDaysContract.Presenter

    private lateinit var rootView: View

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    fun newInstance(): FiveDaysFragment {
        return FiveDaysFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()

    }

    private fun injectDependency() {
        val fiveDaysComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        fiveDaysComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.activity_five_days, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!checkGeolocationPermission()) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_REQUEST_CODE)
        }

        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    private fun initView() {
        context?.applicationContext?.let { presenter.getGeolocationInfo(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun showLoading(show: Boolean) {
        val progressBar = rootView.findViewById<ProgressBar>(R.id.progressBar)
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun hideLoading() {
        //TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        Log.e("ErrOR: ", error)
    }

    override fun loadWeatherList(weather: FiveDaysObject) {
        val header = activity?.findViewById<TextView>(R.id.header_textView)
        header!!.text = weather.city.name

        var adapter = activity?.let { FiveDaysAdapter(it, weather, this) }
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }

    companion object {
        val TAG: String = "FiveDaysFragment"
    }

    private fun checkGeolocationPermission() : Boolean {
        return context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED &&
                context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED


    }
}