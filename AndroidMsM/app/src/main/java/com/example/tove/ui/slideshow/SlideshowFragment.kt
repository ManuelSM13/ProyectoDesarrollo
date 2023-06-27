package com.example.tove.ui.slideshow

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
import com.example.tove.databinding.FragmentSlideshowBinding
import com.example.tove.extras.EnfermedadAdapter
import com.example.tove.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabNuevoEnfermedad.setOnClickListener{
            var navController = findNavController()
            navController.navigate(R.id.nav_nuevo_enfermedad)
        }

        obtenerDatos();

        return root
    }


    fun obtenerDatos(){

        var url="http://192.168.50.38:8000/api/listarenfermedad"//IP de mi casa
        //var url="http://192.168.56.1:8000/api/listarenfermedad"
        //var url="http://192.168.121.230:8000/api/listarenfermedad"

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
                    var listItems = gson.fromJson(respuesta, Array<Models.Enfermedad>::class.java)

                    val adapter = EnfermedadAdapter(listItems.toMutableList())
                    binding.rvDatosEnfermedad.layoutManager = LinearLayoutManager(context)
                    binding.rvDatosEnfermedad.adapter=adapter
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}