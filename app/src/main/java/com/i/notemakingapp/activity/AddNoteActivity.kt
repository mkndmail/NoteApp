package com.i.notemakingapp.activity

/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.i.notemakingapp.R
import kotlinx.android.synthetic.main.activity_add_note.*


class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.title = "Add Note"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        edtNoteTitle.requestFocus()
        getIntentData()
        buttonSaveNote.setOnClickListener {
            if (validated()) {
                val intent = Intent()
                with(intent) {
                    putExtra(NOTE_ADD_WORD, edtNote.text.toString().trim())
                    putExtra(TITLE_ADD_WORD, edtNoteTitle.text.toString().trim())
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun getIntentData() {
        intent?.extras?.let {
            edtNoteTitle.setText(intent.getStringExtra(EDIT_TITLE).toString())
            edtNote.setText(intent.getStringExtra(EDIT_NOTE).toString())

        }
    }

    private fun validated(): Boolean {
        return if (enteredTextIsNotEmpty(edtNoteTitle)) {
            if (enteredTextIsNotEmpty(edtNote)) {
                true
            } else {
                setError(edtNote, "Please Enter a note")
                false
            }
        } else {
            setError(edtNoteTitle, "Please enter note title")
            false
        }
    }


    private fun enteredTextIsNotEmpty(editText: EditText): Boolean {
        return editText.text.toString().isNotEmpty() && editText.text.toString().isNotBlank()
    }

    private fun setError(editText: EditText, errorMessage: String) {
        editText.error = errorMessage
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }
}
