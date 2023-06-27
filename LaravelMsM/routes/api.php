<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\login_controller;
use App\Http\Controllers\c_cita;
use App\Http\Controllers\c_enfermedad;
use App\Http\Controllers\c_paciente;
use App\Http\Controllers\c_medico;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/login',[login_controller::class,'login']);

Route::post('/guardarcita',[c_cita::class,'save']);
Route::get('/listarcita',[c_cita::class,'list']);
Route::post('/eliminarcita',[c_cita::class,'delete']);

//-------------------------------------------------------------------

Route::post('/guardarenfermedad',[c_enfermedad::class,'save']);
Route::get('/listarenfermedad',[c_enfermedad::class,'list']);
Route::post('/eliminarenfermedad',[c_enfermedad::class,'delete']);

//-------------------------------------------------------------------

Route::post('/guardarmedico',[c_medico::class,'save']);
Route::get('/listarmedico',[c_medico::class,'list']);
Route::post('/eliminarmedico',[c_medico::class,'delete']);

//-------------------------------------------------------------------

Route::post('/guardarpaciente',[c_paciente::class,'save']);
Route::get('/listarpaciente',[c_paciente::class,'list']);
Route::post('/eliminarpaciente',[c_paciente::class,'delete']);

//-------------------------------------------------------------------