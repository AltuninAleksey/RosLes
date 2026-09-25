package com.example.rosles.Screens.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.ResponceClass.AuthReSponce
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.Screens.Dashboard
import com.example.rosles.Screens.StartScreen
import com.example.rosles.Screens.create_user
import com.example.rosles.databinding.AuthorizationActivityBinding
import kotlinx.coroutines.launch

class Authorization: AppCompatActivity() {
    private lateinit var binding: AuthorizationActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<ViewModels>() // Q
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)


        fun getuserinfo(access: String){
            lifecycleScope.launch {
                // Корутина приостановится (suspend) и будет ждать реального ответа от репозитория
                val user = viewModel.getUserInfo(access)

                // Данные гарантированно пришли, проверяем их
                if (user != null) {
                    saveText(user.data?.id, user.data?.FIO, access)
                    startActivity(Intent(this@Authorization, StartScreen::class.java))
                    finish()
                }
            }

        }


        CheckUser()
        binding = AuthorizationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.textField.hint="Введите email"
        binding.pass.textField.hint="Введите пароль"
        binding.pass.editUser.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

        binding.buttonLogin.setOnClickListener {
            if (binding.login.editUser.text.isEmpty()){
                binding.emaileror.visibility= View.VISIBLE
            }else{
                binding.emaileror.visibility= View.GONE
            }
            if (binding.pass.editUser.text.isEmpty()){
                binding.passeror.visibility= View.VISIBLE
            }else{
                binding.passeror.visibility= View.GONE
            }

            SafeRequest(viewModel).request(object : SafeRequest.Protection{

                override suspend fun makeRequest(): BaseResponceInterface {
                    val user = SourceProviderHolder.sourcesProvider.getAccountsSource().getToken(
                        AuthRequest(
                            binding.login.editUser.text.toString(),
                            binding.pass.editUser.text.toString()
                        )
                    )
                    return user
                }

                override fun ifSuccess(responce: BaseResponceInterface?) {
                    if (responce != null && responce is AuthReSponce){
                        getuserinfo(responce.access)
                    }

                    Toast.makeText(this@Authorization, "Вы авторизовались", Toast.LENGTH_SHORT).show()
                }

                override fun ifConnectionException() {
                    Toast.makeText(this@Authorization, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
                }

                override fun ifAuthException() {
                    Toast.makeText(this@Authorization, "Не верный логин или пароль", Toast.LENGTH_SHORT).show()
                }

            })

        }



        val wordtwo= SpannableString("Зарегистрируйтесь")
        wordtwo.setSpan(
            ForegroundColorSpan(Color.parseColor("#177164")),0,wordtwo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
            startActivity(Intent(this, StartScreen::class.java))
            finish()
        }
    }
    fun saveText(id:Int?,FIO:String?,token: String?) {
        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        sPref.edit {
            putString("id", id.toString())
            putString("FIO", FIO)
            putString("access_token", token)
        }
        finish()
        startActivity(Intent(this, Dashboard::class.java))
    }


}