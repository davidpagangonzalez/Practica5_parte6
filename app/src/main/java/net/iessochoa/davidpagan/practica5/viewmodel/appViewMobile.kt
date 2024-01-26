package net.iessochoa.davidpagan.practica5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import net.iessochoa.davidpagan.practica5.model.Tarea
import net.iessochoa.davidpagan.practica5.repository.Repositorio

class AppViewModel(application: Application) : AndroidViewModel(application) {
        //repositorio
        private val repositorio:Repositorio
        //liveData de lista de tareas
        val tareasLiveData : LiveData<List<Tarea>>
        //creamos el LiveData de tipo Booleano. Representa nuestro filtro
        private val soloSinPagarLiveData= MutableLiveData<Boolean>(false)

    //inicio ViewModel
    init {
            //inicia repositorio
            Repositorio(getApplication<Application>().applicationContext)
            repositorio=Repositorio
            tareasLiveData=soloSinPagarLiveData.switchMap {soloSinPagar-> Repositorio.getTareasFiltroSinPagar(soloSinPagar)}
        }
    /**
     * activa el LiveData del filtro
     */
    fun setSoloSinPagar(soloSinPagar:Boolean){soloSinPagarLiveData.value=soloSinPagar}
    fun addTarea(tarea: Tarea) = repositorio.addTarea(tarea)
    fun delTarea(tarea: Tarea) = repositorio.delTarea(tarea)


    }