package com.example.moksh.tictactoe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    var boxArray = Array(3, { Array<Button?>(3) { null } })
    var player1Turn = true
    var roundCount = 0
    var player1Points = 0
    var player2Points = 0
    var tvPlayer1: TextView? = null
    var tvPlayer2: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvPlayer1 = findViewById(R.id.tvP1Score)
        tvPlayer2 = findViewById(R.id.tvP2Score)

        for (i in 0..2) {

            for (j in 0..2) {
                var tempBoxID = "box_" + i + j
                var resID = resources.getIdentifier(tempBoxID, "id", packageName)
                boxArray[i][j] = findViewById(resID)
                boxArray[i][j]?.setOnClickListener(this)
            }

        }

        var resetButton: Button? = findViewById(R.id.btnReset)
        resetButton?.setOnClickListener(
                {
                    resetGame()
                }
        )


    }

    override fun onClick(v: View?) {
        val btnSelected = v as Button
        if (!(btnSelected.text.toString().equals(""))) {
            return
        }

        if (player1Turn) {
            btnSelected.text = "X"
            btnSelected.setBackgroundColor(Color.RED)
        } else {
            btnSelected.text = "O"
            btnSelected.setBackgroundColor(Color.YELLOW)
        }

        roundCount++

        if (checkForWinner()) {
            if (player1Turn)
                player1Wins()
            else
                player2Wins()
        } else if (roundCount == 9) {
            draw()
        } else {
            player1Turn = !player1Turn
        }

    }

    fun checkForWinner(): Boolean {
        var field = Array(3, { Array<String?>(3) { null } })
        for (i in 0..2) {

            for (j in 0..2) {
                field[i][j] = boxArray[i][j]?.text.toString()
            }
        }

        for (i in 0..2) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")

            ) {
                return true
            }
        }

        for (i in 0..2) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")

            ) {
                return true
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")

        ) {
            return true
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")

        ) {
            return true
        }

        return false

    }

    fun player1Wins() {
        player1Points++
        Toast.makeText(this, "Player 1 is the Winner!!!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    fun player2Wins() {
        player2Points++
        Toast.makeText(this, "Player 2 is the Winner!!!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    fun draw() {
        Toast.makeText(this, "DRAW!!!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    fun updatePointsText() {
        tvPlayer1?.setText("Player 1: " + player1Points)
        tvPlayer2?.setText("Player 2: " + player2Points)
    }

    fun resetBoard() {
        for (i in 0..2) {

            for (j in 0..2) {
                boxArray[i][j]?.setText("")
                boxArray[i][j]?.setBackgroundColor(Color.LTGRAY)


            }
        }
        roundCount = 0
        player1Turn = true

    }

    fun resetGame() {
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("roundCount", roundCount)
        outState?.putInt("player1Points", player1Points)
        outState?.putInt("player2Points", player2Points)
        outState?.putBoolean("player1Turn", player1Turn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        roundCount = savedInstanceState!!.getInt("roundCount")
        player1Points = savedInstanceState!!.getInt("player1Points")
        player2Points = savedInstanceState!!.getInt("player2Points")
        player1Turn = savedInstanceState!!.getBoolean("player1Turn")
    }
}






