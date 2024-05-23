package net.iessochoa.davidpagan.practica5.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import net.iessochoa.davidpagan.practica5.databinding.FragmentTareaBinding
import net.iessochoa.davidpagan.practica5.ui.TareaFragmentArgs
import net.iessochoa.davidpagan.practica5.viewmodel.AppViewModel

@Entity(tableName = "tareas")
@Parcelize
data class Tarea(
@PrimaryKey(autoGenerate = true)
    var id:Long?=null,//id único
    val categoria:Int,
    val prioridad:Int,
    val pagado:Boolean,
    var estado:Int,
    val horasTrabajo:Int,
    val valoracionCliente:Float,
    val tecnico:String,
    val descripcion:String

):Parcelable
{
    //segundo constructor que genera id nuevo
    constructor( categoria:Int,
                 prioridad:Int,
                 pagado:Boolean,
                 estado:Int,
                 horasTrabajo:Int,
                 valoracionCliente:Float,
                 tecnico:String,
                 descripcion:String):this(null,categoria,prioridad,pagado,estado,horasTrabajo,valoracionCliente, tecnico, descripcion){}

    companion object {
        var idContador = 1L//iniciamos contador de tareas
        private fun generateId(): Long {
            return idContador++//sumamos uno al contador

        }
    }

    //dos tareas son iguales cuando su id es igual.
// Facilita la búsqueda en un arrayList
    override fun equals(other: Any?): Boolean {
        return (other is Tarea)&&(this.id == other.id)
    }
}
