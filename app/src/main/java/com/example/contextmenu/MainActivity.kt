package com.example.contextmenu

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val ITEM_ON = 111
        const val ITEM_OFF = 112
    }

    private lateinit var textET: EditText
    private lateinit var randomBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textET = findViewById(R.id.myET)
        randomBTN = findViewById(R.id.randomBTN)
        registerForContextMenu(textET)

        randomBTN.setOnClickListener(this)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(Menu.NONE, ITEM_ON, Menu.NONE, "Цветовое качество")
        menu?.add(Menu.NONE, ITEM_OFF, Menu.NONE, "Выход из приложения")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var message = ""
        when (item.itemId) {
            ITEM_ON -> {
                val result = textET.text.toString().toIntOrNull()
                if (result != null && result in 1..5) {
                    changeTextColor(result, 1)
                    message = "Пункт меню 'Цветовое качество'"
                } else {
                    message = "Неверный ввод"
                    textET.text.clear()
                }
            }

            ITEM_OFF -> {
                message = "Пункт меню 'Выход из приложения'"
                finish()
            }

            else -> return super.onContextItemSelected(item)
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        return true
    }

    override fun onClick(v: View) {
        val value = 1 + (Math.random() * 49).toInt()
        textET.setText(value.toString(), TextView.BufferType.EDITABLE)
        changeTextColor(value, 10)
    }

    fun changeTextColor(result: Int, value: Int) {
        var num = 1
        val colors: List<Int> =
            listOf(Color.rgb(255, 165, 0), Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED)
        var max = value
        var valColor = 0
        if (value == 10) valColor = 4
        for (i in 0..4) {
            Log.v(result.toString(), valColor.toString())
            if (result >= num && result <= max) {
                textET.setTextColor(colors[valColor])
                return
            }
            valColor++
            if (valColor == colors.size) valColor -= colors.size
            num += value
            max += value
        }
    }
}