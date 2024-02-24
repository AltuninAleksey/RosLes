package com.example.rosles.Screens

import android.content.Intent
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
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.ResponceClass.AuthReSponce
import com.example.rosles.ResponceClass.BaseResp
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.ResponceClass.UserResp
import com.example.rosles.ResponceClass.temp_data_userresp
import com.example.rosles.databinding.CreateUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class change_user:AppCompatActivity() {


    private lateinit var binding: CreateUserBinding
    val viewModel by viewModels<ViewModels>()
    var id_Profile:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = CreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.name.editUser.requestFocus()
        binding.textView.text="Редактировать пользователя"
        binding.createuser.text=""
        binding.CreateSave.text="Редактировать"

        var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        id_Profile= sPref.getString("id", "0")!!.toInt()

        initview()
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

        binding.email.root.visibility=View.GONE
        binding.password.root.visibility=View.GONE
        binding.passwordapply.root.visibility=View.GONE

        binding.name.editUser.setHint("ФИО")
        binding.filial.editUser.setHint("Телефон")

        binding.CreateSave.setOnClickListener{
            try {
                changeuser()

            }catch (e:Exception){
                Toast.makeText(this@change_user, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }


            SafeRequest(viewModel).request(object : SafeRequest.Protection{

                override suspend fun makeRequest(): BaseResponceInterface {
                    val user = SourceProviderHolder.sourcesProvider.getAccountsSource().getprofileid(id_Profile!!)
                    return user
                }

                override fun ifSuccess(responce: BaseResponceInterface?) {
                    if (responce != null && responce is temp_data_userresp){
                        binding.name.editUser.setText(responce.get.FIO)
                        binding.filial.editUser.setText(responce.get.phoneNumber)

                    }
                }

                override fun ifConnectionException() {
                    Toast.makeText(this@change_user, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
                }

                override fun ifAuthException() {
                    Toast.makeText(this@change_user, "Не верный логин или пароль", Toast.LENGTH_SHORT).show()
                }

            })



        }

        fun changeuser(){
            lifecycleScope.launch {
                var body:UserResp= UserResp(
                    id_Profile!!,
                    binding.name.editUser.text.toString(),
                    binding.filial.editUser.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    null
                )

                viewModel.putprofile(id_Profile!!,body)
                Toast.makeText(this@change_user, "Успешно", Toast.LENGTH_SHORT).show()
                delay(1000)
                finish()
            }



        }




    }



