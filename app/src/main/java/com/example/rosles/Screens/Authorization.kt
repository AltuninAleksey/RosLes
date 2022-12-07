package com.example.rosles.Screens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.databinding.AuthorizationActivityBinding


class Authorization: AppCompatActivity() {
    private lateinit var binding: AuthorizationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = AuthorizationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CheckUser()
        binding.buttonLogin.setOnClickListener{
            if (binding.editUser.text.toString()=="L"&&binding.editPassword.text.toString()=="1234"){
                startActivity(Intent(this, MainActivity::class.java))
                saveText()}
        }
    }
    fun CheckUser(){
        var sPref =getPreferences(MODE_PRIVATE)
        var  savedLogin = sPref.getString("Login", "")
        var  savedPassword = sPref.getString("Password", "")
        if (savedLogin != "" && savedPassword!="")
            startActivity(Intent(this, MainActivity::class.java))
    }
    fun saveText() {
        var sPref = getPreferences(MODE_PRIVATE);
        var ed = sPref.edit();
        ed.putString("Login", binding.editUser.text.toString())
        ed.putString("Password", binding.editPassword.text.toString())
        ed.commit()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }
}