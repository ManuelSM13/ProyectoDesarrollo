<?php

namespace App\Http\Controllers;

use App\Models\m_paciente;
use Illuminate\Http\Request;

class c_paciente extends Controller
{
    //

    public function save(Request $req){

        if($req->id==0){
            $paciente = new m_paciente();
        }else{
            $paciente = m_paciente::find($req->id);
        }        

        $paciente->nombre = $req->nombre;
        $paciente->NSS = $req->NSS;
        $paciente->tipo_sangre = $req->tipo_sangre;
        $paciente->alergias = $req->alergias;
        $paciente->telefono = $req->telefono;
        $paciente->domicilio = $req->domicilio;

        $paciente->save();

        return "Ok";
    }

    public function list(Request $req){
        $paciente = m_paciente::all();

        return $paciente;
    }

    public function delete(Request $req){
        $paciente = m_paciente::find($req->id);
        $paciente->delete();
    }
}
