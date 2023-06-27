package com.example.tove.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tove.HomeActivity
import com.example.tove.R
import com.example.tove.databinding.ActivityMainBinding
import com.example.tove.databinding.FragmentNuevoPacienteBinding
import com.example.tove.extras.Models
import com.example.tove.extras.PacienteAdapter
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
private const val ARG_PARAM1 = "json_paciente"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoPacienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoPacienteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var json_paciente: String? = null
    private var param2: String? = null

    private var _binding: FragmentNuevoPacienteBinding? = null;
    private val binding get() = _binding!!

    private var idPac:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_paciente = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentNuevoPacienteBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_paciente != null){
            var gson = Gson()
            var objPaciente = gson.fromJson(json_paciente, Models.Paciente::class.java)

            idPac = objPaciente.id
            binding.txtNombre.setText(objPaciente.nombre)
            binding.txtNSS.setText(objPaciente.NSS)
            binding.txtsangre.setText(objPaciente.tipo_sangre)
            binding.txtAlergias.setText(objPaciente.alergias)
            binding.txtTelefono.setText(objPaciente.telefono)
            binding.txtDomicilio.setText(objPaciente.domicilio)
        }

        binding.btnRegistrarPaciente.setOnClickListener{
            guardarDatos();
        }

        binding.buttonEliminar.setOnClickListener{
            eliminarPaciente();
        }

        //return inflater.inflate(R.layout.fragment_nuevo_paciente, container, false)
        return view
    }

    fun eliminarPaciente(){
        binding.buttonEliminar.setOnClickListener{
            if(idPac !=0){
                var url="http://192.168.50.38:8000/api/eliminarpaciente"//IP de mi casa
                //var url="http://192.168.56.1:8000/api/eliminarpaciente"
                //var url="http://192.168.121.230:8000/api/eliminarpaciente"

                val client = OkHttpClient()

                val formBoddy = FormBody.Builder()
                    .add("id", idPac.toString())
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .header("Accept", "application/json")
                    .post(formBoddy)
                    .build()

                client.newCall(request).enqueue(object  : Callback{
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

        var url="http://192.168.50.38:8000/api/guardarpaciente"//IP de mi casa
        //var url="http://192.168.56.1:8000/api/guardarpaciente"
        //var url="http://192.168.121.230:8000/api/guardarpaciente"

        val client = OkHttpClient()

        val formBoddy = FormBody.Builder()
            .add("id", idPac.toString())
            .add("nombre", binding.txtNombre?.text.toString())
            .add("NSS", binding.txtNSS?.text.toString())
            .add("tipo_sangre", binding.txtsangre?.text.toString())
            .add("alergias", binding.txtAlergias?.text.toString())
            .add("telefono", binding.txtTelefono?.text.toString())
            .add("domicilio", binding.txtDomicilio?.text.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            .post(formBoddy)
            .build()

        var gson = Gson()

        client.newCall(request).enqueue(object  : Callback{
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
         * @return A new instance of fragment NuevoPacienteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoPacienteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}