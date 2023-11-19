package net.iessochoa.davidpagan.practica5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.iessochoa.davidpagan.practica5.model.Tarea
import net.iessochoa.davidpagan.practica5.repository.Repositorio

class AppViewModel(application: Application) : AndroidViewModel(application) {
        //repositorio
        private val repositorio:Repositorio
        //liveData de lista de tareas
        val tareasLiveData : LiveData<List<Tarea>>
        //inicio ViewModel
        init {
            //inicia repositorio
            Repositorio(getApplication<Application>().applicationContext)
            repositorio=Repositorio
            tareasLiveData =repositorio.getAllTareas()
        }
        fun addTarea(tarea: Tarea) = repositorio.addTarea(tarea)
        fun delTarea(tarea: Tarea) = repositorio.delTarea(tarea)
    }