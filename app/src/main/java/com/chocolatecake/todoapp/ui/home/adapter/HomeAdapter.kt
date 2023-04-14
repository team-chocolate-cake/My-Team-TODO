package com.chocolatecake.todoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.databinding.ItemFiltersBinding
import com.chocolatecake.todoapp.databinding.ItemTaskCardBinding
import com.chocolatecake.todoapp.ui.home.model.HomeItem
import com.chocolatecake.todoapp.ui.home.model.HomeItemType
import com.google.android.material.chip.Chip

class HomeAdapter(
    private var itemsList: MutableList<HomeItem>,
    private val onClickTeamTask: (String) -> Unit,
    private val onClickPersonalTask: (String) -> Unit,
    private val onStatusChange: (Set<Int>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    fun setData(newList: List<HomeItem.TeamTaskItem>){
//        val diffResult = DiffUtil.calculateDiff(HomeDiffutils())
//    }

    fun setSelectedTabData(newItems: MutableList<HomeItem>) {
        itemsList = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return itemsList[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (HomeItemType.values()[viewType]) {
            HomeItemType.TYPE_FILTERS -> {
                val binding = ItemFiltersBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FiltersViewHolder(binding)
            }
            HomeItemType.TYPE_TEAM_TASK -> {
                val binding = ItemTaskCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TeamTaskViewHolder(binding, onClickTeamTask)
            }
            HomeItemType.TYPE_PERSONAL_TASK -> {
                val binding = ItemTaskCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PersonalTaskViewHolder(binding, onClickPersonalTask)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        when (holder) {
            is FiltersViewHolder -> holder.bind(item as HomeItem.Filters, onStatusChange)
            is TeamTaskViewHolder -> holder.bind(item as HomeItem.TeamTaskItem)
            is PersonalTaskViewHolder -> holder.bind(item as HomeItem.PersonalTaskItem)
        }
    }

    override fun getItemCount() = itemsList.size

    class FiltersViewHolder(private val binding: ItemFiltersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.Filters, onStatusChange: (Set<Int>) -> Unit) {
            val filters = item.counts
            val context = itemView.context

            binding.toDoChip.text = context.getString(R.string.to_do_task, filters[0])
            binding.InProgressChip.text = context.getString(R.string.in_progress_task, filters[1])
            binding.DoneChip.text = context.getString(R.string.done_task, filters[2])

            binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                val selectedChipPosition = mutableListOf<Int>()
                group.forEachIndexed { index, chip ->
                    if ((chip as Chip).isChecked) {
                        selectedChipPosition.add(index)
                    }
                }
                onStatusChange(selectedChipPosition.toSet())
            }
        }
    }


    class TeamTaskViewHolder(
        private val binding: ItemTaskCardBinding,
        private val onClickTask: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.TeamTaskItem) {
            with(item.teamTask) {
                with(binding) {
                    textViewTaskTitle.text = titleTeamTask
                    textViewTaskDescription.text = descriptionTeamTask
                    textViewAssignee.text = assignee
                    textViewDate.text = getDate(creationTime)
                    textViewTime.text = getTime(creationTime)
                    when (statusTeamTask) {
                        0 -> cardDivider
                            .setBackgroundColor(
                                ContextCompat.getColor(
                                  textViewAssignee.context,
                                    R.color.todo
                                )
                            )
                        1 -> cardDivider
                            .setBackgroundColor(
                                ContextCompat.getColor(
                                    textViewAssignee.context,
                                    R.color.in_progress
                                )
                            )
                        2 -> cardDivider
                            .setBackgroundColor(
                                ContextCompat.getColor(
                                    textViewAssignee.context,
                                    R.color.done_color
                                )
                            )
                    }
                    textViewAssignee.visibility = View.VISIBLE
                    root.setOnClickListener { onClickTask(idTeamTask) }
                }
            }
        }
    }

    class PersonalTaskViewHolder(
        private val binding: ItemTaskCardBinding,
        private val onClickTask: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.PersonalTaskItem) {
            with(item.personalTask) {
                with(binding) {
                    textViewTaskTitle.text = titlePersonalTask
                    textViewTaskDescription.text = descriptionPersonalTask
                    textViewDate.text = getDate(creationTime)
                    textViewTime.text = getTime(creationTime)

                    when (statusPersonalTask) {
                    0 -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                textViewTaskTitle.context,
                                R.color.todo
                            )
                        )
                    1 -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                textViewTaskTitle.context,
                                R.color.in_progress
                            )
                        )
                    2 -> cardDivider
                        .setBackgroundColor(
                            ContextCompat.getColor(
                                textViewTaskTitle.context,
                                R.color.done_color
                            )
                        )
                }
                    textViewAssignee.visibility = View.GONE
                    root.setOnClickListener { onClickTask(idPersonalTask) }
                }
            }
        }
    }

    companion object {

        private fun getTime(creationTime: String): String {
            return creationTime.split("T")[1].take(5)
        }

        private fun getDate(creationTime: String): String {
            return creationTime.split("T").first()
        }
    }
}
