package com.example.tove.extras

class Models {
    data class RespLogin(
        var idUsr:Int,
        var token:String,
        var nombre:String,
        var error:String,
    )

    data class Paciente(
        var id:Int,
        var nombre: String,
        var NSS:String,
        var tipo_sangre:String,
        var alergias:String,
        var telefono:String,
        var domicilio:String,
    )

    data class Medico(
        var id:Int,
        var nombre: String,
        var cedula:String,
        var especialidad:String,
        var turno:String,
        var telefono:String,
        var email:String,
    )

    data class Enfermedad(
        var id:Int,
        var nombre: String,
        var tipo:String,
        var descripcion:String,
    )

    data class Cita(
        var id:Int,
        var consultorio: String,
        var fecha:String,
        var idEnf:Int,
        var idMed:Int,
        var idPác:Int,
    )
}