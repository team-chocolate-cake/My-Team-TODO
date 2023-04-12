package com.chocolatecake.todoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.response.PersonTaskRequset
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.databinding.ItemFiltersBinding
import com.chocolatecake.todoapp.databinding.ItemTaskCardBinding
import com.chocolatecake.todoapp.ui.home.model.HomeItem

class HomeAdapter (
    private var items: List<HomeItem<Any>>,
    private val onClickTask: (id: String) -> Unit
        ) : RecyclerView.Adapter<HomeAdapter.BasicViewHolder>() {

    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        return when (viewType) {
            TYPE_FILTERS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_filters, parent, false)
                ItemFiltersViewHolder(view)
            }
            TYPE_TASKS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_task_card, parent, false)
                ItemTaskCardViewHolder(view)
            }
            else -> throw Exception("UNKNOWN VIEW TYPE")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        when (holder) {
            is ItemFiltersViewHolder -> bindFilters(holder, position)
            is ItemTaskCardViewHolder -> bindTask(holder, position)
        }
    }

    private fun bindFilters(holder: ItemFiltersViewHolder, position: Int) {
        val currentItem = items[position].item as List<Int>
        val context = holder.itemView.context
        val numTodoTasks = currentItem[TO_DO_STATUS]
        val numInProgressTasks = currentItem[IN_PROGRESS_STATUS]
        val numDoneTasks = currentItem[DONE_STATUS]

        with(holder.binding) {
            toDoChip.text = context.getString(R.string.to_do_task, numTodoTasks)
            InProgressChip.text = context.getString(R.string.in_progress_task, numInProgressTasks)
            DoneChip.text = context.getString(R.string.done_task, numDoneTasks)
        }
    }

    private fun bindTask(holder: ItemTaskCardViewHolder, position: Int) {
        val currentItem = items[position].item
        with(holder.binding) {
            if (currentItem is TeamTask) {
                textViewTaskTitle.text = currentItem.titleTeamTask
                textViewTaskDescription.text = currentItem.descriptionTeamTask
                textViewAssignee.text = currentItem.assignee
                val creationTime=currentItem.creationTime.split("T")
                textViewTime.text = creationTime[0]
                textViewDate.text = creationTime[1].substring(0, 5)
                when (currentItem.statusTeamTask) {
                    TO_DO_STATUS -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.todo
                            )
                        )
                    IN_PROGRESS_STATUS -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.in_progress
                            )
                        )
                    DONE_STATUS -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.done_color
                            )
                        )
                }
                textViewAssignee.visibility = View.VISIBLE
                root.setOnClickListener{
                    onClickTask(currentItem.idTeamTask)
                }
            }
            else if (currentItem is PersonTaskRequset) {
                textViewTaskTitle.text = currentItem.titlePersonalTask
                textViewTaskDescription.text = currentItem.descriptionPersonalTask
                val creationTime=currentItem.creationTime.split("T")
                textViewTime.text = creationTime[0]
                textViewDate.text = creationTime[1].substring(0, 5)
                when (currentItem.statusPersonTask) {
                    TO_DO_STATUS -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.todo
                            )
                        )
                    IN_PROGRESS_STATUS -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.in_progress
                            )
                        )
                    DONE_STATUS -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.done_color
                            )
                        )
                }
                textViewAssignee.visibility = View.GONE
                root.setOnClickListener{
                    onClickTask(currentItem.idPersonalTask)
                }
                    }
        }
    }

    class ItemFiltersViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemFiltersBinding.bind(itemView)
    }

    class ItemTaskCardViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemTaskCardBinding.bind(itemView)
    }

    companion object {
        const val TYPE_FILTERS = 1
        const val TYPE_TASKS = 2
        const val TO_DO_STATUS = 0
        const val IN_PROGRESS_STATUS = 1
        const val DONE_STATUS = 2
    }
}