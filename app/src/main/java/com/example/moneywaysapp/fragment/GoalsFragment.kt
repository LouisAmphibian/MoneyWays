package com.example.moneywaysapp.fragment

// GoalsFragment.kt
class GoalsFragment : Fragment() {
    private lateinit var binding: FragmentGoalsBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        setupUI()
        observeData()
    }

    private fun setupUI() {
        binding.saveButton.setOnClickListener {
            val min = binding.minGoalEditText.text.toString().toDoubleOrNull() ?: 0.0
            val max = binding.maxGoalEditText.text.toString().toDoubleOrNull() ?: 0.0

            if (min > max) {
                Toast.makeText(requireContext(), "Minimum goal cannot be greater than maximum", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.setBudgetGoal(min, max)
                Toast.makeText(requireContext(), "Goals saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeData() {
        viewModel.getUserBudgetGoal().observe(viewLifecycleOwner) { goal ->
            goal?.let {
                binding.minGoalEditText.setText("%.2f".format(it.minMonthlyGoal))
                binding.maxGoalEditText.setText("%.2f".format(it.maxMonthlyGoal))
                updateProgress(it)
            } ?: run {
                binding.minGoalEditText.setText("0.00")
                binding.maxGoalEditText.setText("0.00")
                updateProgress(BudgetGoal(0, 0, 0.0, 0.0))
            }
        }

        // Get current month expenses for progress
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val startDate = "$year-${month.toString().padStart(2, '0')}-01"
        val endDate = "$year-${month.toString().padStart(2, '0')}-${calendar.getActualMaximum(Calendar.DAY_OF_MONTH)}"

        viewModel.getExpensesByDateRange(startDate, endDate).observe(viewLifecycleOwner) { expenses ->
            val total = expenses.sumOf { it.amount }
            binding.currentSpendingText.text = "$${"%.2f".format(total)}"

            viewModel.getUserBudgetGoal().value?.let { goal ->
                updateProgress(goal.copy()) // Force update with current spending
            }
        }
    }

    private fun updateProgress(goal: BudgetGoal) {
        val current = binding.currentSpendingText.text.toString().removePrefix("$").toDoubleOrNull() ?: 0.0

        binding.minGoalText.text = "$${"%.2f".format(goal.minMonthlyGoal)}"
        binding.maxGoalText.text = "$${"%.2f".format(goal.maxMonthlyGoal)}"
        binding.currentSpendingText.text = "$${"%.2f".format(current)}"

        if (goal.maxMonthlyGoal > 0) {
            val progress = ((current - goal.minMonthlyGoal) / (goal.maxMonthlyGoal - goal.minMonthlyGoal) * 100).coerceIn(0.0, 100.0)
            binding.progressBar.progress = progress.toInt()

            when {
                current < goal.minMonthlyGoal -> binding.progressBar.progressTintList = ColorStateList.valueOf(Color.GRAY)
                current > goal.maxMonthlyGoal -> binding.progressBar.progressTintList = ColorStateList.valueOf(Color.RED)
                else -> binding.progressBar.progressTintList = ColorStateList.valueOf(Color.BLUE)
            }
        } else {
            binding.progressBar.progress = 0
        }
    }
}