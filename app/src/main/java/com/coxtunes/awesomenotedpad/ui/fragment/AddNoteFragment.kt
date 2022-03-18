package com.coxtunes.awesomenotedpad.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.coxtunes.awesomenotedpad.R
import com.coxtunes.awesomenotedpad.common.base.BaseFragment
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import com.coxtunes.awesomenotedpad.databinding.FragmentAddNoteBinding
import com.coxtunes.awesomenotedpad.ui.uistate.AddNotePadUIState
import com.coxtunes.awesomenotedpad.ui.viewmodel.NotePadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>() {

    private val viewModel: NotePadViewModel by activityViewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_add_note

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        setupObservers()
    }

    private fun initViews() {
        binding.addNoteButton.setOnClickListener {
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
                        title = binding.noteTitleBox.text.toString(),
                        created_at = binding.noteCreatedatBox.text.toString(),
                        description = binding.noteDescriptionBox.text.toString()
                    )
                    viewModel.addNote(notePad)
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
                viewModel.adduiState.collect {
                    handleUiState(it)
                }
            }
        }
    }

    private fun handleUiState(uiState: AddNotePadUIState) {
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