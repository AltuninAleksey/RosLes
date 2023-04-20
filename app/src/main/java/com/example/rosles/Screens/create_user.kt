package com.example.rosles.Screens

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.databinding.CreateUserBinding

class create_user:AppCompatActivity() {


    private lateinit var binding: CreateUserBinding
    val viewModel by viewModels<ViewModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = CreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.name.editUser.requestFocus()

        val wordtwo= SpannableString("Войдите")
        wordtwo.setSpan(
            ForegroundColorSpan(Color.parseColor("#177164")),0,wordtwo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.createuser.append(wordtwo)

        binding.createuser.setOnClickListener(){
            finish()
        }

        initview()
//        initcorutine()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

        }
        return true
    }


    fun  initview(){
        binding.name.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_input_form))
        binding.filial.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_gray))
        binding.password.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lock))
        binding.passwordapply.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lock))

        binding.name.editUser.setHint("ФИО")
        binding.filial.editUser.setHint("Телефон")
        binding.email.editUser.setHint("Email")
        binding.password.editUser.setHint("Password")
        binding.passwordapply.editUser.setHint("Confirm Password")

        binding.CreateSave.setOnClickListener{
            with(binding){
                if (password.editUser.text.toString()==passwordapply.editUser.text.toString()) {
                    val body = RegistrationReqest(
                        email.editUser.text.toString(),
                        password.editUser.text.toString(),
                        name.editUser.text.toString(),
                        filial.editUser.text.toString()
                    )
                    var a=viewModel.registration(body)

                }
                else{
                    Toast.makeText(this@create_user, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                }
            }



            //магия вне хогвартса отправка анкеты на сервер вжух хуяк-хуяк пользователь есть
            //ахуеть папаша вот это паштет навалилы базы + и метода
        }
    }
}
