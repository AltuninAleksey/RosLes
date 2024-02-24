package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.ResponceClass.temp_data_userresp
import com.example.rosles.databinding.ProfileBinding
import com.example.rosles.setSizeRelativeCurrentWindow
import kotlinx.coroutines.launch

class profile:BaseActivity("Профиль") {

//    val viewModel by viewModels<ViewModels>()
    val viewModel by viewModels<ViewModels>()
    private lateinit var binding: ProfileBinding
    private val db = DBCountWood(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intiToolbar()
        initscreen()
    }

    fun intiToolbar() {
        binding.toolbar.open.visibility = android.view.View.GONE
        binding.toolbar.save.visibility = android.view.View.GONE
        binding.toolbar.addporod.setOnClickListener {
            startActivity(Intent(this, AddPorod::class.java))
        }
    }

    override fun onRestart() {
        val bufPodel = binding.firstColumPodle
        val bufPoros = binding.firstColumPoros
        binding.tblPoros.removeAllViews()
        binding.podle.removeAllViews()
        binding.tblPoros.addView(bufPoros)
        binding.podle.addView(bufPodel)
        initscreen()
        super.onRestart()
    }

    @SuppressLint("Range")
    fun initscreen() {
        var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        var id = sPref.getString("id", "0")!!.toInt()
        lifecycleScope.launch {
            binding.fio.setText(checkuser(id))
        }
        var leftright: Boolean = false

        var activetableRow: TableRow? = null
        var favoriteLesList = db.getFavoriteLes(id)
        var favoritePodlesList = db.getFavoritePodles(id)


        for (i in 0..favoriteLesList.size - 1) {

            val tableRow = TableRow(this)
            val text0 = TextView(this)
            val text1 = TextView(this)
            text0.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text1.visibility = View.GONE
            text0.setTextColor(getResources().getColor(R.color.color_button_main))
            text0.setTextSize(20f)
            text0.setText(favoriteLesList[i].nameBreed)
            text1.setText(favoriteLesList[i].id)
            tableRow.addView(text0, 0)
            tableRow.addView(text1, 1)

            tableRow.setOnClickListener {
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
                leftright = true
            }
            binding.tblPoros.addView(tableRow, i + 1);
            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }

        for (i in 0..favoritePodlesList.size - 1) {

            val tableRow = TableRow(this)
            val text0 = TextView(this)
            val text1 = TextView(this)
            text0.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text1.visibility = View.GONE
            text0.setTextColor(getResources().getColor(R.color.color_button_main))
            text0.setTextSize(20f)

            text0.setText(favoritePodlesList[i].name)
            text1.setText(favoritePodlesList[i].id)

            tableRow.addView(text0, 0)
            tableRow.addView(text1, 1)

            tableRow.setOnClickListener {
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
                leftright = false
            }
            binding.podle.addView(tableRow, i + 1);
            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }

        binding.toolbar.user.setOnClickListener {
            startActivity(Intent(this, change_user::class.java))

        }

        binding.toolbar.delete.setOnClickListener {
            var bufer = activetableRow?.get(1)
            if (bufer != null) {
                val textView = bufer as TextView
                var dialog: Dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_delete)
                dialog.setSizeRelativeCurrentWindow(0.85, 0.6)

                var close = dialog.findViewById<Button>(R.id.close)
                var delete = dialog.findViewById<Button>(R.id.delete)
                dialog.show()

            close.setOnClickListener{
                dialog.dismiss()
                onRestart()
            }
            delete.setOnClickListener{

                    if (leftright) {
                        db.deletelesporod(textView.text.toString().toInt())
//                        startActivity(Intent(this, profile::class.java))
                        dialog.dismiss()
                        onRestart()
                    }
                    else{
                        db.deletepodlesporod(textView.text.toString().toInt())
//                        startActivity(Intent(this, profile::class.java))
                        dialog.dismiss()
                        onRestart()
                    }

                }

            }
        }
    }

    suspend fun checkuser(id:Int):String{

        var result:String
        var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);

        result= sPref.getString("FIO", "").toString()



        SafeRequest(viewModel).request(object : SafeRequest.Protection{

            override suspend fun makeRequest(): BaseResponceInterface {
                val user = SourceProviderHolder.sourcesProvider.getAccountsSource().getprofileid(id)
                return user
            }

            override fun ifSuccess(responce: BaseResponceInterface?) {
                if (responce != null && responce is temp_data_userresp){
                    result=responce.get.FIO

                    var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
                    val ed = sPref.edit()
                    ed.putString("FIO", result.toString())
                    ed.apply()
                    binding.fio.setText(result)

                }
            }



        })
        return result
    }





}

