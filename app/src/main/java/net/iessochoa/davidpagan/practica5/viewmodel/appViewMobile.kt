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
    //LiveData que cuando se modifique un filtro cambia el tareasLiveData
    val SOLO_SIN_PAGAR="SOLO_SIN_PAGAR"
    val ESTADO="ESTADO"

        public val filtrosLiveData by lazy {//inicio tardío
        val mutableMap = mutableMapOf<String, Any?>(
            SOLO_SIN_PAGAR to false,
            ESTADO to 3
        )
        val mutableLiveData : MutableLiveData<Int>
        MutableLiveData(mutableMap)
    }
    //inicio ViewModel
    init {
            //inicia repositorio
            Repositorio(getApplication<Application>().applicationContext)
            repositorio=Repositorio

            //tareasLiveData=soloSinPagarLiveData.switchMap {soloSinPagar-> Repositorio.getTareasFiltroSinPagar(soloSinPagar)}
        tareasLiveData=filtrosLiveData.switchMap{ mapFiltro ->
            val aplicarSinPagar = mapFiltro!![SOLO_SIN_PAGAR] as Boolean
            val estado = mapFiltro!![ESTADO] as Int
            //Devuelve el resultado del when
            when {//trae toda la lista de tareas
                (!aplicarSinPagar && (estado == 3)) ->
                    repositorio.getAllTareas()
                //Sólo filtra por ESTADO
                (!aplicarSinPagar && (estado != 3)) ->
                    repositorio.getTareasFiltroEstado(estado)
                //Sólo filtra SINPAGAR
                (aplicarSinPagar && (estado == 3)) ->
                    repositorio.getTareasFiltroSinPagar(
                        aplicarSinPagar
                    )//Filtra por ambos
                else ->
                    repositorio.getTareasFiltroSinPagarEstado(aplicarSinPagar, estado)
            }
        }

    }
    /**
     * activa el LiveData del filtro
     */

    fun addTarea(tarea: Tarea) = repositorio.addTarea(tarea)
    fun delTarea(tarea: Tarea) = repositorio.delTarea(tarea)

    /**
     * Modifica el Map filtrosLiveData el elemento "SOLO_SIN_PAGAR"
     * que activará el Transformations de TareasLiveData
     */
    fun setSoloSinPagar(soloSinPagar: Boolean) {
        //recuperamos el map
        val mapa = filtrosLiveData.value
        //modificamos el filtro
        mapa!![SOLO_SIN_PAGAR] = soloSinPagar
        //activamos el LiveData
        filtrosLiveData.value = mapa
    }
    /**
     * Modifica el Map filtrosLiveData el elemento "ESTADO"
     * que activará el Transformations de TareasLiveData lo
     *llamamos cuando cambia el RadioButton
     */
    fun setEstado(estado: Int) {
        //recuperamos el map
        val mapa = filtrosLiveData.value
        //modificamos el filtro
        mapa!![ESTADO] = estado
        //activamos el LiveData
        filtrosLiveData.value = mapa
    }
}