package com.example.flashcardappnorely

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)

    // Added a Flashcard Database Instance
    lateinit var flashcardDatabase: FlashcardDatabase

    // List of all added flashcards
    var allFlashcards = mutableListOf<Flashcard>()

    // Index variable to access individual flashcards in the app
    var currentCardDisplayedIndex = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialized the Flashcard Database Instance
        flashcardDatabase = FlashcardDatabase(this)

        // Code to access all the flashcards in the database
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()


        val question = findViewById<TextView>(R.id.flashcard_question)
        val answer = findViewById<TextView>(R.id.flashcard_answer)


        // Code to display the flashcards in the database as long as it's not empty
        if (allFlashcards.size > 0) {
            question.text = allFlashcards[0].question
            answer.text = allFlashcards[0].answer
        }


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

//        findViewById<View>(R.id.addButton).setOnClickListener {
//            val intent = Intent(this, AddCardActivity::class.java)
//            startActivity(intent)
//        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->

            // This code is executed in StartingActivity after we come back from EndingActivity

            // This extracts any data that was passed back from EndingActivity
            val data: Intent? = result.data
            // ToDo: Execute more code here

            if (data != null) { // Check that we have data returned
                val string1 = data.getStringExtra("string1") // 'string1' needs to match the key we used when we put the string in the Intent
                val string2 = data.getStringExtra("string2")

                // Log the value of the strings for easier debugging
                Log.i("MainActivity", "string1: $string1")
                Log.i("MainActivity", "string2: $string2")

                question.setText(string1)
                answer.setText(string2)


                // Save newly created flashcard to database
                if (string1 != null && string2 != null) {
                    flashcardDatabase.insertCard(Flashcard(string1.toString(), string2.toString()))
                    // Update set of flashcards to include new card
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                    currentCardDisplayedIndex = allFlashcards.size - 1
                } else {
                    Log.e("TAG", "Missing question or answer to input into database. Question is $question and answer is $answer")
                }


            } else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }

        findViewById<View>(R.id.addButton).setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            // Launch EndingActivity with the resultLauncher so we can execute more code
            // once we come back here from EndingActivity
            resultLauncher.launch(intent)
        }

        findViewById<View>(R.id.nextButton).setOnClickListener {
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }

            // advance our pointer index so we can show the next card
            currentCardDisplayedIndex++

            // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
            if (currentCardDisplayedIndex >= allFlashcards.size){
                currentCardDisplayedIndex = 0
            }

            // set the question and answer TextViews with data from the database
            question.setText(allFlashcards[currentCardDisplayedIndex].question)
            answer.setText(allFlashcards[currentCardDisplayedIndex].answer)
        }

    }
}