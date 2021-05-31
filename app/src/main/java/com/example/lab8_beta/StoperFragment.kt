package com.example.lab8_beta

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout


class StoperFragment : Fragment(), View.OnClickListener {
    lateinit var timeView : TextView;
    lateinit var sharedPref : SharedPreferences;
    private var seconds: Int = 0;
    private var running = false;
    private var wasRunning = false;
    private var hours: Int = 0;
    private var secs: Int = 0;
    private var minutes: Int = 0;
    lateinit var best_result: TextView;
    lateinit var last_result: TextView;
    private var item: Route.Item? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(StoperFragment.ARG_ITEM_ID)) {
                item = Route.route_map[it.getString(StoperFragment.ARG_ITEM_ID)]
                Log.d("Tag", item?.content.toString())            }
        }
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout: View = inflater.inflate(R.layout.fragment_stopper, container, false)
        runStoper(layout)
        val startButton: Button = layout.findViewById<View>(R.id.start_button) as Button
        startButton.setOnClickListener(this)
        val stopButton: Button = layout.findViewById<View>(R.id.stop_button) as Button
        stopButton.setOnClickListener(this)
        val resetButton: Button = layout.findViewById<View>(R.id.reset_button) as Button
        resetButton.setOnClickListener(this)
        val saveButton: Button = layout.findViewById<View>(R.id.save_button) as Button
        saveButton.setOnClickListener(this)

        sharedPref = activity?.getSharedPreferences("com.example.lab8_beta.shared",0)!!
        var besthours = sharedPref?.getInt(item?.content.toString()+"besthours",0)
        var bestsecs = sharedPref?.getInt(item?.content.toString()+"bestsecs",0)
        var bestminutes = sharedPref?.getInt("bestminutes",0)

        var lasthours = sharedPref?.getInt(item?.content.toString()+"lasthours",0)
        var lastsecs = sharedPref?.getInt(item?.content.toString()+"lastsecs",0)
        var lastminutes = sharedPref?.getInt(item?.content.toString()+"lastminutes",0)

        best_result = layout.findViewById<View>(R.id.best_result) as TextView
        last_result = layout.findViewById<View>(R.id.last_result) as TextView
        var time2 = String.format("%d:%02d:%02d", besthours, bestminutes, bestsecs)
        var time3 = String.format("%d:%02d:%02d", lasthours, lastminutes,lastsecs)
        best_result.text = "Najlepszy wynik: " + time2

        last_result.text = "Ostatni wynik:"+ time3
        return layout
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt("seconds", seconds)
        savedInstanceState.putBoolean("running", running)
        savedInstanceState.putBoolean("wasRunning", wasRunning)
    }

    fun onClickStart() {
        running = true
    }

    fun onClickStop() {
        running = false
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
                timeView, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    fun onClickReset() {
        running = false
        seconds = 0
        val animator = ObjectAnimator.ofFloat(timeView, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }
    fun onClickSave() {

        var sharedPref = activity?.getSharedPreferences("com.example.lab8_beta.shared",0)

        var best_hours = sharedPref?.getInt(item?.content.toString()+"besthours",0)
        var best_minutes = sharedPref?.getInt(item?.content.toString()+"bestminutes",0)
        var best_secs = sharedPref?.getInt(item?.content.toString()+"bestsecs",0)

        last_result.text =  "Ostatni wynik:" + activity?.findViewById<TextView>(R.id.time_view)?.text
        var last = sharedPref?.edit()
        last?.putInt(item?.content.toString()+"lasthours",hours)
        last?.putInt(item?.content.toString()+"lastminutes",minutes)
        last?.putInt(item?.content.toString()+"lastsecs",secs)
        last?.apply()

        val animator2 = ObjectAnimator.ofFloat(last_result, View.ALPHA, 0f)
        animator2.repeatCount = 1
        animator2.repeatMode = ObjectAnimator.REVERSE
        animator2.start()

        if(
            (hours>best_hours!!)
            || ((hours==best_hours) &&(minutes> best_minutes!!))
            || ((hours==best_hours) &&(minutes== best_minutes!!) && (secs> best_secs!!))
        ) {
            var best = sharedPref?.edit()
            best?.putInt(item?.content.toString() + "besthours", hours)
            best?.putInt(item?.content.toString() + "bestminutes", minutes)
            best?.putInt(item?.content.toString() + "bestsecs", secs)
            best?.apply()
            var time3 = String.format("%d:%02d:%02d", hours, minutes, secs)
            best_result.text = "Najlepszy wynik: " + time3

            val animator = ObjectAnimator.ofFloat(timeView, View.ROTATION,  0f,3600f)
            animator.duration = 1000
            animator.start()

            val animator2 = ObjectAnimator.ofFloat(best_result, View.ALPHA, 0f)
            animator2.repeatCount = 1
            animator2.repeatMode = ObjectAnimator.REVERSE
            animator2.start()
        }
    }

    private fun runStoper(view: View) {
        timeView = view.findViewById<View>(R.id.time_view) as TextView
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                hours = seconds / 3600
                minutes = seconds % 3600 / 60
                secs = seconds % 60
                var time = String.format("%d:%02d:%02d", hours, minutes, secs)
                timeView.text = time
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.start_button -> onClickStart()
            R.id.stop_button -> onClickStop()
            R.id.reset_button -> onClickReset()
            R.id.save_button -> onClickSave()
        }
    }
}
