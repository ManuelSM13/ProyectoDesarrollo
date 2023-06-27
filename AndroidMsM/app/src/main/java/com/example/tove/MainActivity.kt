package com.example.tove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tove.databinding.ActivityMainBinding
import com.example.tove.extras.Models
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnAccesar.setOnClickListener{
            fnlogin();
        }

        setContentView(view)
    }



    fun fnlogin(){
        //Toast.makeText(baseContext, binding.txtUsername?.text, Toast.LENGTH_LONG).show();
        //Toast.makeText(baseContext, binding.txtPassword?.text, Toast.LENGTH_LONG).show();

        var url="http://192.168.50.38:8000/api/login"//IP de mi casa
        //var url="http://192.168.56.1:8000/api/login"
        //var url="http://192.168.121.230:8000/api/login"

        val client = OkHttpClient()

        val formBoddy = FormBody.Builder()
            .add("email", binding.txtUsername?.text.toString())
            .add("password", binding.txtPassword?.text.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBoddy)
            .build()

        client.newCall(request).enqueue(object  : Callback{
            override fun onFailure(call: Call, e: IOException){
                runOnUiThread{
                    Toast.makeText(baseContext, "Ocurrio un error" + e.message.toString(), Toast.LENGTH_LONG).show();
                }
            }

            override fun onResponse(call: Call, response: Response){
                //
                // println(response.body?.string())

                var objGson = Gson();
                var respuesta = response.body?.string();

                //println(respuesta);

                var objResp = objGson.fromJson(respuesta, Models.RespLogin::class.java)


                if(objResp.token == ""){
                    runOnUiThread{
                        Toast.makeText(baseContext, objResp.error, Toast.LENGTH_LONG).show();
                    }
                } else{

                    val intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(intent)

                runOnUiThread{
                        Toast.makeText(baseContext, "Acceso correcto", Toast.LENGTH_LONG).show();
                    }
                }
            }
        })
    }
}