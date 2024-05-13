package net.iessochoa.davidpagan.practica5.ui

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

    private fun iniciaRecyclerView() {
        //creamos el adaptador
        tareasAdapter = TareasAdapter()

        with(binding.rvTarea) {
            //Creamos el layoutManager
            layoutManager = LinearLayoutManager(activity)
            //le asignamos el adaptador
            adapter = tareasAdapter
        }
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
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