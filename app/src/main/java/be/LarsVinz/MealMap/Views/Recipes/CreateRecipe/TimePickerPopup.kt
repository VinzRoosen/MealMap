package be.LarsVinz.MealMap.Views.Recipes.CreateRecipe

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.NumberPicker
import be.LarsVinz.MealMap.R
import java.time.LocalTime

class TimePickerPopup(context : Context) : AlertDialog(context) {

    private val applyBtn : Button
    private val cancelBtn : Button
    private val hourPicker : NumberPicker
    private val minutePicker : NumberPicker
    private val secondPicker : NumberPicker

    private lateinit var onAddButtonEvent : () -> Unit

    init {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.time_picker_popup, null, false)
        setView(view)

        applyBtn = view.findViewById(R.id.applyBtn)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        hourPicker = view.findViewById(R.id.hourPicker)
        minutePicker = view.findViewById(R.id.minutePicker)
        secondPicker = view.findViewById(R.id.secondPicker)

        setupPickers()

        cancelBtn.setOnClickListener{
            this.dismiss()
        }

        applyBtn.setOnClickListener{
            onAddButtonEvent.invoke()
        }
    }

    public fun setSelectedTime(time : LocalTime){
        hourPicker.value = time.hour
        minutePicker.value = time.minute
        secondPicker.value = time.second
    }

    public fun getSelectedTime() : LocalTime {

        return LocalTime.of(hourPicker.value, minutePicker.value, secondPicker.value)
    }

    public fun setOnAddBtnListener(onClick: () -> Unit){
        onAddButtonEvent = onClick
    }

    private fun setupPickers(){
        hourPicker.minValue = 0
        hourPicker.maxValue = 12
        hourPicker.value = 0

        minutePicker.minValue = 0
        minutePicker.maxValue = 60
        minutePicker.value = 0

        secondPicker.minValue = 0
        secondPicker.maxValue = 60
        secondPicker.value = 0
    }
}