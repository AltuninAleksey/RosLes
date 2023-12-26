package com.example.rosles.Screens


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Models.Poroda
import com.example.rosles.Network.Singletons
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.ActivityMainBinding
import com.example.rosles.setSizeRelativeCurrentWindow


class MainActivity : BaseActivity("Перечетные ведомости") {

    //в ожидании звездного часа на синхрон
    val viewModel by viewModels<ViewModels>()
    private val db = DBCountWood(this, null)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Singletons.init(MainActivity())
        //инциализация навигации

        RecyclerviewInit()
    }
    override fun onRestart() {
        binding.tblLayout.removeAllViews()
        RecyclerviewInit()
        super.onRestart()
    }
    @SuppressLint("Range")
    fun RecyclerviewInit() {
        val porodaList :List<Poroda> = db.readbyporoda()
        var activetableRow: TableRow? = null

        // отсюда вычитаем единицу тк как в противном случае выходим в оут оф баунс(данные не записываются в полном обьеме)
        for (i in 0..porodaList.size-1) {
            val tableRow = TableRow(this)
            /* Порядок важен, знацения будут добавляться в колонки таблицы
            * в порядке указанном в valuesOfPoroda */
            val valuesOfPorodaList: List<String> = mutableListOf(
                porodaList[i].id,
                porodaList[i].nameForestly,
                porodaList[i].nameDistrictForestly,
                porodaList[i].quarterName,
                porodaList[i].soilLot,
                porodaList[i].date
            )
            // сборка строки для тоблицы
            for((indexOfvalue, valueOfPoroda) in valuesOfPorodaList.withIndex()) {
                val text = TextView(this)
                text.textAlignment = View.TEXT_ALIGNMENT_CENTER
                text.setTextColor(-0x1000000)
                text.text = valueOfPoroda
//                if (indexOfvalue == 1)
//                    text.visibility = View.GONE
                tableRow.addView(text, indexOfvalue)
            }
            val img = ImageView(this)
            if (porodaList[i].markUpdate >= 1){
                img.setImageResource(R.drawable.reloadred)
                tableRow.addView(img)
            }

            tableRow.setOnClickListener {
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
            }

            //в table view на 0 элементе находиться тайтл таблицы поэтому к счетчику добавляем единицу
            binding.tblLayout.addView(tableRow, i);

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }

        binding.toolbar.open.setOnClickListener {
            if (activetableRow != null) {
                var bufer = activetableRow?.get(0)
                val textView = bufer as TextView
                val intent = Intent(this, lisq_square::class.java)
                intent.putExtra("id_Vedomost", textView.text)
                startActivity(intent)
            }
        }
        binding.toolbar.delete.setOnClickListener {
            val bufer = activetableRow?.get(0)
            if (bufer != null) {
                val textView = bufer as TextView
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
                    db.delete_listregion(textView.text.toString().toInt())
                    dialog.dismiss()
                    onRestart()
                }
            }
        }

        binding.toolbar.save.setOnClickListener() {
            if (activetableRow != null) {
                val intent = Intent(this, ChangeListregion::class.java)

                var bufer = activetableRow?.get(0)

                val textView = bufer as TextView
                intent.putExtra("id_Vedomost", textView.text)
                startActivity(intent)
            }
        }

        binding.toolbar.addbutton.setOnClickListener {
            startActivity(Intent(this, ChoiceSubject::class.java))
        }
    }


}






