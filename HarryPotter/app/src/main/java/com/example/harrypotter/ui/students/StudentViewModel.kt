package com.example.harrypotter.ui.students

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.harrypotter.data.model.Student
import com.example.harrypotter.data.repository.StudentRepository
import com.example.harrypotter.ui.LoadingDialog
import com.example.harrypotter.utils.Coroutines
import com.example.harrypotter.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject


class StudentViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val house : String,
    val repository: StudentRepository,
    val dialog: LoadingDialog
) : ViewModel() {
    var title : String? = null
    private var studentList = MutableLiveData<List<Student>>()
    lateinit var response : Response<List<Student>>
    val _studentList : LiveData<List<Student>>
        get() = studentList
    init {

        title = house
        Coroutines.main {
            try{
                dialog.startDialog()
                response = repository.getStudent(house!!)
                studentList.value = response.body()
                dialog.dismissDialog()
            }
            catch (e : NoInternetException){
                dialog.dismissDialog()
                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            }

        }
    }

    fun getStudentDetails() : LiveData<List<Student>> = _studentList

}