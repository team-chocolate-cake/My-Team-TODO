package com.chocolatecake.todoapp.ui.task_details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.databinding.BottomSheetConfirmationBinding
import com.chocolatecake.todoapp.databinding.BottomSheetStatusChangeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangeStatusBottomSheet(
    private val updateStatus: (status: Int) -> Unit,
    private val oldStatus: Int
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetStatusChangeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetStatusChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallBacks()
    }

    private fun addCallBacks() {
        val id = when (oldStatus) {
            0 -> R.id.button_todo
            1 -> R.id.button_in_progress
            else -> R.id.button_done
        }
        with(binding) {
            toggleGroupStatuses.check(id)
            buttonConfirmStatus.setOnClickListener {
                val selectedStatus = when (toggleGroupStatuses.checkedButtonId) {
                    R.id.button_todo -> 0
                    R.id.button_in_progress -> 1
                    else -> 2
                }
                updateStatus(selectedStatus)
                dismiss()
            }
        }

    }
}