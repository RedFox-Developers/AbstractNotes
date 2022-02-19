package com.codingwithme.notesapp.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.redfox.abstractnotes.R
import dev.redfox.abstractnotes.database.Notes
import dev.redfox.abstractnotes.databinding.ItemRvNotesBinding

import java.util.*
import kotlin.collections.ArrayList

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRvNotesBinding.inflate(layoutInflater, parent, false)
        return NotesViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.binding.tvTitle.text = arrList[position].title
        holder.binding.tvDesc.text = arrList[position].noteText
        holder.binding.tvDateTime.text = arrList[position].dateTime

        if (arrList[position].color != null){
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }else{
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorLightBlack.toString()))
        }

//        IMAGE STUFF
//        if (arrList[position].imgPath != null){
//            holder.binding.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
//            holder.binding.imgNote.visibility = View.VISIBLE
//        }else{
//            holder.binding.imgNote.visibility = View.GONE
//        }

        if (arrList[position].webLink != ""){
            holder.binding.tvWebLink.text = arrList[position].webLink
            holder.binding.tvWebLink.visibility = View.VISIBLE
        }else{
            holder.binding.tvWebLink.visibility = View.GONE
        }

        holder.binding.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }

    }

    class NotesViewHolder(val binding: ItemRvNotesBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){

    }


    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

}