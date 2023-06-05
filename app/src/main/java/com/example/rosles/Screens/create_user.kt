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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

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

            CoroutineScope(Dispatchers.IO).launch {
                val value=viewModel.registration(RegistrationReqest(
                    binding.email.editUser.text.toString(),
                    binding.password.editUser.text.toString(),
                    binding.name.editUser.text.toString(),
                    binding.filial.editUser.text.toString()))

                runOnUiThread{
                    when(value.code()){
                        (201)->{
                            Toast.makeText(this@create_user,"Пользователь создан",Toast.LENGTH_SHORT).show()
                            finish()
                        }else->{
                        val a=value?.errorBody()?.string().let { JSONObject(it).getString("error_text") }
                        Toast.makeText(this@create_user,a,Toast.LENGTH_SHORT).show()
                        }
                    }
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
