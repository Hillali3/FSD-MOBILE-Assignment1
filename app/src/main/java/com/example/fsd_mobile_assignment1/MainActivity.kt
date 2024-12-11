package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var buttons: Array<Button>
    private lateinit var resetButton: Button
    private var currentPlayer = "X"
    private var gameBoard = Array(3) { arrayOfNulls<String>(3) }
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.status)
        resetButton = findViewById(R.id.resetButton)
        buttons = Array(9) { i ->
            findViewById<Button>(resources.getIdentifier("button${i + 1}", "id", packageName)).apply {
                setOnClickListener {
                    onCellClicked(this, i)
                }
            }
        }

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(button: Button, index: Int) {
        if (gameOver || button.text.isNotEmpty()) return

        val row = index / 3
        val col = index % 3

        gameBoard[row][col] = currentPlayer
        button.text = currentPlayer
        checkGameStatus()

        currentPlayer = if (currentPlayer == "X") "O" else "X"
        statusText.text = "Player $currentPlayer's turn"
    }

    private fun checkGameStatus() {
        if (checkWinner("X")) {
            gameOver = true
            statusText.text = "Player X wins!"
            Toast.makeText(this, "Player X wins!", Toast.LENGTH_SHORT).show()
        } else if (checkWinner("O")) {
            gameOver = true
            statusText.text = "Player O wins!"
            Toast.makeText(this, "Player O wins!", Toast.LENGTH_SHORT).show()
        } else if (isBoardFull()) {
            gameOver = true
            statusText.text = "It's a tie!"
            Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkWinner(player: String): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if ((gameBoard[i][0] == player && gameBoard[i][1] == player && gameBoard[i][2] == player) ||
                (gameBoard[0][i] == player && gameBoard[1][i] == player && gameBoard[2][i] == player)) {
                return true
            }
        }
        if ((gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) ||
            (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player)) {
            return true
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoard[i][j] == null) {
                    return false
                }
            }
        }
        return true
    }

    private fun resetGame() {
        gameOver = false
        currentPlayer = "X"
        statusText.text = "Player X's turn"
        gameBoard = Array(3) { arrayOfNulls<String>(3) }

        for (button in buttons) {
            button.text = ""
        }
    }
}