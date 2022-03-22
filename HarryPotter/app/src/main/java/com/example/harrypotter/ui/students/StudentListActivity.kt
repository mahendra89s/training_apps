package com.example.harrypotter.ui.students

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypotter.R
import com.example.harrypotter.data.model.Student
import com.example.harrypotter.data.network.NetworkConnectionIntercepter
import com.example.harrypotter.data.repository.StudentRepository
import com.example.harrypotter.databinding.ActivityStudentListBinding
import com.example.harrypotter.ui.LoadingDialog
import com.example.harrypotter.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_student_list.*
import javax.inject.Inject

@AndroidEntryPoint
class StudentListActivity : AppCompatActivity(),StudentListClickListener {

    @Inject
    lateinit var dialogLoader: LoadingDialog
    @Inject
    lateinit var networkConnectionIntercepter: NetworkConnectionIntercepter
    @Inject
    lateinit var repository: StudentRepository


//    private val viewModel : StudentViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val house = intent.getStringExtra("house").toString()
        dialogLoader = LoadingDialog(this)
        networkConnectionIntercepter = NetworkConnectionIntercepter(this)
        repository = StudentRepository(networkConnectionIntercepter)

        val binding : ActivityStudentListBinding= DataBindingUtil.setContentView(this,R.layout.activity_student_list)
        val viewModel = ViewModelProvider(this,StudentViewModelFactory(applicationContext,house,repository,dialogLoader))[StudentViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.getStudentDetails().observe(this, { students ->
            recyclerview_students.also{
                val gridLayoutManager = GridLayoutManager(this,2)
                it.layoutManager = gridLayoutManager
                it.adapter = StudentAdapter(students,this)
            }
        })
    }

    override fun onClick(student: Student) {
        Intent(this,DetailActivity::class.java).also {
            it.putExtra("student",student)
            startActivity(it)
        }
    }
}