package com.example.weatherapplication.ui.screens.currentDay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.di.component.DaggerFragmentComponent
import com.example.weatherapplication.di.module.FragmentModule
import com.example.weatherapplication.model.currentDay.CurrentDayObject
import com.example.weatherapplication.ui.screens.main.MainContract
import javax.inject.Inject

class CurrentDayFragment : Fragment(), CurrentDayContract.View {

    @Inject
    lateinit var presenter: CurrentDayContract.Presenter


    private lateinit var rootView: View

    fun newInstance(): CurrentDayFragment {
        return CurrentDayFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.activity_current_day, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    private fun initView() {
        presenter.loadWeather()
    }

    private fun injectDependency() {
        val currentDayComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        currentDayComponent.inject(this)
    }

    override fun loadCurrentWeather(weather: CurrentDayObject) {
        val testText = rootView.findViewById<TextView>(R.id.real_temp)
        val header = activity?.findViewById<TextView>(R.id.header_textView)

        testText!!.text = weather.main.temp.toInt().toString()
        header!!.text = resources.getText(R.string.today)

    }

    override fun showError(error: String) {
        Log.e("ErrOR in CurrentDay: ", error)
    }

    override fun showLoading(show: Boolean) {
        val progressBar = rootView.findViewById<ProgressBar>(R.id.progressBar)
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        val TAG: String = "CurrentDayFragment"
    }
}