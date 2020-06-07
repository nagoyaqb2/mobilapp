package com.example.fittracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_workout.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RVAdapter(val context: Context, val items: ArrayList<Workout>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(items: Workout) {
            itemView.workoutName.text = items.name
            itemView.workoutDate.text = items.date

            itemView.buttonDelete.setOnClickListener {
                itemView.context.database.use {
                    delete(Workout.TABLE_WORKOUT, "(${Workout.NAME} = {name})",
                        "name" to items.name.toString())
                }
                itemView.context.toast("Workout deleted")

                itemView.context.startActivity<MainActivity>()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }
}