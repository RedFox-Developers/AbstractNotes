package dev.redfox.abstractnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.redfox.abstractnotes.adapter.NotesAdapter
import dev.redfox.abstractnotes.database.Notes
import dev.redfox.abstractnotes.database.NotesDatabase
import dev.redfox.abstractnotes.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).notesDao().getAllNotes()
                notesAdapter!!.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                binding.recyclerView.adapter = notesAdapter
            }
        }
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }
    }

}