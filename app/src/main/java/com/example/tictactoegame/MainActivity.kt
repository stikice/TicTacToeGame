package com.example.tictactoegame

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val winCombination = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(3, 6, 9),
        listOf(1, 5, 9),
        listOf(7, 5, 3),
    )

    var buttons = listOf<Button>()

    var timer = 1
    var isX = true

    val listX = mutableListOf<Int>()
    val listO = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startGame()
        buttonClickListener()
    }

    fun startGame() {
        if(buttons.isNotEmpty()){
            buttons.forEach{
                it.text=""
                val b1b = it.background as GradientDrawable
                b1b.setColor(ContextCompat.getColor(this, R.color.grey))
                it.isEnabled = true
            }
        }

        timer = 1
        isX = true

        printTurn(isX)

        listX.clear()
        listO.clear()
    }

    fun buttonClickListener() {
        buttons = listOf<Button>(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
        )
        buttons.forEachIndexed { index, element ->
            element.setOnClickListener {
                if (timer < 9) {
                    timer++
                    if (isX) {
                        putX(element, index)
                    } else {
                        putO(element, index)
                    }
                } else {
                    if (isX) {
                        putX(element, index)
                    } else {
                        putO(element, index)
                    }
                    val customView: View = layoutInflater.inflate(R.layout.dialog_custom, null)
                    val dialog: AlertDialog = AlertDialog.Builder(this).apply {
                        setView(customView)
                    }.create()
                    with(customView) {
                        findViewById<TextView>(R.id.tv_win_result).text = "Ничья!"
                        findViewById<Button>(R.id.btn_replay).setOnClickListener() {
                            startGame()
                            dialog.dismiss()
                        }
                    }
                    dialog.show()

                }
            }
        }
    }

    fun putX(element: Button, index: Int) {
        element.text = "X"
        val b1b = element.background as GradientDrawable
        b1b.setColor(ContextCompat.getColor(this, R.color.blue))
        isX = false
        listX.add(index + 1)
        printTurn(isX)
        checkForCombination(listX, 1)
        //element.setOnClickListener(null)
        element.isEnabled = false
    }

    fun putO(element: Button, index: Int) {
        element.text = "O"
        val b1b = element.background as GradientDrawable
        b1b.setColor(ContextCompat.getColor(this, R.color.pink))
        isX = true
        listO.add(index + 1)
        printTurn(isX)
        checkForCombination(listO, 0)
        //element.setOnClickListener(null)
        element.isEnabled = false
    }

    fun printTurn(isX: Boolean) {
        if (isX) {
            findViewById<TextView>(R.id.tv_turn).text = "Ходит: крестик"
        } else {
            findViewById<TextView>(R.id.tv_turn).text = "Ходит: нолик"
        }
    }

    fun checkForCombination(list: MutableList<Int>, x: Int) {
        winCombination.forEach() {
            if (list.containsAll(it)) {
                val customView: View = layoutInflater.inflate(R.layout.dialog_custom, null)
                val dialog: AlertDialog =
                    AlertDialog.Builder(this, R.style.FullWidth_Dialog).apply {
                        setView(customView)
                    }.create()
                with(customView) {
                    findViewById<Button>(R.id.btn_replay).setOnClickListener {
                        startGame()
                        dialog.dismiss()
                    }
                }

                if (x == 1) {
                    with(customView) {
                        findViewById<TextView>(R.id.tv_win_result).text = "Выиграл крестик!"
                    }
                    dialog.show()
                    println("x is win")
                } else {
                    with(customView) {
                        findViewById<TextView>(R.id.tv_win_result).text = "Выиграл нолик!"
                    }
                    dialog.show()
                    println("o is win")
                }

            }
        }
    }
}