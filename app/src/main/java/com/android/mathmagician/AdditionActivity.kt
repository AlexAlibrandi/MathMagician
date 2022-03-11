package com.android.mathmagician

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class AdditionActivity : AppCompatActivity() {

    lateinit var score : TextView
    lateinit var lives : TextView
    lateinit var time : TextView

    lateinit var question : TextView
    lateinit var answer : TextView

    lateinit var okayButton : Button
    lateinit var nextButton : Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    lateinit var timer : CountDownTimer
    private val startTimerInMillis : Long = 20000
    var timeLeftInMillis : Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addition)

        supportActionBar!!.title = ("Addition")

        score = findViewById(R.id.scoregame)
        lives = findViewById(R.id.life)
        time = findViewById(R.id.time)
        question = findViewById(R.id.question)
        answer = findViewById(R.id.answer)
        okayButton = findViewById(R.id.okay)
        nextButton = findViewById(R.id.next)

        gameContinue()

        okayButton.setOnClickListener {

            val input = answer.text.toString()

            if (input == ""){
                Toast.makeText(applicationContext,"Please write an answer or click the next button",
                    Toast.LENGTH_SHORT).show()
            }
            else {

                pauseTimer()

                val userAnswer = input.toInt()

                if(userAnswer == correctAnswer){
                    userScore += 10
                    question.text = "Correct!!!!!"
                    score.text = userScore.toString()
                }
                else {
                    userLife--
                    question.text = "Incorrect =("
                    lives.text = userLife.toString()
                }
            }
        }
        nextButton.setOnClickListener {
            pauseTimer()
            resetTimer()
            gameContinue()
            answer.text = ""

            if(userLife == 0){
                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AdditionActivity,ResultActivity::class.java)
                intent.putExtra("score", userScore)
                startActivity(intent)
                finish()
            }
            else {
                gameContinue()
            }
        }
    }
    fun gameContinue(){
        val randNumb1 = Random.nextInt(0,1000)
        val randNumb2 = Random.nextInt(0,1000)

        question.text = "$randNumb1 + $randNumb2"

        correctAnswer = randNumb1 + randNumb2

        startTimer()
    }
    fun startTimer(){
        timer = object: CountDownTimer(timeLeftInMillis,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                lives.text = userLife.toString()
                question.text = "Sorry time is up, be smarter"
            }

        }.start()
    }
    private fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun updateText() {
        val remainingTime = (timeLeftInMillis / 1000).toInt()
        time.text = String.format(Locale.getDefault(), "%02d",remainingTime)
    }
}