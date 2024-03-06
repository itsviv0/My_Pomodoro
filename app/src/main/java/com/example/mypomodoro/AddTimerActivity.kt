package com.example.mypomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.mypomodoro.databinding.ActivityAddTimerBinding

class AddTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTimerBinding
    private lateinit var titleEditText: EditText
    private lateinit var timerSeekBar: SeekBar
    private lateinit var restSeekBar: SeekBar
    private var etFocus: Int? = null
    private var etRest: Int? = null
    private lateinit var db: TimerDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TimerDatabaseHelper(this)

        titleEditText = findViewById(R.id.edit_text_title)
        timerSeekBar = findViewById(R.id.seek_bar_timer)
        restSeekBar = findViewById(R.id.seek_bar_rest)
        val timerMins = findViewById<TextView>(R.id.num_timer)
        val restMins = findViewById<TextView>(R.id.num_rest)

        timerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timerMins.text = (progress * 5).toString()
                etFocus = (timerSeekBar.progress) * 5
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        restSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                restMins.text = progress.toString()
                etRest = restSeekBar.progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.saveButton.setOnClickListener {
            if (titleEditText.text.equals("") || titleEditText.text.isEmpty() || titleEditText.text == null) {
                if (titleEditText.text.equals("") || titleEditText.text.isEmpty() || titleEditText.text == null)
                    titleEditText.error = "Please Enter Timer Name"
            } else {
                val Tname = titleEditText.text.toString()
                val Ftime = etFocus!!.toInt()
                val Rtime = etRest!!.toInt()
                val pomoTimer = PomoTimer(Tname, Ftime, Rtime)
                this.db.insertTimer(pomoTimer)
                finish()
                Toast.makeText(this, "Pomodoro Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}