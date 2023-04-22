package com.example.rosles.Screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.databinding.AuthorizationActivityBinding


class Authorization: AppCompatActivity() {
    private lateinit var binding: AuthorizationActivityBinding
    val viewModel by viewModels<ViewModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            viewModel.get_user(
                AuthRequest(
                    binding.login.editUser.text.toString(),
                    binding.pass.editUser.text.toString()
                )
            )
            viewModel.user.observe(this) {
                saveText(it.id, it.FIO)
                startActivity(Intent(this, Dashboard::class.java))
            }
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