package com.example.mypomodoro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TimerAdapter (private var pomoTimers: List<PomoTimer>, context: Context) :
    RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {

        private var timerStarted = false
        private lateinit var serviceIntent: Intent
        private var time = 0.0
        private val db: TimerDatabaseHelper = TimerDatabaseHelper(context)

    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tname: TextView = itemView.findViewById(R.id.view_timer_name)
        val ftime: TextView = itemView.findViewById(R.id.view_focus_time)
        val rtime: TextView = itemView.findViewById(R.id.view_rest_time)
        val deleteB: ImageView = itemView.findViewById(R.id.deleteButton)
        val startB: ImageView = itemView.findViewById(R.id.startButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerViewHolder(view)
    }

    override fun getItemCount(): Int = pomoTimers.size

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val timer = pomoTimers[position]
        holder.tname.text = timer.timerName
        holder.ftime.text = timer.focusTime.toString()
        holder.rtime.text = timer.restTime.toString()

        holder.deleteB.setOnClickListener {
            db.deleteTimer(timer.timerName)
            refreshData(db.getAllTimers())
            Toast.makeText(holder.itemView.context, "Timer Deleted", Toast.LENGTH_SHORT).show()
        }

        holder.startB.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity::class.java).apply {
                putExtra("id", timer.focusTime)
            }
            holder.itemView.context.startActivity(intent)
        }
    }


    fun refreshData(newPomoTimers: List<PomoTimer>) {
        pomoTimers = newPomoTimers
        notifyDataSetChanged()
    }
}