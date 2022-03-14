package edu.bu.CS683M1_DiceExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val player1 = Player()
    private val player2 = Player("John",2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val play1Name = findViewById<TextView>(R.id.editTextPersonName1)
        val play2Name = findViewById<TextView>(R.id.editTextPersonName2)
        val diceResult = findViewById<TextView>(R.id.diceexample)
        val score1 = findViewById<TextView>(R.id.score1)
        val score2 = findViewById<TextView>(R.id.score2)

        // reset the names and scores
        findViewById<TextView>(R.id.resetBtn).setOnClickListener{
            player1.name = play1Name?.text?.toString()?:player1.name
            player1.score = 0
            player2.name = play2Name?.text?.toString()?:player2.name
            player2.score = 0
            score1.setText("score:0")
            score2.setText("score:0")
            diceResult.setText("")

        }
        // play the game and update scores and game details
        findViewById<Button>(R.id.diceBtn).setOnClickListener{
            diceResult.text= playDice()
            score1.setText("score:${player1.score}")
            score2.setText("score:${player2.score}")
        }
    }


    // This function returns a message providing the game details.
    // Each game has 6 rounds. In each round, both player throw a dice.
    // The winner gets 1 point
    fun playDice():String  {

        var message=""

        // put your code here

        



        return message
    }

    private class Player(
        var name: String = "Amy",
        val level: Int  = 1) {

        val id:String
            get() = "$name-$level"

        var score: Int = 0;

        fun win(){
            score ++
        }
    }
}