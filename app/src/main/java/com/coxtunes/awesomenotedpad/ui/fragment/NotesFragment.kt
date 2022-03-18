package com.coxtunes.awesomenotedpad.ui.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.coxtunes.awesomenotedpad.R
import com.coxtunes.awesomenotedpad.common.base.BaseFragment
import com.coxtunes.awesomenotedpad.databinding.FragmentNotesBinding
import com.coxtunes.awesomenotedpad.ui.adapter.NotepadAdapter
import com.coxtunes.awesomenotedpad.ui.uistate.NotePadUIState
import com.coxtunes.awesomenotedpad.ui.viewmodel.NotePadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>() {

    @Inject
    lateinit var notepadAdapter: NotepadAdapter
    private val viewModel: NotePadViewModel by activityViewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_notes

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        subscribeObserver()
    }

    private fun initViews() {
        binding.apply {
            viewModel.getNotes()
            notesRecyclerView.apply {
                hasFixedSize()
                layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
                adapter = notepadAdapter
            }
            notepadAdapter.actionClickItem = {
                val bundle = bundleOf("note" to it)
                findNavController().navigate(
                    R.id.action_notesFragment_to_updateNoteFragment,
                    bundle
                )
            }

            addNotebutton.setOnClickListener {
                navController.navigate(R.id.action_notesFragment_to_addNoteFragment)
            }

            /** -- Delete Note-- **/
            notepadAdapter.deleteActionClickItem = {
                viewModel.deleteNote(it)
            }
        }
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    handleUiState(it)
                }
            }
        }
    }

    private fun handleUiState(uiState: NotePadUIState) {
        uiState.apply {
            if (message.isNotEmpty()) showToastMessage(message)
            if (notesList.isNotEmpty()) binding.placeholderText.text = "" else binding.placeholderText.text = "No Notes Added!"
            notepadAdapter.addNotes(notesList)
        }
    }

    override fun backPressedAction() {
        findNavController().navigateUp()
    }
}