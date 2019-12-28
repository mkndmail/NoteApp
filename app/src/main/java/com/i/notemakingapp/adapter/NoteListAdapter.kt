package com.i.notemakingapp.adapter

/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */
import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.i.notemakingapp.R
import com.i.notemakingapp.entity.Note

class NoteListAdapter(private var context: Context, itemClicked: ItemClicked) :
    RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    private var listOfNotes = emptyList<Note>()
    private val itemAction = itemClicked

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtContent: TextView = itemView.findViewById(R.id.txtContent)
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtCreatedDate: TextView = itemView.findViewById(R.id.txtCreatedDate)
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_notes_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = listOfNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = listOfNotes[position]
        holder.txtContent.text = note.content
        holder.txtTitle.text = note.title
        val dateInRequiredForm = getDateInRequiredForm(note.createDate, ::getDateFromTimeStamp)
        if (note.isEdited)
            holder.txtCreatedDate.text =
                context.getString(R.string.edited_on_text, dateInRequiredForm)
        else
            holder.txtCreatedDate.text =
                context.getString(R.string.created_on_text, dateInRequiredForm)

        holder.imgDelete.setOnClickListener {
            itemAction.itemClicked(note, 0)
        }
        holder.imgEdit.setOnClickListener {
            itemAction.itemClicked(note, 1)
        }
    }

    fun setNotes(notes: List<Note>) {
        listOfNotes = notes
        notifyDataSetChanged()
    }


    interface ItemClicked {
        fun itemClicked(note: Note, action: Int)
    }
}

fun getDateFromTimeStamp(currentTimeInMillis: Long): String {
    return DateFormat.format("dd MMM hh:mm a", currentTimeInMillis).toString()
}


fun getDateInRequiredForm(timeInMillis: Long, op: (Long) -> String): String {
    return op(timeInMillis)
}
