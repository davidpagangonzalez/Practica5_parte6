package net.iessochoa.davidpagan.practica5.repository

import android.app.Application
import android.content.Context
import net.iessochoa.davidpagan.practica5.model.Tarea
import net.iessochoa.davidpagan.practica5.model.db.TareasDao
import net.iessochoa.davidpagan.practica5.model.db.TareasDataBase
import net.iessochoa.davidpagan.practica5.model.temp.ModelTempTarea

object Repositorio {

    //instancia al modelo
    private lateinit var modelTareas:TareasDao

    //el context suele ser necesario para recuperar datos
    private lateinit var application: Application
    //inicio del objeto singleton
    operator fun invoke(context: Context){
        this.application= context.applicationContext as Application
        //iniciamos el modelo
        //ModelTempTarea(application)
        //Iniciamos base de datos
        modelTareas=TareasDataBase.TareasDataBase.getDatabase(application).tareasDao()
    }
    suspend fun addTarea(tarea: Tarea)= modelTareas.addTarea(tarea)
    suspend fun delTarea(tarea: Tarea)= modelTareas.delTarea(tarea)
    fun getAllTareas()=modelTareas.getAllTareas()
    fun getTareasFiltroEstado (estado:Int) = modelTareas.getTareasFiltroEstado(estado)
    fun getTareasFiltroSinPagar (soloSinPagar:Boolean)= modelTareas.getTareasFiltroSinPagar(soloSinPagar)
    fun getTareasFiltroSinPagarEstado(soloSinPagar:Boolean, estado:Int)= modelTareas.getTareasFiltroSinPagarEstado(soloSinPagar,estado)
    fun updateTarea(tarea: Tarea) {
        modelTareas.updateTarea(tarea)
    }
}