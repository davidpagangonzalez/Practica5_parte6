package net.iessochoa.davidpagan.practica5.ui

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import net.iessochoa.davidpagan.practica5.R
import net.iessochoa.davidpagan.practica5.adapters.TareasAdapter
import net.iessochoa.davidpagan.practica5.databinding.FragmentListaBinding
import net.iessochoa.davidpagan.practica5.model.Tarea
import net.iessochoa.davidpagan.practica5.viewmodel.AppViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListaFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels()

    private var _binding: FragmentListaBinding? = null

    lateinit var tareasAdapter: TareasAdapter

    private fun cambiarEstado(tarea: Tarea) {
        val nEstado = when (tarea.estado) {
            0 -> 1
            1 -> 2
            2 -> 0
            else -> tarea.estado
        }
        val tareaNuevaEstado = tarea.copy(estado = nEstado)
        viewModel.updateTarea(tareaNuevaEstado)
    }
    private fun iniciaCRUD(){
        binding.fabNuevo.setOnClickListener(){
            val action=ListaFragmentDirections.actionEditar(null)
            findNavController().navigate(action)
        }

        tareasAdapter.onTareaClickListener = object : TareasAdapter.OnTareaClickListener {
            //**************Editar Tarea*************
            override fun onTareaClick(tarea: Tarea?) {
                //creamos la acción y enviamos como argumento la tarea para editarla
                val action = ListaFragmentDirections.actionEditar(tarea)
                findNavController().navigate(action)
            }
            //***********Borrar Tarea************
            override fun onTareaBorrarClick(tarea: Tarea?) {
                //borramos directamente la tarea
                borrarTarea(tarea!!)
            }

            override fun onTareaEstadoClick(tarea: Tarea?) {
                cambiarEstado(tarea!!)
            }

        }
    }
    fun borrarTarea(tarea:Tarea){
        AlertDialog.Builder(activity as Context)
            .setTitle(android.R.string.dialog_alert_title)
            //recuerda: todo el texto en string.xml
            .setMessage("Desea borrar la Tarea ${tarea.id}?")
            //acción si pulsa si
            .setPositiveButton(android.R.string.ok){v,_->
                //borramos la tarea
                viewModel.delTarea(tarea)
                //cerramos el dialogo
                v.dismiss()
            }
            //accion si pulsa no
            .setNegativeButton(android.R.string.cancel){v,_->v.dismiss()}
            .setCancelable(false)
            .create()
            .show()
    }

    private fun iniciaRecyclerView() {
        //creamos el adaptador
        tareasAdapter = TareasAdapter()

        with(binding.rvTarea) {
            val orientation=resources.configuration.orientation
            layoutManager =if(orientation== Configuration.ORIENTATION_PORTRAIT)
            //Vertical: lista con una colummna
                LinearLayoutManager(activity)
            else//Horizontal: lista con dos columnas
                GridLayoutManager(activity,2)
            adapter = tareasAdapter
        }
    }

    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaRecyclerView()
        iniciaFiltros()
        iniciaCRUD()

        viewModel.tareasLiveData.observe(viewLifecycleOwner,
            Observer<List<Tarea>> { lista ->
                //actualizaLista(lista)
                tareasAdapter.setLista(lista)
            
            })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun iniciaFiltros(){
        binding.swSinPagar.setOnCheckedChangeListener( ) { _,isChecked->
            //actualiza el LiveData SoloSinPagarLiveData que a su vez modifica tareasLiveData
            //mediante el Transformation
            viewModel.setSoloSinPagar(isChecked)}
        iniciaFiltroEstado()


    }
    private fun iniciaFiltroEstado(){
        binding.rb0.setOnCheckedChangeListener(){_, isChecked->
            viewModel.setEstado(0)
        }
        binding.rb1.setOnCheckedChangeListener(){_, isChecked->
            viewModel.setEstado(1)
        }
        binding.rb2.setOnCheckedChangeListener(){_, isChecked->
            viewModel.setEstado(2)
        }
        binding.rb3.setOnCheckedChangeListener(){_, isChecked->
            viewModel.setEstado(3)
        }
    }

    private fun actualizaLista(lista: List<Tarea>?) {
        //creamos un string modificable
        val listaString = buildString {
            lista?.forEach() {
                //añadimos al final del string
                append(
                    "${it.id}-${it.tecnico}-${
                        //mostramos un trozo de la descripción
                        if (it.descripcion.length < 21) it.descripcion
                        else
                            it.descripcion.subSequence(0, 20)
                    }-${
                        if (it.pagado) "SI-PAGADO" else
                            "NO-PAGADO"
                    }-" + when (it.estado) {
                        0 -> "ABIERTA"
                        1 -> "EN_CURSO"
                        else -> "CERRADA"
                    } + "\n"
                )
            }
        }
        //binding.tvListaTareas.setText(listaString)
    }
}