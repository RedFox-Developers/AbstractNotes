package dev.redfox.abstractnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dev.redfox.abstractnotes.database.Notes
import dev.redfox.abstractnotes.database.NotesDatabase
import dev.redfox.abstractnotes.databinding.FragmentCreateNoteBinding
import dev.redfox.abstractnotes.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {
    var currentDate: String? = null
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding
        get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        binding.tvDateTime.text = currentDate

        binding.imgDone.setOnClickListener {
            saveNote()
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }
    }
    private fun saveNote(){
        if (binding.etNoteTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Title is Required",Toast.LENGTH_SHORT).show()
        }
        else if (binding.etNoteSubTitle.text.isNullOrEmpty()){

            Toast.makeText(context,"Note Sub Title is Required",Toast.LENGTH_SHORT).show()
        }

        else if (binding.etNoteDesc.text.isNullOrEmpty()){

            Toast.makeText(context,"Note Description is Required",Toast.LENGTH_SHORT).show()
        }

        else {
            launch {
                var notes = Notes()
                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                context?.let {
                    NotesDatabase.getDatabase(it).notesDao().insertNotes(notes)
                    binding.etNoteDesc.setText("")
                    binding.etNoteTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                }
            }
        }
    }
}