package com.chocolatecake.todoapp.ui.fragment.add_new_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.todoapp.databinding.BottomSheetConfirmationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateTaskConfirmDialog(private val createTask: () -> Unit) : BottomSheetDialogFragment(){

    private lateinit var binding: BottomSheetConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=BottomSheetConfirmationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonConfirm.setOnClickListener {
            createTask()
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }


}