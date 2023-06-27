package com.example.tove.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tove.R
import com.example.tove.databinding.FragmentNuevoDoctorBinding
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
private const val ARG_PARAM1 = "json_doctor"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoDoctorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoDoctorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var json_doctor: String? = null
    private var param2: String? = null

    private var _binding: FragmentNuevoDoctorBinding? = null;
    private val binding get() = _binding!!

    private var idDoc:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_doctor = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNuevoDoctorBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_doctor != null){
            var gson = Gson()
            var objDoctor = gson.fromJson(json_doctor, Models.Medico::class.java)

            idDoc = objDoctor.id
            binding.txtNombre.setText(objDoctor.nombre)
            binding.txtCedula2.setText(objDoctor.cedula)
            binding.txtEspecialidad.setText(objDoctor.especialidad)
            binding.txtTurno2.setText(objDoctor.turno)
            binding.txtTelefono2.setText(objDoctor.telefono)
            binding.txtEmail2.setText(objDoctor.email)
        }

        binding.btnRegistrarDoctor.setOnClickListener{
            guardarDatos();
        }

        binding.buttonEliminar.setOnClickListener{
            eliminarDoctor();
        }

        //return inflater.inflate(R.layout.fragment_nuevo_paciente, container, false)
        return view
    }

    fun eliminarDoctor(){
        binding.buttonEliminar.setOnClickListener{
            if(idDoc !=0){
                var url="http://192.168.50.38:8000/api/eliminarmedico"//IP de mi casa
                //var url="http://192.168.56.1:8000/api/eliminarmedico"
                //var url="http://192.168.121.230:8000/api/eliminarmedico"

                val client = OkHttpClient()

                val formBoddy = FormBody.Builder()
                    .add("id", idDoc.toString())
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

        var url="http://192.168.50.38:8000/api/guardarmedico"//IP de mi casa
        //var url="http://192.168.56.1:8000/api/guardarmedico"
        //var url="http://192.168.121.230:8000/api/guardarmedico"

        val client = OkHttpClient()

        val formBoddy = FormBody.Builder()
            .add("id", idDoc.toString())
            .add("nombre", binding.txtNombre?.text.toString())
            .add("cedula", binding.txtCedula2?.text.toString())
            .add("especialidad", binding.txtEspecialidad?.text.toString())
            .add("turno", binding.txtTurno2?.text.toString())
            .add("telefono", binding.txtTelefono2?.text.toString())
            .add("email", binding.txtEmail2?.text.toString())
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
         * @return A new instance of fragment NuevoDoctorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoDoctorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}