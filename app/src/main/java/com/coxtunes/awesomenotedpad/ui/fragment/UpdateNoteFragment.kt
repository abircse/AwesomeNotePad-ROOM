package com.coxtunes.awesomenotedpad.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.coxtunes.awesomenotedpad.R
import com.coxtunes.awesomenotedpad.common.base.BaseFragment
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import com.coxtunes.awesomenotedpad.databinding.FragmentUpdateNoteBinding
import com.coxtunes.awesomenotedpad.ui.uistate.AddNotePadUIState
import com.coxtunes.awesomenotedpad.ui.uistate.UpdateNotePadUIState
import com.coxtunes.awesomenotedpad.ui.viewmodel.NotePadViewModel
import kotlinx.coroutines.launch
import java.util.*


class UpdateNoteFragment : BaseFragment<FragmentUpdateNoteBinding>() {

    private val viewModel: NotePadViewModel by activityViewModels()
    lateinit var notepaad: NotePad


    override val layoutRes: Int
        get() = R.layout.fragment_update_note

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        setupObservers()
    }

    private fun initViews() {

        notepaad = requireArguments().getParcelable<NotePad>("note") as NotePad
        binding.noteTitleBox.setText(notepaad.title)
        binding.noteCreatedatBox.setText(notepaad.created_at)
        binding.noteDescriptionBox.setText(notepaad.description)

        binding.updateNoteButton.setOnClickListener {
            when {
                binding.noteTitleBox.text.toString().isEmpty() -> {
                    showToastMessage("Title is Required")
                }
                binding.noteCreatedatBox.text.toString().isEmpty() -> {
                    showToastMessage("Date is Required")
                }
                binding.noteDescriptionBox.text.toString().isEmpty() -> {
                    showToastMessage("Description is Required")
                }
                else -> {
                    val notePad = NotePad(
                        id = notepaad.id,
                        title = binding.noteTitleBox.text.toString(),
                        created_at = binding.noteCreatedatBox.text.toString(),
                        description = binding.noteDescriptionBox.text.toString()
                    )
                    viewModel.updateNote(notePad)
                    binding.apply {
                        noteTitleBox.text?.clear()
                        noteCreatedatBox.text?.clear()
                        noteDescriptionBox.text?.clear()
                    }
                }
            }
        }

        binding.noteCreatedatBox.setOnClickListener {
            onDateOfBirthSelection()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.updateuiState.collect {
                    handleUiState(it)
                }
            }
        }
    }

    private fun handleUiState(uiState: UpdateNotePadUIState) {
        uiState.apply {
            if (isLoading) showLoading(true) else showLoading(false)
            if (message.isNotEmpty()) showToastMessage(message)
        }
    }

    fun onDateOfBirthSelection() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePicker =
            DatePickerDialog(requireActivity(), { datePicker, i, i2, i3 ->
                val dob = "${i2 + 1}/${i3}/${i}"
                binding.noteCreatedatBox.setText(dob)
            }, year, month, day)
        datePicker.show()
    }

    override fun backPressedAction() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearState()
    }
}