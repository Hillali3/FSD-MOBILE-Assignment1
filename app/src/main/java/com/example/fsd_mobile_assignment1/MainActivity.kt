package com.example.fsd_mobile_assignment1

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.fsd_mobile_assignment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    package com.example.tictactoe

    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity

    class MainActivity : AppCompatActivity() {

        private lateinit var statusText: TextView
        private lateinit var buttons: Array<Button>
        private var currentPlayer = "X"  // Player 1 starts as X
        private var gameState = Array(3) { Array(3) { "" } }  // 3x3 grid of empty strings

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            statusText = findViewById(R.id.statusText)
            buttons = arrayOf(
                findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3),
                findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
                findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
            )

            // Initialize button click listeners
            buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    onCellClick(index, button)
                }
            }
        }

        private fun onCellClick(index: Int, button: Button) {
            val row = index / 3
            val col = index % 3

            // If the cell is already taken, do nothing
            if (gameState[row][col].isNotEmpty()) return

            // Update the game state and button text
            gameState[row][col] = currentPlayer
            button.text = currentPlayer

            // Check for a winner
            if (checkWinner()) {
                statusText.text = "$currentPlayer Wins!"
                disableAllButtons()
            } else if (isBoardFull()) {
                statusText.text = "It's a Draw!"
            } else {
                // Switch players
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Player $currentPlayer's Turn"
            }
        }

        private fun checkWinner(): Boolean {
            // Check rows and columns
            for (i in 0..2) {
                if (gameState[i][0] == currentPlayer && gameState[i][1] == currentPlayer && gameState[i][2] == currentPlayer) {
                    return true
                }
                if (gameState[0][i] == currentPlayer && gameState[1][i] == currentPlayer && gameState[2][i] == currentPlayer) {
                    return true
                }
            }

            // Check diagonals
            if (gameState[0][0] == currentPlayer && gameState[1][1] == currentPlayer && gameState[2][2] == currentPlayer) {
                return true
            }
            if (gameState[0][2] == currentPlayer && gameState[1][1] == currentPlayer && gameState[2][0] == currentPlayer) {
                return true
            }

            return false
        }

        private fun isBoardFull(): Boolean {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (gameState[i][j].isEmpty()) {
                        return false
                    }
                }
            }
            return true
        }

        private fun disableAllButtons() {
            buttons.forEach { button -> button.isEnabled = false }
        }
    }
}