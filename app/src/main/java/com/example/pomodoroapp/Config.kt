package com.example.pomodoroapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
class Config : AppCompatActivity() {
    private var workseekbar: SeekBar? = null
    private var workmin: TextView? = null
    private var shortseekbar: SeekBar? = null
    private var shortmin: TextView? = null
    private var longseekbar: SeekBar? = null
    private var longmin: TextView? = null
    private var sessionseekbar: SeekBar? = null
    private var sessionmin: TextView? = null
    private var seekbarValues: seekbarValues = seekbarValues()
    private var seekbaredit: SharedPreferences.Editor? = null
    private var sp: SharedPreferences ?= null
    private var restoredefault:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.config)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //SharedPrefences for seekbar values
        sp= getSharedPreferences("seekbarconfig", MODE_PRIVATE)
        seekbaredit = sp?.edit()

        //after the app is opened set the seekbar and text
        workseekbar = findViewById(R.id.workseekbar)
        workseekbar?.setProgress(sp?.getInt("workseekbar", seekbarValues.work_seekbar)?.div(60000) ?:60000 )
        workmin = findViewById(R.id.workmin)
        workmin?.setText("${sp?.getInt("workseekbar",seekbarValues.work_seekbar)?.div(60000)} mins")

        shortseekbar = findViewById(R.id.shortseekbar)
        shortseekbar?.setProgress(sp?.getInt("short_break_seekbar", seekbarValues.long_break_seekbar)?.div(60000) ?:60000)
        shortmin = findViewById(R.id.shortbreakmin)
        shortmin?.setText("${sp?.getInt("short_break_seekbar",seekbarValues.long_break_seekbar)?.div(60000)} mins")

        longseekbar = findViewById(R.id.longseekbar)
        longseekbar?.setProgress(sp?.getInt("long_break_seekbar", seekbarValues.long_break_seekbar)?.div(60000) ?:60000)
        longmin = findViewById(R.id.longbreakmin)
        longmin?.setText("${sp?.getInt("long_break_seekbar",seekbarValues.long_break_seekbar)?.div(60000)} mins")

        sessionseekbar = findViewById(R.id.sessionseekbar)
        sessionseekbar?.setProgress(sp?.getInt("session_seekbar", 4)!!)
        sessionmin = findViewById(R.id.sessionmin)
        sessionmin?.setText("${sp?.getInt("session_seekbar",seekbarValues.session_seekbar)} rounds")

        //restore default
        restoredefault = findViewById(R.id.restore)
        restoredefault?.setOnClickListener{restoredefault()}

        //put the seekbar values to class and after stoptrackingtouch put these values to sharedprefences(seekbaredit)
        //for work seekbar
        workseekbar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                workmin?.setText(progress.toString() + " mins")
                seekbarValues.work_seekbar = progress * 60000
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbaredit?.putInt("workseekbar", seekbarValues.work_seekbar)?.commit()
            }
        })

        //for short seekbar
        shortseekbar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                shortmin?.setText(progress.toString() + " mins")
                seekbarValues.short_break_seekbar = progress * 60000
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbaredit?.putInt("short_break_seekbar", seekbarValues.short_break_seekbar)?.commit()
            }
        })

        // for long seekbar
        longseekbar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                longmin?.setText(progress.toString() + " mins")
                seekbarValues.long_break_seekbar = progress * 60000
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbaredit?.putInt("long_break_seekbar", seekbarValues.long_break_seekbar)?.commit()
            }
        })

        // for sessionseekbar
        sessionseekbar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sessionmin?.setText(progress.toString() + " rounds")
                seekbarValues.session_seekbar = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekbaredit?.putInt("session_seekbar", seekbarValues.session_seekbar)?.commit()
            }
        })
    }

    //restore the default (work 25 - short break 5 - long break - 15 - sessions 4)
    fun restoredefault(){
        seekbaredit?.putInt("workseekbar", 1500000)?.commit()
        seekbaredit?.putInt("session_seekbar", 4)?.commit()
        seekbaredit?.putInt("long_break_seekbar", 900000)?.commit()
        seekbaredit?.putInt("short_break_seekbar", 300000)?.commit()
        workseekbar?.setProgress(sp?.getInt("workseekbar", seekbarValues.work_seekbar)?.div(60000) ?:60000)
        workmin?.setText("${sp?.getInt("workseekbar", seekbarValues.work_seekbar)?.div(60000)} mins")
        shortseekbar?.setProgress(sp?.getInt("short_break_seekbar", seekbarValues.short_break_seekbar)?.div(60000) ?:60000)
        shortmin?.setText("${sp?.getInt("short_break_seekbar", seekbarValues.short_break_seekbar)?.div(60000)} mins")
        longseekbar?.setProgress(sp?.getInt("long_break_seekbar", seekbarValues.long_break_seekbar)?.div(60000) ?:60000)
        longmin?.setText("${sp?.getInt("long_break_seekbar", seekbarValues.long_break_seekbar)?.div(60000)} mins")
        sessionseekbar?.setProgress(sp?.getInt("session_seekbar", 4)!!)
        sessionmin?.setText("${sp?.getInt("session_seekbar", 4)} rounds")
    }
}
