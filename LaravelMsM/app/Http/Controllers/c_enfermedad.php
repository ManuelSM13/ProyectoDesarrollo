<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
//use App\Models\m_enfermedad;
use App\Models\m_enfermedad;

class c_enfermedad extends Controller
{
    //
    public function save(Request $req){

        if($req->id==0){
            $enfermedad = new m_enfermedad();
        }else{
            $enfermedad = m_enfermedad::find($req->id);
        }        

        $enfermedad->nombre = $req->nombre;
        $enfermedad->tipo = $req->tipo;
        $enfermedad->descripcion = $req->descripcion;

        $enfermedad->save();

        return "Ok";
    }

    public function list(Request $req){
        $enfermedad = m_enfermedad::all();

        return $enfermedad;
    }

    public function delete(Request $req){
        $enfermedad = m_enfermedad::find($req->id);
        $enfermedad->delete();
    }
}
