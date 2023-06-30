<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\m_cita;

class c_cita extends Controller
{
        //se van a hacer algunos cambios here
        public function save(Request $req){

            if($req->id==0){
                $cita = new m_cita();
            }else{
                $cita = m_cita::find($req->id);
            }        
    
            $cita->consultorio = $req->consultorio;
            $cita->fecha = $req->fecha;
            
            $cita->id_enfermedad = $req->id_enfermedad;
            $cita->id_doctor = $req->id_doctor;
            $cita->id_paciente = $req->id_paciente;
    
            $cita->save();
    
            return "Okeyyyyy";
        }
    
        public function list(Request $req){
            $cita = m_cita::join('enfermedad','cita.id_enfermedad','=','enfermedad.id')
                                ->join('medico','cita.id_doctor','=','medico.id')
                                ->join('paciente','cita.id_paciente','=','paciente.id')
                                ->select('cita.*','enfermedad.nombre as nombre_enfermedad','cita.*','medico.nombre as nombre_medico','cita.*','paciente.nombre as nombre_paciente')                                    
                                ->get();
            //$cita = m_cita::all();
    
            return $cita;
        }
    
        public function delete(Request $req){
            $cita = m_cita::find($req->id);
            $cita->delete();
        }
}
