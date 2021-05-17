package com.example.weatherapplication.ui.screens.currentDay

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.di.component.DaggerFragmentComponent
import com.example.weatherapplication.di.module.FragmentModule
import com.example.weatherapplication.model.currentDay.CurrentDayObject
import com.example.weatherapplication.util.PERMISSION_REQUEST_CODE
import javax.inject.Inject

class CurrentDayFragment : Fragment(), CurrentDayContract.View {

    @Inject
    lateinit var presenter: CurrentDayContract.Presenter

    private lateinit var rootView: View

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

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

        if(!checkGeolocationPermission()) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, PERMISSION_REQUEST_CODE)
        }

        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    private fun initView() {
        context?.applicationContext?.let { presenter.getGeolocationInfo(it) }
    }

    private fun injectDependency() {
        val currentDayComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        currentDayComponent.inject(this)
    }

    override fun loadCurrentWeather(weather: CurrentDayObject) {
        val header = activity?.findViewById<TextView>(R.id.header_textView)

        val city = rootView.findViewById<TextView>(R.id.location_city)
        val countryCode = rootView.findViewById<TextView>(R.id.location_country_code)
        val temperature = rootView.findViewById<TextView>(R.id.location_temperature)
        val weatherDesc = rootView.findViewById<TextView>(R.id.location_weather)

        val imageWeather = rootView.findViewById<ImageView>(R.id.location_weather_icon)
        val imageLocation = rootView.findViewById<ImageView>(R.id.image_location)

        val imageHumidity = rootView.findViewById<ImageView>(R.id.location_humidity_icon)
        val humidity = rootView.findViewById<TextView>(R.id.location_humidity_text)
        val rainVolume = rootView.findViewById<TextView>(R.id.location_rainVolume_text)
        val imageRainVolume = rootView.findViewById<ImageView>(R.id.location_rainVolume_icon)
        val pressure = rootView.findViewById<TextView>(R.id.location_pressure_text)
        val imagePressure = rootView.findViewById<ImageView>(R.id.location_pressure_icon)

        val wind = rootView.findViewById<TextView>(R.id.location_wind_text)
        val imageWind = rootView.findViewById<ImageView>(R.id.location_wind_icon)

        val windDegree = rootView.findViewById<TextView>(R.id.location_windDegree_text)
        val imageWindDegree = rootView.findViewById<ImageView>(R.id.location_windDegree_icon)


        city!!.text = weather.name + ","
        countryCode!!.text = weather.sys.country
        temperature!!.text = weather.main.temp.toInt().toString() + "°C,"
        weatherDesc!!.text = weather.weather[0].main
        imageLocation!!.setImageResource(R.drawable.outline_north_east_black_24dp)
        imageWeather.setImageResource(setImage(weather.weather[0].main))

        humidity!!.text = weather.main.humidity.toString() + "%"
        imageHumidity.setImageResource(R.drawable.humidity_icon)
        rainVolume!!.text = "0 mm"
        imageRainVolume.setImageResource(R.drawable.drop_icon)
        pressure!!.text = weather.main.pressure.toString() + " hPa"
        imagePressure.setImageResource(R.drawable.pressure_icon)

        wind!!.text = weather.wind.speed.toString() + " m/s"
        imageWind.setImageResource(R.drawable.wind_120368)
        windDegree!!.text = setWindDegree(weather.wind.deg)
        imageWindDegree.setImageResource(R.drawable.compass_icon)


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

    private fun setImage(nameWeather: String) : Int =
        when (nameWeather) {
            "Rain" -> { R.drawable.rain }
            "Clouds" -> { R.drawable.clouds }
            "Clear" -> { R.drawable.sun }

            else -> { R.drawable.ic_launcher_foreground }
        }

    private fun setWindDegree(degree: Int) : String =
        when(degree)  {
            0, 360 -> { "N" }
            45-> { "NE" }
            90 -> { "E" }
            135 -> { "SE" }
            180 -> { "S" }
            225 -> { "SW" }
            270 -> { "W" }
            315 -> { "NW" }
            in 1 .. 44 -> { "NNE" }
            in 46 .. 89 -> { "ENE" }
            in 91 .. 134 -> { "ESE" }
            in 136 .. 179 -> { "SSE" }
            in 181 .. 224 -> { "SSW" }
            in 226 .. 269 -> { "WSW" }
            in 271 .. 314 -> { "WNW" }
            in 316 .. 359 -> { "NNW" }

            else -> { "Unknown" }
        }


    private fun checkGeolocationPermission() : Boolean {
        return context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED &&
                context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED


    }

}