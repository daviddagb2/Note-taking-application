package com.gonzalez.blanchard.notetakingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gonzalez.blanchard.notetakingapp.R
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel

class NotesAdapter(
    private val surveys: List<NoteModel>,
    private val itemClickListener: (NoteModel) -> Unit,
) : RecyclerView.Adapter<NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val survey = surveys[position]
        holder.bind(survey)
    }

    override fun getItemCount(): Int {
        return surveys.size
    }
}
