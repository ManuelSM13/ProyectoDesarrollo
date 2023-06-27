package com.example.tove.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tove.R
import com.example.tove.databinding.FragmentNuevoEnfermedadBinding
import com.example.tove.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "json_enfermedad"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoEnfermedadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoEnfermedadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var json_enfermedad: String? = null
    private var param2: String? = null

    private var _binding: FragmentNuevoEnfermedadBinding? = null;
    private val binding get() = _binding!!

    private var idEnf:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_enfermedad = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNuevoEnfermedadBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_enfermedad != null){
            var gson = Gson()
            var objEnfermedad = gson.fromJson(json_enfermedad, Models.Enfermedad::class.java)

            idEnf = objEnfermedad.id
            binding.txtNombre.setText(objEnfermedad.nombre)
            binding.txtTipo.setText(objEnfermedad.tipo)
            binding.txtDescripcion.setText(objEnfermedad.descripcion)
        }

        binding.btnRegistrarEnfermedad.setOnClickListener{
            guardarDatos();
        }

        binding.buttonEliminar.setOnClickListener{
            eliminarEnfermedad();
        }

        //return inflater.inflate(R.layout.fragment_nuevo_paciente, container, false)
        return view
    }

    fun eliminarEnfermedad(){
        binding.buttonEliminar.setOnClickListener{
            if(idEnf !=0){
                var url="http://192.168.50.38:8000/api/eliminarenfermedad"//IP de mi casa
                //var url="http://192.168.56.1:8000/api/eliminarenfermedad"
                //var url="http://192.168.121.230:8000/api/eliminarenfermedad"

                val client = OkHttpClient()

                val formBoddy = FormBody.Builder()
                    .add("id", idEnf.toString())
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .header("Accept", "application/json")
                    .post(formBoddy)
                    .build()

                client.newCall(request).enqueue(object  : Callback {
                    override fun onFailure(call: Call, e: IOException){
                        activity?.runOnUiThread{
                            Toast.makeText(context, "Ocurrio un error" + e.message.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    override fun onResponse(call: Call, response: Response){
                        activity?.runOnUiThread{
                            activity?.onBackPressed()
                        }
                    }
                })
            }
        }
    }

    fun guardarDatos(){

        var url="http://192.168.50.38:8000/api/guardarenfermedad"//IP de mi casa
        //var url="http://192.168.56.1:8000/api/guardarenfermedad"
        //var url="http://192.168.121.230:8000/api/guardarenfermedad"

        val client = OkHttpClient()

        val formBoddy = FormBody.Builder()
            .add("id", idEnf.toString())
            .add("nombre", binding.txtNombre?.text.toString())
            .add("tipo", binding.txtTipo?.text.toString())
            .add("descripcion", binding.txtDescripcion?.text.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            .post(formBoddy)
            .build()

        var gson = Gson()

        client.newCall(request).enqueue(object  : Callback {
            override fun onFailure(call: Call, e: IOException){
                activity?.runOnUiThread{
                    Toast.makeText(context, "Ocurrio un error" + e.message.toString(), Toast.LENGTH_LONG).show();
                }
            }

            override fun onResponse(call: Call, response: Response){
                activity?.runOnUiThread{
                    activity?.onBackPressed()
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevoEnfermedadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoEnfermedadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}