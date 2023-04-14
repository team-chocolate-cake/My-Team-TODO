package com.chocolatecake.todoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chocolatecake.todoapp.data.model.response.PersonalTask
import com.chocolatecake.todoapp.data.model.response.TeamTask
import com.chocolatecake.todoapp.databinding.ItemFiltersBinding
import com.chocolatecake.todoapp.databinding.ItemTaskCardBinding
import com.chocolatecake.todoapp.ui.home.model.HomeItemType

//class HomeAdapter (
//    private var items: List<HomeItem<Any>>,
//    private val onClickTask: (id: String) -> Unit
//        ) : RecyclerView.Adapter<HomeAdapter.BasicViewHolder>() {
//
//    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
//        return when (viewType) {
//            TYPE_FILTERS -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_filters, parent, false)
//                ItemFiltersViewHolder(view)
//            }
//            TYPE_TASKS -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_task_card, parent, false)
//                ItemTaskCardViewHolder(view)
//            }
//            else -> throw Exception("UNKNOWN VIEW TYPE")
//        }
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
//        when (holder) {
//            is ItemFiltersViewHolder -> bindFilters(holder, position)
//            is ItemTaskCardViewHolder -> bindTask(holder, position)
//        }
//    }
//
//    private fun bindFilters(holder: ItemFiltersViewHolder, position: Int) {
//        val currentItem = items[position].item as List<Int>
//        val context = holder.itemView.context
//        val numTodoTasks = currentItem[TO_DO_STATUS]
//        val numInProgressTasks = currentItem[IN_PROGRESS_STATUS]
//        val numDoneTasks = currentItem[DONE_STATUS]
//
//        with(holder.binding) {
//            toDoChip.text = context.getString(R.string.to_do_task, numTodoTasks)
//            InProgressChip.text = context.getString(R.string.in_progress_task, numInProgressTasks)
//            DoneChip.text = context.getString(R.string.done_task, numDoneTasks)
//        }
//    }
//
//    private fun bindTask(holder: ItemTaskCardViewHolder, position: Int) {
//        val currentItem = items[position].item
//        with(holder.binding) {
//            if (currentItem is TeamTask) {
//                textViewTaskTitle.text = currentItem.titleTeamTask
//                textViewTaskDescription.text = currentItem.descriptionTeamTask
//                textViewAssignee.text = currentItem.assignee
//                val creationTime=currentItem.creationTime.split("T")
//                textViewTime.text = creationTime[0]
//                textViewDate.text = creationTime[1].substring(0, 5)
//                when (currentItem.statusTeamTask) {
//                    TO_DO_STATUS -> cardDivider
//                        .setBackgroundColor(
//                            ContextCompat.getColor(
//                                holder.itemView.context,
//                                R.color.todo
//                            )
//                        )
//                    IN_PROGRESS_STATUS -> cardDivider
//                        .setBackgroundColor(
//                            ContextCompat.getColor(
//                                holder.itemView.context,
//                                R.color.in_progress
//                            )
//                        )
//                    DONE_STATUS -> cardDivider
//                        .setBackgroundColor(
//                            ContextCompat.getColor(
//                                holder.itemView.context,
//                                R.color.done_color
//                            )
//                        )
//                }
//                textViewAssignee.visibility = View.VISIBLE
//                root.setOnClickListener{
//                    onClickTask(currentItem.idTeamTask)
//                }
//            }
//            else if (currentItem is PersonalTask) {
//                textViewTaskTitle.text = currentItem.titlePersonalTask
//                textViewTaskDescription.text = currentItem.descriptionPersonalTask
//                val creationTime=currentItem.creationTime.split("T")
//                textViewTime.text = creationTime[0]
//                textViewDate.text = creationTime[1].substring(0, 5)
//                when (currentItem.statusPersonTask) {
//                    TO_DO_STATUS -> cardDivider
//                        .setBackgroundColor(
//                            ContextCompat.getColor(
//                                holder.itemView.context,
//                                R.color.todo
//                            )
//                        )
//                    IN_PROGRESS_STATUS -> cardDivider
//                        .setBackgroundColor(
//                            ContextCompat.getColor(
//                                holder.itemView.context,
//                                R.color.in_progress
//                            )
//                        )
//                    DONE_STATUS -> cardDivider
//                        .setBackgroundColor(
//                            ContextCompat.getColor(
//                                holder.itemView.context,
//                                R.color.done_color
//                            )
//                        )
//                }
//                textViewAssignee.visibility = View.GONE
//                root.setOnClickListener{
//                    onClickTask(currentItem.idPersonalTask)
//                }
//                    }
//        }
//    }
//
//    class ItemFiltersViewHolder(itemView: View) : BasicViewHolder(itemView) {
//        val binding = ItemFiltersBinding.bind(itemView)
//    }
//
//    class ItemTaskCardViewHolder(itemView: View) : BasicViewHolder(itemView) {
//        val binding = ItemTaskCardBinding.bind(itemView)
//    }
//
//    companion object {
//        const val TYPE_FILTERS = 1
//        const val TYPE_TASKS = 2
//        const val TO_DO_STATUS = 0
//        const val IN_PROGRESS_STATUS = 1
//        const val DONE_STATUS = 2
//    }
//}

class HomeAdapter(
    private val itemsList: MutableList<HomeItem>,
    private val onClickTask: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                TeamTaskViewHolder(binding, onClickTask)
            }
            HomeItemType.TYPE_PERSONAL_TASK -> {
                val binding = ItemTaskCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PersonalTaskViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList[position]
        when (holder) {
            is FiltersViewHolder -> holder.bind(item as HomeItem.Filters)
            is TeamTaskViewHolder -> holder.bind(item as HomeItem.TeamTaskItem)
            is PersonalTaskViewHolder -> holder.bind(item as HomeItem.PersonalTaskItem)
        }
    }

    override fun getItemCount() = itemsList.size


    sealed class HomeItem(val type: HomeItemType) {
        data class Filters(
            val count : List<Int>
        ): HomeItem(HomeItemType.TYPE_FILTERS)
        data class TeamTaskItem(
          val teamTask: TeamTask
        ) : HomeItem(HomeItemType.TYPE_TEAM_TASK)

        data class PersonalTaskItem(
            val personalTask: PersonalTask
        ) : HomeItem(HomeItemType.TYPE_PERSONAL_TASK)
    }

    enum class HomeItemType {
        TYPE_FILTERS, TYPE_TEAM_TASK,TYPE_PERSONAL_TASK
    }

    class FiltersViewHolder(private val binding: ItemFiltersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.Filters) {
            val filters = item.count
            binding.toDoChip.text = filters[0].toString()
            binding.InProgressChip.text = filters[1].toString()
            binding.DoneChip.text = filters[2].toString()
        }
    }

    class TeamTaskViewHolder(
        private val binding: ItemTaskCardBinding,
        private val onClickTask: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.TeamTaskItem) {

        }
    }

    class PersonalTaskViewHolder(private val binding: ItemTaskCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.PersonalTaskItem) {
//            val task = item.data
//            binding.title.text = task.title
//            binding.description.text = task.description
//            binding.status.text = task.statusPersonalTask
        }
    }
}
