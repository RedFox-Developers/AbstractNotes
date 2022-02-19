package dev.redfox.abstractnotes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import dev.redfox.abstractnotes.database.Notes
import dev.redfox.abstractnotes.database.NotesDatabase
import dev.redfox.abstractnotes.databinding.FragmentCreateNoteBinding
import dev.redfox.abstractnotes.databinding.FragmentHomeBinding
import dev.redfox.abstractnotes.util.NoteBottomSheetFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {
    var selectedColor = "#39A3F6"
    var currentDate: String? = null
    private var noteId = -1
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        noteId = requireArguments().getInt("noteId",-1)
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

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        currentDate = sdf.format(Date())

        binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

        binding.tvDateTime.text = currentDate

        binding.imgDone.setOnClickListener {
            if (noteId != -1) {
                updateNote()
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
            } else {
                saveNote()
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)

            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }

        binding.imgMore.setOnClickListener {
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId)
            noteBottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                "Note Bottom Sheet Fragment"
            )
        }
        binding.btnOk.setOnClickListener {
            if (binding.etWebLink.text.toString().trim().isNotEmpty()) {
                checkWebUrl()
            } else {
                Toast.makeText(requireContext(), "Url is required", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancel.setOnClickListener {
            binding.layoutWebUrl.visibility = View.GONE
        }

        binding.tvWebLink.setOnClickListener{
            var intent =Intent(Intent.ACTION_VIEW, Uri.parse(binding.etWebLink.text.toString()))
            startActivity(intent)
        }
    }

    private fun saveNote() {
        if (binding.etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        } else if (binding.etNoteSubTitle.text.isNullOrEmpty()) {

            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        } else if (binding.etNoteDesc.text.isNullOrEmpty()) {

            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        } else {
            launch {
                var notes = Notes()
                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                notes.webLink = binding.etWebLink.text.toString()
                context?.let {
                    NotesDatabase.getDatabase(it).notesDao().insertNotes(notes)
                    binding.etNoteDesc.setText("")
                    binding.etNoteTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.tvWebLink.visibility=View.GONE
                }
            }
        }
    }

    private fun deleteNote() {

        launch {
            context?.let {
                NotesDatabase.getDatabase(it).notesDao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    private fun updateNote() {
        launch {

            context?.let {
                var notes = NotesDatabase.getDatabase(it).notesDao().getSpecificNote(noteId)

                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
//                notes.imgPath = selectedImagePath
//                notes.webLink = webLink

                NotesDatabase.getDatabase(it).notesDao().updateNote(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDesc.setText("")
                binding.layoutImage.visibility = View.GONE
                binding.imgNote.visibility = View.GONE
                binding.tvWebLink.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun checkWebUrl() {
        if (Patterns.WEB_URL.matcher(binding.etWebLink.text.toString()).matches()) {
            binding.layoutWebUrl.visibility = View.GONE
            binding.etWebLink.isEnabled = false
            binding.tvWebLink.visibility = View.VISIBLE
            binding.tvWebLink.text = binding.etWebLink.text.toString()

        } else {
            Toast.makeText(requireContext(), "Url is not valid", Toast.LENGTH_SHORT).show()
        }
    }

    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            var actionColor = p1!!.getStringExtra("action")

            when (actionColor!!) {

                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Orange" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

//                "Image" ->{
//                    readStorageTask()
//                    layoutWebUrl.visibility = View.GONE
//                }

                "WebUrl" -> {
                    binding.layoutWebUrl.visibility = View.VISIBLE
                }
                "DeleteNote" -> {
                    //delete note
                    deleteNote()
                }

                else -> {
                    binding.layoutImage.visibility = View.GONE
                    binding.imgNote.visibility = View.GONE
                    binding.layoutWebUrl.visibility = View.GONE
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

}
