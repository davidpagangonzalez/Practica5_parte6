package net.iessochoa.davidpagan.practica5.repository

import android.app.Application
import android.content.Context
import net.iessochoa.davidpagan.practica5.model.Tarea
import net.iessochoa.davidpagan.practica5.model.temp.ModelTempTarea

object Repositorio {

    //instancia al modelo
    private lateinit var modelTareas:ModelTempTarea
    //el context suele ser necesario para recuperar datos
    private lateinit var application: Application
    //inicio del objeto singleton
    operator fun invoke(context: Context){
        this.application= context.applicationContext as Application
        //iniciamos el modelo
        ModelTempTarea(application)
        modelTareas=ModelTempTarea
    }
    fun addTarea(tarea: Tarea)= modelTareas.addTarea(tarea)
    fun delTarea(tarea: Tarea)= modelTareas.delTarea(tarea)
    fun getAllTareas()=modelTareas.getAllTareas()
    fun getTareasFiltroSinPagar (soloSinPagar:Boolean)=
        modelTareas.getTareasFiltroSinPagar(soloSinPagar)


}