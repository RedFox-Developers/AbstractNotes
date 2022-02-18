package dev.redfox.abstractnotes.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.redfox.abstractnotes.R
import dev.redfox.abstractnotes.database.Notes
import dev.redfox.abstractnotes.databinding.ItemRvNotesBinding

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    var arrList= ArrayList<Notes>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
       val binding = ItemRvNotesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, parent.context)
    }

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.text = arrList[position].title
        holder.binding.tvDesc.text = arrList[position].noteText
        holder.binding.tvDateTime.text = arrList[position].dateTime
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    class ViewHolder(val binding: ItemRvNotesBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

    }
}