package net.iessochoa.davidpagan.practica5.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.davidpagan.practica5.R
import net.iessochoa.davidpagan.practica5.databinding.ItemTareaBinding
import net.iessochoa.davidpagan.practica5.model.Tarea

class TareasAdapter ():
    RecyclerView.Adapter<TareasAdapter.TareaViewHolder>() {
    lateinit var listaTareas: List<Tarea>

    fun setLista(lista: List<Tarea>) {
        listaTareas = lista
        //notifica al adaptador que hay cambios y tiene que redibujar el ReciclerView
        notifyDataSetChanged()
    }

    inner class TareaViewHolder(val binding: ItemTareaBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TareaViewHolder {
        //utilizamos binding, en otro caso hay que indicar el item.xml. Para más detalles puedes verlo en la documentación
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TareaViewHolder(binding)


    }

    override fun onBindViewHolder(tareaViewHolder: TareaViewHolder, pos: Int) {
        //Nos pasan la posición del item a mostrar en el viewHolder
        with(tareaViewHolder) {
            //cogemos la tarea a mostrar y rellenamos los campos del ViewHolder
            with(listaTareas!!.get(pos)) {
                binding.tvId.text = id.toString()
                binding.tvDescripcion.text = descripcion
                binding.tvTecnico.text = tecnico
                binding.rbValoracion.rating = valoracionCliente
                //mostramos el icono en función del estado
                binding.ivEstado.setImageResource(
                    when (estado) {
                        0 -> R.drawable.alien
                        1 -> R.drawable.allergy
                        else -> R.drawable.alien_outline
                    }
                )
                //cambiamos el color de fondo si la prioridad es alta
                binding.cvItem.setBackgroundResource(
                    if (prioridad == 2)//prioridad alta
                        R.color.prioridad_alta
                    else
                        Color.TRANSPARENT
                )
            }
        }
    }

    interface OnTareaClickListener{
        //editar tarea que contiene el ViewHolder
        fun onTareaClick(tarea:Tarea?)
        //borrar tarea que contiene el ViewHolder
        fun onTareaBorrarClick(tarea:Tarea?)
    }

    //tamaño de la lista
    override fun getItemCount(): Int = listaTareas?.size?:0

}

