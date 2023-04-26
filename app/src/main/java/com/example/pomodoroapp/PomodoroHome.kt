package com.example.pomodoroapp
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import java.util.*

class PomodoroHome : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var start_Time_in_millis:Long = 1500000
    private var work_startTime:Long = start_Time_in_millis
    private var startTime:Long = start_Time_in_millis
    private var short_startTime:Long = start_Time_in_millis
    private var long_startTime:Long = start_Time_in_millis
    private var session_startTime:Int = 4
    private var is_timer_running:Boolean = false
    private var start_pause_button:ImageButton ?= null
    private var reset_button:ImageButton ?= null
    private var next_button:ImageButton ?= null
    private var tv_session:TextView ?= null
    private var session:Int = 1
    private var CountTextView:TextView ?= null
    private var mp:MediaPlayer ?=null
    private var sp:SharedPreferences?=null
    private var period_time = "short_period"
    private var resetselector:Long ?= null
    private var circleshape:ImageView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pomodoro_home)
        start_pause_button = findViewById<ImageButton>(R.id.button_start_pause)
        reset_button = findViewById<ImageButton>(R.id.button_reset)
        next_button = findViewById<ImageButton>(R.id.button_next)
        CountTextView = findViewById<TextView>(R.id.text_view_countdown)
        tv_session = findViewById<TextView>(R.id.tv_session)
        circleshape = findViewById(R.id.circle_shape)

        mp = MediaPlayer.create(this,R.raw.alarmsound)
        CountTextView?.setText("$(start_Time_in_millis/60000)")

        //get all sharedprefences
        sp = getSharedPreferences("seekbarconfig", MODE_PRIVATE)
        work_startTime = sp?.getInt("workseekbar", 1500000)!!.toLong()
        //when starting the app set to starttime value to work
        startTime = work_startTime
        short_startTime = sp?.getInt("short_break_seekbar", 300000)!!.toLong()
        long_startTime = sp?.getInt("long_break_seekbar", 900000)!!.toLong()
        session_startTime = sp?.getInt("session_seekbar", 4)!!

        tv_session?.setText("$session"+"/"+"$session_startTime "+"\nSessions")

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

        SetTextViewCount()
    }

    //for fixing color bug will be added to next version
fun fixbug(){
        if(resetselector == short_startTime){
            changecolor(R.color.green)
        }
        else if(resetselector == work_startTime){
            changecolor(R.color.space)
        }
        else if (resetselector == long_startTime){
            changecolor(R.color.yellow)
        }
}

    //options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        when(id){
            R.id.nav_settings -> { val intent:Intent = Intent(this,Config::class.java)
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
        resetselector = startTime
        countDownTimer = object:CountDownTimer(startTime,1000){
        override fun onTick(millisUntilFinished: Long) {
            startTime = millisUntilFinished
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
    if(resetselector == short_startTime){
        startTime = resetselector as Long
    }
    else if(resetselector == work_startTime){
        startTime = resetselector as Long
    }
    else if (resetselector == long_startTime){
        startTime = resetselector as Long
    }
    start_pause_button?.setBackgroundResource(R.drawable.startbtn)
    SetTextViewCount()
    }

    fun nextTimer(){
            if(period_time == "short_period"){
                startTime = short_startTime
                changecolor(R.color.green)
                period_time = "work"
            }
            else if(period_time == "work"){
                startTime = work_startTime
                changecolor(R.color.space)
                if (session == session_startTime-1){
                    period_time = "long_period"
                }
                else{
                    period_time = "short_period"
                }
                session++
            }
            else if (period_time == "long_period"){
                startTime = long_startTime
                changecolor(R.color.yellow)
                period_time = "work"
                session = 0
            }
        tv_session?.setText("$session"+"/"+"$session_startTime "+"\nSessions")
        countDownTimer?.cancel()
        is_timer_running = false
        SetTextViewCount()
        start_pause_button?.setBackgroundResource(R.drawable.startbtn)
    }

    fun SetTextViewCount(){
        var minute = (startTime /1000)/60
        var second = (startTime /1000)%60
        val format_time:String = String.format(Locale.getDefault(),"%02d:%02d", minute, second)
        CountTextView?.setText(format_time)
    }

    fun changecolor(colorid:Int){
        DrawableCompat.setTint(
            DrawableCompat.wrap(circleshape!!.getDrawable()),
            ContextCompat.getColor(this, colorid)
        )
        CountTextView?.setTextColor(ContextCompat.getColor(this, colorid))
        tv_session?.setTextColor(ContextCompat.getColor(this, colorid))
    }
}

