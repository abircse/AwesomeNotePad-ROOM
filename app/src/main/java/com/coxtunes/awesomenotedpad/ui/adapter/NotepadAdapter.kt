package com.coxtunes.awesomenotedpad.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.awesomenotedpad.R
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import javax.inject.Singleton

@Singleton
class NotepadAdapter : RecyclerView.Adapter<NotepadAdapter.Holder>() {

    internal var actionClickItem: (notepad: NotePad) -> Unit = { _ -> }
    internal var deleteActionClickItem: (notepad: NotePad) -> Unit = { _ -> }


    private val diffCallBack = object : DiffUtil.ItemCallback<NotePad>() {
        override fun areItemsTheSame(oldItem: NotePad, newItem: NotePad): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NotePad, newItem: NotePad): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun addNotes(notesList: List<NotePad>) {
        differ.submitList(notesList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.custom_notes_items,
            parent,
            false
        )
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, @SuppressLint("RecyclerView") position: Int) {
        val notepad = differ.currentList[position]
        notepad.let {
            it.title.let {
                holder.title.text = it
            }
            it.created_at.let {
                holder.createdAt.text = it
            }
            it.description.let {
                holder.description.text = it
            }
        }

        holder.itemView.setOnClickListener {
            actionClickItem.invoke(notepad)
        }

        holder.deleteNoteIcon.setOnClickListener {
            deleteActionClickItem.invoke(notepad)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_note)
        val createdAt: TextView = itemView.findViewById(R.id.createdat_note)
        val description: TextView = itemView.findViewById(R.id.description_note)
        val deleteNoteIcon: ImageView = itemView.findViewById(R.id.icon_delete)

    }

}
