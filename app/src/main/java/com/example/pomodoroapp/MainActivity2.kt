package com.example.pomodoroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    private var workseekbar:SeekBar?= null
    private var workmin:TextView?= null
    private var shortseekbar:SeekBar?= null
    private var shortmin:TextView?= null
    private var longseekbar:SeekBar?= null
    private var longmin:TextView?= null
    private var sessionseekbar:SeekBar?= null
    private var sessionmin:TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        workseekbar = findViewById(R.id.workseekbar)
        workmin = findViewById(R.id.workmin)

        shortseekbar = findViewById(R.id.shortseekbar)
        shortmin = findViewById(R.id.shortbreakmin)

        longseekbar = findViewById(R.id.longseekbar)
        longmin = findViewById(R.id.longbreakmin)

        sessionseekbar = findViewById(R.id.sessionseekbar)
        sessionmin = findViewById(R.id.sessionmin)

        //for work area
        workseekbar?.setOnSeekBarChangeListener(object:OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                workmin?.setText(progress.toString()+" mins")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        shortseekbar?.setOnSeekBarChangeListener(object:OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                shortmin?.setText(progress.toString()+" mins")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        longseekbar?.setOnSeekBarChangeListener(object:OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                longmin?.setText(progress.toString()+" mins")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        sessionseekbar?.setOnSeekBarChangeListener(object:OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sessionmin?.setText(progress.toString()+" rounds")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }
}
