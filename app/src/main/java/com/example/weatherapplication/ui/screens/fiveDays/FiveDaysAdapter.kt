package com.example.weatherapplication.ui.screens.fiveDays

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.model.fiveDays.FiveDaysObject
import org.w3c.dom.Text

class FiveDaysAdapter(private val context: Context,
                      private val weatherObj: FiveDaysObject,
                      fragment: Fragment)
    : RecyclerView.Adapter<FiveDaysAdapter.FiveDaysViewHolder>() {

    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiveDaysViewHolder {
        val itemView = LayoutInflater
           .from(context)
           .inflate(R.layout.item_five_days, parent, false)
        return FiveDaysViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherObj.list.size
    }

    override fun onBindViewHolder(holder: FiveDaysViewHolder, position: Int) {
        val temp = weatherObj.list[position]

        val myMessage = temp.weather[0].description.capitalize()
        val myTemperature = temp.main.temp.toInt().toString() + "°"
        val dateTimeMap = getDateAndTime(temp.dtTxt)
        val image = setImage(temp.weather[0].main)


        holder.message!!.text = myMessage
        holder.temperature!!.text = myTemperature
        holder.time!!.text = dateTimeMap["day"] + " " + dateTimeMap["time"]
        holder.image!!.setImageResource(image)
    }

    class FiveDaysViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        var layout = itemView.findViewById<CardView>(R.id.item_five_days)
        var message = itemView.findViewById<TextView>(R.id.weather_message)
        var temperature = itemView.findViewById<TextView>(R.id.weather_temperature)
        val time = itemView.findViewById<TextView>(R.id.weather_time)

        val image = itemView.findViewById<ImageView>(R.id.weather_image)

    }

    private fun getDateAndTime(string: String) : Map<String, String> {
        val dateTime = string.split(" ")
        val time = dateTime[1].dropLast(3)
        val date = dateTime[0].split("-")

        return mapOf("day" to "${date[2]}.${date[1]}", "time" to time)
    }

    private fun setImage(nameWeather: String) : Int =
        when (nameWeather) {
            "Rain" -> { R.drawable.rain }
            "Clouds" -> { R.drawable.clouds }
            "Clear" -> { R.drawable.sun }

            else -> { R.drawable.ic_launcher_foreground }
        }

}