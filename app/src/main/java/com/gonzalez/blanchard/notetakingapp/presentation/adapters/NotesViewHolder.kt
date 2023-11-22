package com.gonzalez.blanchard.notetakingapp.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gonzalez.blanchard.notetakingapp.databinding.ItemNoteBinding
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel

class NotesViewHolder(view: View, private val onClickListener: (NoteModel) -> Unit) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNoteBinding.bind(view)

    fun bind(noteItem: NoteModel) {
        binding.tvTitle.text = noteItem.title
        binding.tvContent.text = noteItem.content
        binding.rootLayout.setOnClickListener {
            onClickListener(noteItem)
        }
    }
}
