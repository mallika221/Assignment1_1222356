package com.example.assignment1_mallika

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskEditText: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var addTaskButton: Button
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter

    private val tasks = mutableListOf<Task>()
    private var selectedPriority = "Low"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskEditText = findViewById(R.id.taskEditText)
        prioritySpinner = findViewById(R.id.prioritySpinner)
        addTaskButton = findViewById(R.id.addTaskButton)
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)

        // Set up the spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }

        // Set up the spinner listener
        prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedPriority = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Set up the button listener
        addTaskButton.setOnClickListener {
            val taskName = taskEditText.text.toString()
            if (taskName.isNotEmpty()) {
                tasks.add(Task(taskName, selectedPriority))
                tasksAdapter.notifyDataSetChanged()
                taskEditText.text.clear()
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the RecyclerView
        tasksAdapter = TasksAdapter(tasks)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = tasksAdapter
    }
}