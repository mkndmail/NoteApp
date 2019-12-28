package com.i.notemakingapp.activity

/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.i.notemakingapp.R
import com.i.notemakingapp.adapter.NoteListAdapter
import com.i.notemakingapp.entity.Note
import com.i.notemakingapp.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*


const val ADD_NOTE_REQUEST = 1
const val EDIT_REQUEST_CODE = 2
const val NOTE_ADD_WORD = "note_add"
const val TITLE_ADD_WORD = "title"
const val EDIT_TITLE = "edit_title"
const val EDIT_NOTE = "edit_note"

class MainActivity : AppCompatActivity(), NoteListAdapter.ItemClicked {

    private lateinit var notesAdapter: NoteListAdapter
    private lateinit var noteViewModel: NoteViewModel
    private var editNotedId: Int = 0
    private var notes = arrayListOf<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        notesAdapter = NoteListAdapter(this, this)
        rvNotes.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        addNoteButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        noteViewModel.allNotes.observe(this, Observer { notes ->
            if (notes.isEmpty()) {
                textView.visibility = View.VISIBLE
                rvNotes.visibility = View.GONE
            } else {
                textView.visibility = View.GONE
                rvNotes.visibility = View.VISIBLE
                notesAdapter.setNotes(notes)
            }

        })
    }

    override fun itemClicked(note: Note, action: Int) {
        when (action) {
            0 -> deleteNote(note)
            1 -> editNote(note)
        }
    }

    private fun deleteNote(note: Note) {

        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Confirmation?")
            .setMessage("Are you sure to delete this note?")
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                noteViewModel.deleteNote(note.id)
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }

    private fun editNote(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        editNotedId = note.id
        with(intent) {
            putExtra(EDIT_TITLE, note.title)
            putExtra(EDIT_NOTE, note.content)
        }
        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val note = data.getStringExtra(NOTE_ADD_WORD)
                val createdDate = System.currentTimeMillis()
                notesAdapter.setNotes(notes)
                val title = data.getStringExtra(TITLE_ADD_WORD)
                val objNote = Note(0, title, note, createdDate, false)
                noteViewModel.insert(objNote)
            }

        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val note = data.getStringExtra(NOTE_ADD_WORD)
                val createdDate = System.currentTimeMillis()
                notesAdapter.setNotes(notes)
                val title = data.getStringExtra(TITLE_ADD_WORD)
                val objNote = Note(editNotedId, title, note, createdDate, true)
                noteViewModel.editNote(editNotedId, objNote)
                notesAdapter.notifyDataSetChanged()
            }
        }
    }
}
