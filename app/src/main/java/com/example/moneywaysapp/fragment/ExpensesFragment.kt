package com.example.moneywaysapp.fragment

// CategoriesFragment.kt
class ExpensesFragment : Fragment() {
    private lateinit var binding: FragmentExpensesBinding
    private lateinit var viewModel: BudgetViewModel
    private lateinit var expensesAdapter: ExpensesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BudgetViewModel::class.java]

        setupUI()
        observeData()
    }

    private fun setupUI() {
        expensesAdapter = ExpensesAdapter(emptyList()) { expense ->
            showExpenseDetails(expense)
        }
        binding.expensesList.adapter = expensesAdapter
        binding.expensesList.layoutManager = LinearLayoutManager(requireContext())

        binding.addExpenseButton.setOnClickListener {
            showAddExpenseDialog()
        }

        // Setup period spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.period_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.periodSpinner.adapter = adapter
        }

        binding.periodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateExpensesList()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Setup category spinner
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.name }
            val allCategories = listOf("All Categories") + categoryNames
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, allCategories)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
        }

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateExpensesList()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Date pickers
        binding.startDateEditText.setOnClickListener {
            showDatePicker(binding.startDateEditText)
        }

        binding.endDateEditText.setOnClickListener {
            showDatePicker(binding.endDateEditText)
        }
    }

    private fun observeData() {
        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            updateExpensesList()
        }
    }

    private fun updateExpensesList() {
        val period = binding.periodSpinner.selectedItem.toString()
        val categoryPos = binding.categorySpinner.selectedItemPosition
        val categories = viewModel.categories.value ?: emptyList()
        val categoryId = if (categoryPos == 0 || categories.isEmpty()) null else categories[categoryPos - 1].id

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1

        val (startDate, endDate) = when (period) {
            "This Month" -> {
                "$year-${month.toString().padStart(2, '0')}-01" to
                        "$year-${month.toString().padStart(2, '0')}-${calendar.getActualMaximum(Calendar.DAY_OF_MONTH)}"
            }
            "Last Month" -> {
                calendar.add(Calendar.MONTH, -1)
                val lastYear = calendar.get(Calendar.YEAR)
                val lastMonth = calendar.get(Calendar.MONTH) + 1
                "$lastYear-${lastMonth.toString().padStart(2, '0')}-01" to
                        "$lastYear-${lastMonth.toString().padStart(2, '0')}-${calendar.getActualMaximum(Calendar.DAY_OF_MONTH)}"
            }
            "This Year" -> {
                "$year-01-01" to "$year-12-31"
            }
            "Custom" -> {
                val start = binding.startDateEditText.text.toString()
                val end = binding.endDateEditText.text.toString()
                if (start.isBlank() || end.isBlank()) {
                    return
                }
                start to end
            }
            else -> { // All Time
                null to null
            }
        }

        viewModelScope.launch {
            val expenses = if (startDate != null && endDate != null) {
                if (categoryId == null) {
                    viewModel.getExpensesByDateRange(startDate, endDate)
                } else {
                    viewModel.getExpensesByCategoryAndDate(categoryId, startDate, endDate)
                }
            } else {
                viewModel.getAllExpenses()
            }

            expensesAdapter.updateExpenses(expenses)
            binding.noExpensesText.visibility = if (expenses.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showAddExpenseDialog() {
        // Implement add expense dialog
    }

    private fun showDatePicker(editText: EditText) {
        // Implement date picker
    }

    private fun showExpenseDetails(expense: Expense) {
        // Implement expense details dialog
    }
}