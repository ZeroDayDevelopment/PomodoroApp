package com.example.pomodoroapp
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.MediaStore.Audio.Media
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var start_Time_in_millis:Long = 1500000
    private var start_Time:Long = start_Time_in_millis
    private var is_timer_running:Boolean = false
    private var nextcount:Int = 1
    private var start_pause_button:ImageButton ?= null
    private var reset_button:ImageButton ?= null
    private var next_button:ImageButton ?= null
    private var tv_session:TextView ?= null
    private var session:Int = 1
    private var total_session:Int = 4
    private var CountTextView:TextView ?= null
    private var progressbar:ProgressBar ?= null
    private var progresstext:TextView ?= null
    private var mp:MediaPlayer ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mp = MediaPlayer.create(this,R.raw.alarmsound)
        CountTextView?.setText("$(start_Time_in_millis/60000)")
        start_pause_button = findViewById<ImageButton>(R.id.button_start_pause)
        reset_button = findViewById<ImageButton>(R.id.button_reset)
        next_button = findViewById<ImageButton>(R.id.button_next)
        CountTextView = findViewById<TextView>(R.id.text_view_countdown)
        tv_session = findViewById<TextView>(R.id.tv_session)
        progressbar = findViewById(R.id.progress_bar)


        start_pause_button?.setOnClickListener{
            if (is_timer_running){
                stopTimer()
            }
            else{
                startTimer()
            }
        }
        reset_button?.setOnClickListener{
            resetTimer()
        }
        next_button?.setOnClickListener{
            nextTimer()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        when(id){
            R.id.nav_settings -> { val intent:Intent = Intent(this,MainActivity2::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_rate -> {
                Toast.makeText(this,"Thanks for rating our app",Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun startTimer(){
    countDownTimer = object:CountDownTimer(start_Time,1000){
        override fun onTick(millisUntilFinished: Long) {
            start_Time = millisUntilFinished
            SetTextViewCount()
        }
        override fun onFinish() {
            nextTimer()
            mp?.start()

        }
    }.start()

    is_timer_running = true

    start_pause_button?.setBackgroundResource(R.drawable.stopimg)
}
fun stopTimer(){
    countDownTimer?.cancel()
    is_timer_running = false
    start_pause_button?.setBackgroundResource(R.drawable.startbtn)
}
fun resetTimer(){
    countDownTimer?.cancel()
    is_timer_running = false
    start_pause_button?.setBackgroundResource(R.drawable.startbtn)
    if (nextcount == 1){
        start_Time = start_Time_in_millis
    }
    else if(nextcount == 2 && session != 0){
        start_Time = 60000*5
    }
    else if (nextcount == 2){
        start_Time = 60000*15
    }
        SetTextViewCount()
}
    fun nextTimer(){
        if (nextcount == 1)
        {
            resetTimer()
            nextcount = 2
            start_Time = 60000*5

            SetTextViewCount()
            start_pause_button?.setBackgroundResource(R.drawable.startbtn)
        }
        else if(nextcount == 2){
            resetTimer()
            start_Time = start_Time_in_millis

            SetTextViewCount()
            start_pause_button?.setBackgroundResource(R.drawable.startbtn)
            session++
            if(session == 4){
                nextcount = 2
            }
            else{
                nextcount = 1
            }

            tv_session?.setText("$session/4 \nSessions")
            if (session == total_session+1)
            {
                resetTimer()
                start_Time = 60000*15

                SetTextViewCount()
                start_pause_button?.setBackgroundResource(R.drawable.startbtn)
                nextcount = 2
                session = 0
                tv_session?.setText("$session/4 \nSessions")
            }
        }


    }
fun SetTextViewCount(){
        var minute = (start_Time/1000)/60
        var second = (start_Time/1000)%60
        val format_time:String = String.format(Locale.getDefault(),"%02d:%02d", minute, second)
        CountTextView?.setText(format_time)
    }

}
