package com.example.myapplication5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import org.json.JSONObject
import android.os.AsyncTask
import android.widget.*
import java.net.URL
import com.example.myapplication5.R.layout.activity_main
import org.json.JSONArray
import java.io.File
import java.io.FileWriter
import android.content.Intent
import android.widget.AdapterView.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main_two.view.*

class MainActivity : AppCompatActivity()  {
    var lvMain : ListView? = null //
    //var mInfoTextView : TextView? = null  //=
    val sUrl = "https://sample.fitnesskit-admin.ru/schedule/get_group_lessons_v2/1/"
    var mArrayFitnessObject = emptyArray<Any>() //MutableListIterator<Any>  //emptyArray<Any>()
    val mArrayKey = arrayOf("name","teacher","place","startTime","endTime","pay","weekDay","availability","description")
    interface AListener {
        fun doEvent()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(activity_main)

        lvMain = findViewById(R.id.test)
        //mInfoTextView = findViewById(R.id.HelloWorld)

        // Создаем поток для получения данных
        val mNotify = Notify(this)
        val ContentFts = GetUrlContentTask1()

        ContentFts.addListener(mNotify)
        ContentFts.execute(sUrl)

        lvMain?.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, MainTwoActivity::class.java)
            var mArrayRows = emptyArray<String>()

            val mElement = mArrayFitnessObject[position] as HashMap<String,String>

            intent.putExtra("mArrayObject",mElement)
            //intent.putExtra("mArrayKey",mArrayKey)
            startActivity(intent)
        }

    }

    internal inner class GetUrlContentTask1 : AsyncTask<String, Int, Boolean>() {
        var listeners = emptyArray<Notify>()


        //var listeners: emptyArray<Notify>() //Array<AListener>? = null
        fun addListener(listener: Notify) {
            listeners += listener
        }
        override fun doInBackground(vararg urls: String): Boolean {
            var arrayJson : JSONArray
            var mTryRFile = false
            try {
                val url = URL(urls[0])
                val mFaleJSONText = File(filesDir, "mFale.json")
                val mRecordsForFile = FileWriter(mFaleJSONText)
                mRecordsForFile.write(url.readText())
                arrayJson = JSONArray(url.readText())
                fitnessObject(arrayJson)
                //return url.readText()
                //return url.readText()
            }catch (h:NumberFormatException){
                // а тут надо бы собирать описание ошибки.
                mTryRFile = true
            }
            if (mTryRFile == true){
                try {
                    val url = File(filesDir, "mFale.json")
                    //return url.readText()
                    arrayJson = JSONArray(url.readText())
                    fitnessObject(arrayJson)
                    //return url.readText()
                }catch (h:NumberFormatException){
                    // а тут надо бы собирать описание ошибки.
                    return false
                }
            }
        return true
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)

            if (result) {
                try {
                    listeners!![0].doEvent()
                } finally {

                }
            }
            // else <Тут надо бы сообщить пользователю, что данных нет>
        }
        fun fitnessObject(arrayJson:JSONArray){

            for (check in 0 until arrayJson.length()) {
                val mHasp = HashMap<String,Any>()
                val mDataRecord = arrayJson[check] as JSONObject
                val mTeacher2 = HashMap<String,String>()
                for (mIdKey in 0 until mArrayKey.size){
                    val mKey = mArrayKey[mIdKey]
                    val mValue= mDataRecord [mKey as String]
                    if (mValue is String){
                        mHasp.put(mKey,mValue.toString().trim())
                    }
                    if (mValue is Boolean){
                        if(mValue==true){mHasp.put(mKey,"Да")}else{mHasp.put(mKey,"Нет")}
                    }
                    if(mValue is Int){
                        mHasp.put(mKey,mValue.toString().trim())

                    }

                }
                mTeacher2.put("name",((mDataRecord["teacher_v2"] as JSONObject)["name"]).toString())
                mTeacher2.put("position",((mDataRecord["teacher_v2"] as JSONObject)["position"]).toString())
                mTeacher2.put("imageUrl",((mDataRecord["teacher_v2"] as JSONObject)["imageUrl"]).toString())
                mHasp.put("teacher_v2",mTeacher2)
                mArrayFitnessObject += mHasp
                //mArrayKey += "teacher,description,place,name,startTime,endTime,pay"
            }
        }

    }


    internal inner class Notify (mContext:Context): AListener {

        var mContext = mContext

        override fun doEvent() {

            var mArrayString = emptyArray<String>()
            for (check in  0..mArrayFitnessObject.size-1){
                mArrayString += (mArrayFitnessObject[check] as HashMap<String,String>)["name"].toString()
            }

            var adapter = ArrayAdapter<String>(
                mContext,
                android.R.layout.simple_list_item_1,mArrayString
            )

            // присваиваем адаптер списку
            lvMain?.setAdapter(adapter)

        }

    }
}



