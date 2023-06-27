<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class login_controller extends Controller
{
    public function login(Request $request){
        if(Auth::attempt(['email' => $request->email, 'password' => $request->password])){
            $user = Auth::user();

            $user->tokens()->delete();

            $token = $user->createToken("AppMobile");

            $token = explode("|", $token->plainTextToken);

            $arr = array('idUsr' => $user->id, 'token' => $token[1],'nombre' => $user->name);

            return json_encode($arr);
        }else{
            $arr = array('idUsr' => 0,'token' => "",'nombre' => "",'error' => "No existe el usuario o la contraseña es invalida");
            return json_encode($arr);
        }
    }
}
