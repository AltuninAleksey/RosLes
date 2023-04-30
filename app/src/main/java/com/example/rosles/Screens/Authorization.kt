package com.example.rosles.Screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.rosles.Network.*
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.ResponceClass.AuthReSponce
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.databinding.AuthorizationActivityBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Authorization: AppCompatActivity() {
    private lateinit var binding: AuthorizationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<ViewModels>() // Q
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        CheckUser()
        binding = AuthorizationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLogin.setOnClickListener {
            if (binding.login.editUser.text.isEmpty()){
                binding.emaileror.visibility=View.VISIBLE
            }else{
                binding.emaileror.visibility=View.GONE
            }
            if (binding.pass.editUser.text.isEmpty()){
                binding.passeror.visibility=View.VISIBLE
            }else{
                binding.passeror.visibility=View.GONE
            }

            SafeRequest(viewModel).request(object : SafeRequest.Protection{

                override suspend fun makeRequest(): BaseResponceInterface {
                    val user = SourceProviderHolder.sourcesProvider.getAccountsSource().get_user(
                        AuthRequest(
                            binding.login.editUser.text.toString(),
                            binding.pass.editUser.text.toString()
                        ))
                    return user
                }

                override fun ifSuccess(responce: BaseResponceInterface?) {
                    if (responce != null && responce is AuthReSponce)
                        saveText(responce.id, responce.FIO)
                    Toast.makeText(this@Authorization, "Вы авторизовались", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Authorization, Dashboard::class.java))
                }

                override fun ifException() {
                    Toast.makeText(this@Authorization, "Не верный логин или пароль", Toast.LENGTH_SHORT).show()
                }

                override fun ifConnectionException() {
                    Toast.makeText(this@Authorization, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
                }

            })

        }



        val wordtwo=SpannableString("Зарегистрируйтесь")
        wordtwo.setSpan(ForegroundColorSpan(Color.parseColor("#177164")),0,wordtwo.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.createuser.append(wordtwo)

        binding.createuser.setOnClickListener{
            startActivity(Intent(this, create_user::class.java))
        }
    }

    fun CheckUser(){
        val sPref =getSharedPreferences("PreferencesName", MODE_PRIVATE)
        val savedLogin = sPref.getString("id", "")
        val savedPassword = sPref.getString("FIO", "")
        if (savedLogin != "" && savedPassword!=""){
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }
    }
    fun saveText(id:Int,FIO:String) {
        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putString("id", id.toString())
        ed.putString("FIO", FIO)
        ed.apply()
        finish()
        startActivity(Intent(this, Dashboard::class.java))
    }


}