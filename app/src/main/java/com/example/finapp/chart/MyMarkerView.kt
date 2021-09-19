package com.example.finapp.chart

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.finapp.R
import com.example.finapp.dateToString
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

/**
 * Функция  refreshContent принимает объект класса Entry — это запись на графике, может содержать
 * одно или множество значений. Второй параметр — Highlight — содержит информацию, необходимую для
 * определения выделенного значения. В теле функции заполняем текстовое поле маркера значениями графика по осям
 * с применением необходимого форматирования. Функция getOffset() возвращает положение маркера.
 */

@SuppressLint("ViewConstructor")
class MyMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView

    init {
        tvContent = findViewById(R.id.tvContent)
    }

    // runs every time the MarkerView is redrawn, can be used to update the content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {


        tvContent.text = e.y.toString() + "\n" + e.x.dateToString("MMM dd, yyyy")


        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}