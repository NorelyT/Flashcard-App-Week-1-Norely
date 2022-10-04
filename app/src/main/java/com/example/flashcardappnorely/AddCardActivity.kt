package com.example.flashcardappnorely

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        findViewById<View>(R.id.cancelButton).setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.saveButton).setOnClickListener {
            val data = Intent() // create a new Intent, this is where we will put our data

            data.putExtra(
                "string1",
                findViewById<EditText>(R.id.inputQuestion).text.toString()
            ) // puts one string into the Intent, with the key as 'string1'

            data.putExtra(
                "string2",
                findViewById<EditText>(R.id.inputAnswer).text.toString()
            ) // puts another string into the Intent, with the key as 'string2

            setResult(RESULT_OK, data) // set result code and bundle data for response

            finish() // closes this activity and pass data to the original activity that launched this activity
        }

    }
}