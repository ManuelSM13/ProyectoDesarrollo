<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::table('cita', function (Blueprint $table){
            $table->bigInteger('id_enfermedad')->nullable();
            $table->foreign('id_enfermedad')->references('id')->on('enfermedad');
            //
            $table->bigInteger('id_doctor')->nullable();
            $table->foreign('id_doctor')->references('id')->on('medico');
            //
            $table->bigInteger('id_paciente')->nullable();
            $table->foreign('id_paciente')->references('id')->on('paciente');
            //
        });
        //
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        //
    }
};
