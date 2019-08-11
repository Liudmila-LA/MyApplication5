package com.example.myapplication5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main_two.*
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView


class MainTwoActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_two)
        setSupportActionBar(toolbar)
        var mArrayRows = emptyArray<String>()
        var mArrayCol = emptyArray<String>()
        val mArrayObject= intent.getSerializableExtra("mArrayObject") as HashMap<String,String>
        var mRightCol = findViewById(R.id.dataRow) as ListView
        //var mLeftCol = findViewById(R.id.nameRow) as ListView
        var mCorrValue = HashMap<String,String>()
        var mPay = ""
        mCorrValue.put("name","Тренировка")
        mCorrValue.put("teacher","Тренер")
        mCorrValue.put("place","Зал")
        //mCorrValue.put("weekDay","День недели")
        //mCorrValue.put("startTime","Начало")
        //mCorrValue.put("endTime","Окончание")
        //mCorrValue.put("pay","Тренировка платная")"pay",
        mCorrValue.put("availability","Кол-во мест")
        //mCorrValue.put("description","Описание")
        // -
        // + Дни недели
        var mDaysOfWeek = HashMap<Int,String>()
        mDaysOfWeek.put(1,"Понедельник")
        mDaysOfWeek.put(2,"Вторник")
        mDaysOfWeek.put(3,"Среда")
        mDaysOfWeek.put(4,"Четверг")
        mDaysOfWeek.put(5,"Пятница")
        mDaysOfWeek.put(6,"Суббота")
        mDaysOfWeek.put(7,"Воскресенье")
// -
        //val mImage = findViewById<ImageView>(R.id.)
        val entries:MutableSet<String> = (mCorrValue.keys)

        val g = entries.toTypedArray()
        mArrayRows += ""
        mArrayCol += ""
        for (mKey in 0 until entries.size){
            mArrayRows += mCorrValue[g[mKey]].toString() + ": " + mArrayObject[g[mKey]].toString().trim()
            //mArrayCol += mCorrValue[g[mKey]].toString()
        }
        if(mArrayObject["pay"]=="true"){
            mPay = "(Занятие платное)"
        }
        mArrayRows += "Расписание: "+mDaysOfWeek[(mArrayObject["weekDay"] as String).toInt()] + " c " + mArrayObject["startTime"] + " по " + mArrayObject["endTime"]+mPay
        mArrayRows += "Описание:" + mArrayObject["description"].toString().trim()

        //mArrayCol += "Расписание"
        //mArrayCol += "Описание"
       // mArrayRows += "Ntcn"



        var adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,mArrayRows
        )
        mRightCol.adapter=adapter

    }

}
