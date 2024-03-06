package com.example.mypomodoro

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypomodoro.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TimerDatabaseHelper
    private lateinit var timerAdapter: TimerAdapter
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var timer: CountDownTimer

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var totalTimeInMinutes: Long = 1
    private val millisecondsInOneSecond: Long = 1000
    private val secondsInOneMinute: Long = 60
    private var totalTimeInMillis: Long = 1
    private var timeLeftInMillis: Long = totalTimeInMillis


    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val addButton = findViewById<Button>(R.id.addButton)

        db = TimerDatabaseHelper(this)
        timerAdapter = TimerAdapter(db.getAllTimers(), this)

        binding.timerRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.timerRecyclerView.adapter = timerAdapter

        addButton.setOnClickListener {
            val intent = Intent(this, AddTimerActivity::class.java)
            startActivity(intent)
        }

        val intent = intent
        val timText = intent.getIntExtra("id", 0)
        mediaPlayer = MediaPlayer.create(this, R.raw.beep_sound)

        var initialTimeInMillis: Long = (timText*60*1000).toLong()
        totalTimeInMinutes = timText.toLong()
        totalTimeInMillis = totalTimeInMinutes * secondsInOneMinute * millisecondsInOneSecond

        startButton = findViewById(R.id.timerStartButton)
        resetButton = findViewById(R.id.timerResetButton)

        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)

        startButton.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
            } else {
                if (initialTimeInMillis > 0)
                    startTimer(initialTimeInMillis)
            }
        }

        resetButton.setOnClickListener {
            // Reset the timer when the reset button is clicked
            if (progressText.text != "00:00")
            {
                timer.cancel()
                progressText.text = "$timText:00"
                initialTimeInMillis = (timText*60*1000).toLong()
                startButton.text = "Start"
                updateUI(0)
            }
        }
    }


    private fun startTimer(value: Long) {
        timer = object : CountDownTimer(value, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                progressText.text = "$minutes:${String.format("%02d", seconds)}"
                updateUI(millisUntilFinished)
            }
            override fun onFinish() {
                progressText.text = "00:00"
                isTimerRunning = false
                startButton.text = "Start"
                mediaPlayer.start()
                showToast("Timer Finished!")
                updateUI(0)
            }
        }
        timer.start()
        isTimerRunning = true
        startButton.text = "Stop"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun stopTimer() {
        timer?.cancel()
        progressText.text = "00:00"
        isTimerRunning = false
        startButton.text = "Start"
    }

    fun updateUI(timeLeftInMillis: Long) {
        val progress = ((timeLeftInMillis.toDouble() / totalTimeInMillis.toDouble()) * 100).toInt()
        progressBar.progress = progress
    }

    override fun onResume() {
        super.onResume()
        timerAdapter.refreshData(db.getAllTimers())
    }
}




