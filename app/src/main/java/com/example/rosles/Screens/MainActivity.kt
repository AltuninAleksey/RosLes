package com.example.rosles.Screens


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import com.example.rosles.Adapters.ChoiceVudelAdapter
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.Singletons
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.ActivityMainBinding
import com.example.rosles.setSizeRelativeCurrentWindow


class MainActivity : BaseActivity("Перечетные ведомости") {


    val viewModel by viewModels<ViewModels>()
    private val db = DBCountWood(this, null)
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ChoiceVudelAdapter

    //to navigation next activity
    var id_vedomost: String? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Singletons.init(MainActivity())
        //инциализация навигации
        RecyclerviewInit()
        binding.toolbar.addbutton.setOnClickListener {
            var id_subject = getSharedPreferences("PreferencesName", MODE_PRIVATE)
                .getInt("id_subject",0)
            val intent1 = Intent(this, ChoiceSubject::class.java)
            intent1.putExtra("id", id_subject.toString())
            intent1.putExtra("id_Vedomost", intent.getStringExtra("id_Vedomost"))

            startActivity(intent1)
            RecyclerviewInit()
        }

    }
    override fun onRestart() {
        RecyclerviewInit()
        adapter.notifyDataSetChanged()
        super.onRestart()
    }
    @SuppressLint("Range")
    fun RecyclerviewInit() {

        adapter = ChoiceVudelAdapter().apply {
            setData(db.readbyporoda())
            listener={
                id_vedomost=it.id
            }
        }


        binding.testRecycler!!.adapter=adapter


        binding.toolbar.open.setOnClickListener {
            if (id_vedomost != null) {
                startActivity(
                    Intent(this, lisq_square::class.java)
                        .putExtra("id_Vedomost", id_vedomost))


            }
        }
        binding.toolbar.delete.setOnClickListener {
            if (id_vedomost != null) {
                val dialog: Dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_delete)
                dialog.setSizeRelativeCurrentWindow(0.85, 0.6)

                val close = dialog.findViewById<Button>(R.id.close)
                val delete = dialog.findViewById<Button>(R.id.delete)
                dialog.show()

                close.setOnClickListener {
                    dialog.dismiss()
                    onRestart()
                }
                delete.setOnClickListener {
                    db.delete_listregion(id_vedomost!!)
                    dialog.dismiss()
                    onRestart()
                }
            }
        }

        binding.toolbar.save.setOnClickListener() {
            if (id_vedomost != null) {

                startActivity(
                    Intent(this,
                        ChangeListregion::class.java)
                        .putExtra("id_Vedomost", id_vedomost))
            }
        }
    }


}






