package com.example.moneywaysapp.adapter

// ExpensesAdapter.kt
class ExpensesAdapter(
    private var expenses: List<Expense>,
    private val onItemClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.expenseDescription)
        val date: TextView = view.findViewById(R.id.expenseDate)
        val amount: TextView = view.findViewById(R.id.expenseAmount)
        val category: TextView = view.findViewById(R.id.expenseCategory)
        val photoIndicator: ImageView = view.findViewById(R.id.photoIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = expenses[position]
        holder.description.text = expense.description
        holder.date.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            .format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expense.date)!!)
        holder.amount.text = "$${"%.2f".format(expense.amount)}"

        // You'll need to get the category name from somewhere - either pass it in or query it
        // For now, we'll just show the category ID
        holder.category.text = "Category ID: ${expense.categoryId}"

        // Show photo indicator if photo exists
        holder.photoIndicator.visibility = if (expense.photoPath != null) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onItemClick(expense)
        }
    }

    override fun getItemCount() = expenses.size

    fun updateExpenses(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}