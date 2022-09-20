package com.example.flashcardappnorely

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val question = findViewById<TextView>(R.id.flashcard_question)
        val answer = findViewById<TextView>(R.id.flashcard_answer)


        // Flips card around from question to answer and from answer to question
        question.setOnClickListener {
            question.visibility = View.INVISIBLE
            answer.visibility = View.VISIBLE
        }

        answer.setOnClickListener {
            answer.visibility = View.INVISIBLE
            question.visibility = View.VISIBLE
        }


        val answerOption1 = findViewById<TextView>(R.id.option1)
        val answerOption2 = findViewById<TextView>(R.id.option2)
        val answerOption3 = findViewById<TextView>(R.id.option3)


        // Function that resets the previously clicked choices
        fun resetAnswers(wrongAnswer: TextView, rightAnswer: TextView){
            wrongAnswer.setBackgroundColor(getResources().getColor(R.color.pink))
            rightAnswer.setBackgroundColor(getResources().getColor(R.color.pink))
        }

        var wrongAnswerPointer = answerOption3      // Default value
        val answer3Color = answerOption3.background as ColorDrawable


        // Conditionals that indicate whether the chosen option is right (green) or
        // wrong (red), and also resets them after the user chooses an answer
        answerOption1.setOnClickListener {
            if (answer3Color.color == getResources().getColor(R.color.green)) {
                resetAnswers(wrongAnswerPointer, answerOption3)
            }
            else {
                answerOption1.setBackgroundColor(getResources().getColor(R.color.red))
                answerOption3.setBackgroundColor(getResources().getColor(R.color.green))
                wrongAnswerPointer = answerOption1
            }
        }

        answerOption2.setOnClickListener {
            if (answer3Color.color == getResources().getColor(R.color.green)) {
                resetAnswers(wrongAnswerPointer, answerOption3)
            }
            else {
                answerOption2.setBackgroundColor(getResources().getColor(R.color.red))
                answerOption3.setBackgroundColor(getResources().getColor(R.color.green))
                wrongAnswerPointer = answerOption2
            }
        }

        answerOption3.setOnClickListener {
            if (answer3Color.color == getResources().getColor(R.color.green)) {
                resetAnswers(wrongAnswerPointer, answerOption3)
            }
            else {
                answerOption3.setBackgroundColor(getResources().getColor(R.color.green))
                wrongAnswerPointer = answerOption3
            }
        }


        val hideOptions = findViewById<ImageView>(R.id.closedEye)


        // Conditionals that toggle the option visibility
        var optionsIsVisible = true
        hideOptions.setOnClickListener {
            if (optionsIsVisible){
                hideOptions.setImageResource(R.drawable.eyecon_1)
                optionsIsVisible = false
            }
            else {
                hideOptions.setImageResource(R.drawable.eyecon_2)
                optionsIsVisible = true
            }
            answerOption1.isVisible = !(answerOption1.isVisible)
            answerOption2.isVisible = !(answerOption2.isVisible)
            answerOption3.isVisible = !(answerOption3.isVisible)
        }
    }
}