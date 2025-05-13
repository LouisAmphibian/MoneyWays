package com.example.moneywaysapp.fragment

// DashboardFragment.kt
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        setupUI()
        observeData()
    }

    private fun setupUI() {
        binding.refreshLayout.setOnRefreshListener {
            observeData()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun observeData() {
        viewModel.currentUser.value?.let { user ->
            // Get current month expenses
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val startDate = "$year-${month.toString().padStart(2, '0')}-01"
            val endDate = "$year-${month.toString().padStart(2, '0')}-${calendar.getActualMaximum(Calendar.DAY_OF_MONTH)}"

            // Monthly spending
            viewModel.getExpensesByDateRange(startDate, endDate).observe(viewLifecycleOwner) { expenses ->
                val total = expenses.sumOf { it.amount }
                binding.monthlySpentText.text = "$${"%.2f".format(total)}"

                viewModel.getUserBudgetGoal().observe(viewLifecycleOwner) { goal ->
                    goal?.let {
                        binding.monthlyGoalText.text = "$${"%.2f".format(it.minMonthlyGoal)} - $${"%.2f".format(it.maxMonthlyGoal)}"

                        val progress = if (it.maxMonthlyGoal > 0) {
                            (total / it.maxMonthlyGoal * 100).toInt()
                        } else {
                            0
                        }.coerceIn(0, 100)

                        binding.budgetProgressBar.progress = progress
                        binding.budgetProgressText.text = "$progress% of your budget"

                        when {
                            progress > 100 -> binding.budgetProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                            progress > 80 -> binding.budgetProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW))
                            else -> binding.budgetProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN))
                        }
                    } ?: run {
                        binding.monthlyGoalText.text = "$0.00 - $0.00"
                        binding.budgetProgressBar.progress = 0
                        binding.budgetProgressText.text = "No budget set"
                    }
                }
            }

            // Recent expenses
            viewModel.getUserExpenses().observe(viewLifecycleOwner) { expenses ->
                if (expenses.isEmpty()) {
                    binding.recentExpensesText.visibility = View.GONE
                    binding.recentExpensesList.visibility = View.GONE
                    binding.noExpensesText.visibility = View.VISIBLE
                } else {
                    binding.recentExpensesText.visibility = View.VISIBLE
                    binding.recentExpensesList.visibility = View.VISIBLE
                    binding.noExpensesText.visibility = View.GONE

                    val recentExpenses = expenses.take(5)
                    binding.recentExpensesList.adapter = RecentExpensesAdapter(recentExpenses)
                }
            }

            // Category breakdown
            viewModel.getUserCategories().observe(viewLifecycleOwner) { categories ->
                if (categories.isEmpty()) {
                    binding.categoryBreakdownText.visibility = View.GONE
                    binding.categoryChart.visibility = View.GONE
                    binding.categoryList.visibility = View.GONE
                    binding.noCategoriesText.visibility = View.VISIBLE
                } else {
                    binding.categoryBreakdownText.visibility = View.VISIBLE
                    binding.categoryChart.visibility = View.VISIBLE
                    binding.categoryList.visibility = View.VISIBLE
                    binding.noCategoriesText.visibility = View.GONE

                    viewModel.getExpensesByDateRange(startDate, endDate).observe(viewLifecycleOwner) { expenses ->
                        val categoryMap = mutableMapOf<Long, Double>()
                        categories.forEach { category ->
                            categoryMap[category.id] = expenses.filter { it.categoryId == category.id }.sumOf { it.amount }
                        }

                        setupPieChart(categories, categoryMap)
                        setupCategoryList(categories, categoryMap)
                    }
                }
            }
        }
    }

    private fun setupPieChart(categories: List<Category>, categoryMap: Map<Long, Double>) {
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()

        categories.forEach { category ->
            val amount = categoryMap[category.id] ?: 0.0
            if (amount > 0) {
                entries.add(PieEntry(amount.toFloat(), category.name))
                colors.add(Color.parseColor(category.color))
            }
        }

        val dataSet = PieDataSet(entries, "Categories")
        dataSet.colors = colors
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$${"%.2f".format(value.toDouble())}"
            }
        })

        binding.categoryChart.data = data
        binding.categoryChart.description.isEnabled = false
        binding.categoryChart.legend.isEnabled = false
        binding.categoryChart.setEntryLabelColor(Color.BLACK)
        binding.categoryChart.setEntryLabelTextSize(12f)
        binding.categoryChart.invalidate()
    }

    private fun setupCategoryList(categories: List<Category>, categoryMap: Map<Long, Double>) {
        val sortedCategories = categories.sortedByDescending { categoryMap[it.id] ?: 0.0 }

        binding.categoryList.adapter = CategoryBreakdownAdapter(sortedCategories, categoryMap)
    }
}

// RecentExpensesAdapter.kt
class RecentExpensesAdapter(private val expenses: List<Expense>) : RecyclerView.Adapter<RecentExpensesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.expenseDescription)
        val date: TextView = view.findViewById(R.id.expenseDate)
        val amount: TextView = view.findViewById(R.id.expenseAmount)
        val category: TextView = view.findViewById(R.id.expenseCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = expenses[position]
        holder.description.text = expense.description
        holder.date.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expense.date)!!)
        holder.amount.text = "$${"%.2f".format(expense.amount)}"
        // Note: You'll need to get category name from somewhere - either pass it in or query it
    }

    override fun getItemCount() = expenses.size
}

// CategoryBreakdownAdapter.kt
class CategoryBreakdownAdapter(
    private val categories: List<Category>,
    private val categoryMap: Map<Long, Double>
) : RecyclerView.Adapter<CategoryBreakdownAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.categoryName)
        val amount: TextView = view.findViewById(R.id.categoryAmount)
        val color: View = view.findViewById(R.id.categoryColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_breakdown, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        val amount = categoryMap[category.id] ?: 0.0

        holder.name.text = category.name
        holder.amount.text = "$${"%.2f".format(amount)}"
        holder.color.setBackgroundColor(Color.parseColor(category.color))
    }

    override fun getItemCount() = categories.size
}