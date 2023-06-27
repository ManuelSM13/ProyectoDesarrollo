package com.example.tove.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tove.R
import com.example.tove.databinding.FragmentGalleryBinding
import com.example.tove.extras.DoctorAdapter
import com.example.tove.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabNuevoDoctor.setOnClickListener{
            var navController = findNavController()
            navController.navigate(R.id.nav_nuevo_doctor)
        }


        obtenerDatos();

        return root
    }

    fun obtenerDatos(){

        var url="http://192.168.50.38:8000/api/listarmedico"//IP de mi casa
        //var url="http://192.168.56.1:8000/api/listarmedico"
        //var url="http://192.168.121.230:8000/api/listarmedico"

        val request = Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            .get()
            .build()
        val client = OkHttpClient()
        var gson = Gson()

        client.newCall(request).enqueue(object  : Callback {
            override fun onFailure(call: Call, e: IOException){
                activity?.runOnUiThread{
                    Toast.makeText(context, "Ocurrio un error" + e.message.toString(), Toast.LENGTH_LONG).show();
                }
            }

            override fun onResponse(call: Call, response: Response){
                //
                // println(response.body?.string())
                var respuesta = response.body?.string();

                println("Respuesta: "+respuesta)
                activity?.runOnUiThread{
                    var listItems = gson.fromJson(respuesta, Array<Models.Medico>::class.java)

                    val adapter = DoctorAdapter(listItems.toMutableList())
                    binding.rvDatosDoctor.layoutManager = LinearLayoutManager(context)
                    binding.rvDatosDoctor.adapter=adapter
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}