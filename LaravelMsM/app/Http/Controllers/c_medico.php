<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\m_medico;

class c_medico extends Controller
{
    //
    public function save(Request $req){

        if($req->id==0){
            $medico = new m_medico();
        }else{
            $medico = m_medico::find($req->id);
        }        

        $medico->nombre = $req->nombre;
        $medico->cedula = $req->cedula;
        $medico->especialidad = $req->especialidad;
        $medico->turno = $req->turno;
        $medico->telefono = $req->telefono;
        $medico->email = $req->email;

        $medico->save();

        return "Ok";
    }

    public function list(Request $req){
        $medico = m_medico::all();

        return $medico;
    }

    public function delete(Request $req){
        $medico = m_medico::find($req->id);
        $medico->delete();
    }
}
