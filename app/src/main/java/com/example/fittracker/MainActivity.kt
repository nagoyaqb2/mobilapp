package com.example.fittracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.db.NULL
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var workouts = ArrayList<Workout>()

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        buttonPrevWorkout.setOnClickListener {
            startActivity<ListWorkoutActivity>()
        }

        fabMain.setOnClickListener {
            startActivity<AddWorkoutActivity>()
        }

        var sum = getSumOfWorkouts()
        editSumOfWorkoutText.setText(sum.toString())

        var latest = getLatestWorkout()
        editLatestWorkoutText.setText(latest)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> {
                startActivity<MainActivity>()
            }
            R.id.nav_workouts -> {
                startActivity<ListWorkoutActivity>()
            }
            R.id.nav_friends -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        var sum = getSumOfWorkouts()
        editSumOfWorkoutText.setText(sum.toString())

        var latest = getLatestWorkout()
            editLatestWorkoutText.setText(latest)
    }

    private fun getSumOfWorkouts(): Int {
        database.use {
            workouts.clear()
            var result = select(Workout.TABLE_WORKOUT)
            var workoutData = result.parseList(classParser<Workout>())
            workouts.addAll(workoutData)
        }
        if (workouts.size == 0){
            return 0
        } else {
            return workouts.size
        }
    }

    private fun getLatestWorkout(): String {
        database.use {
            workouts.clear()
            var result = select(Workout.TABLE_WORKOUT)
            var workoutData = result.parseList(classParser<Workout>())
            workouts.addAll(workoutData)
        }
        if (workouts.size == 0) {
            return "No previous activity"
        } else {
            return workouts.last().name.toString()
        }
    }
}