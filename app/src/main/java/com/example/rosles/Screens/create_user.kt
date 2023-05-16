package com.example.rosles.Screens

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.ResponceClass.BaseResp
import com.example.rosles.ResponceClass.BaseResponceInterface
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


    fun  initview() {
        binding.name.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.person))
        binding.filial.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home))
        binding.password.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.password))
        binding.passwordapply.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.password))

        binding.name.editUser.setHint("ФИО")
        binding.filial.editUser.setHint("Телефон")
        binding.email.editUser.setHint("Email")
        binding.password.editUser.setHint("Password")
        binding.passwordapply.editUser.setHint("Confirm Password")

        binding.CreateSave.setOnClickListener {
            with(binding) {
                if (isValidPassword()) {
                    SafeRequest(viewModel).request(object: SafeRequest.Protection {
                        override suspend fun makeRequest(): BaseResponceInterface? {
                            val body = RegistrationReqest(
                                email.editUser.text.toString(),
                                password.editUser.text.toString(),
                                name.editUser.text.toString(),
                                filial.editUser.text.toString())

                            val responseFromReg: BaseResp = SourceProviderHolder.sourcesProvider
                                .getAccountsSource().registration(body)

                            return responseFromReg
                        }

                        override fun ifSuccess(responce: BaseResponceInterface?) {
                           Toast.makeText(this@create_user, "Вы зарегестрировались",
                               Toast.LENGTH_SHORT).show()
                        }

                        override fun ifException() {
                            Toast.makeText(this@create_user, "Ошибка регистрации",
                                Toast.LENGTH_SHORT).show()
                        }

                        override fun ifConnectionException() {
                            Toast.makeText(this@create_user, "Нет подключения к интернету",
                                Toast.LENGTH_SHORT).show()
                        }

                    })
                } else {
                    Toast.makeText(this@create_user, "Пароли не совпадают",
                        Toast.LENGTH_SHORT).show()
                }
            }



            //магия вне хогвартса отправка анкеты на сервер вжух хуяк-хуяк пользователь есть
            //ахуеть папаша вот это паштет навалилы базы + и метода
        }
    }

    fun isValidPassword(): Boolean {
        val passwordBuffer: String = binding.password.editUser.text.toString()
        val passwordApplyBuffer: String = binding.passwordapply.editUser.text.toString()

        return passwordBuffer.isNotEmpty() && passwordApplyBuffer.isNotEmpty() &&
                passwordBuffer.equals(passwordApplyBuffer)
    }
}
