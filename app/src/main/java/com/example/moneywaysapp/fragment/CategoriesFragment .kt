package com.example.moneywaysapp.fragment
/*
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: BudgetViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BudgetViewModel::class.java]

        setupUI()
        observeData()
    }

    private fun setupUI() {
        categoriesAdapter = CategoriesAdapter(emptyList()) { category ->
            showDeleteCategoryDialog(category)
        }
        binding.categoriesList.adapter = categoriesAdapter
        binding.categoriesList.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.addCategoryButton.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.updateCategories(categories)
            binding.noCategoriesText.visibility = if (categories.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showAddCategoryDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_category)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val nameEditText = dialog.findViewById<EditText>(R.id.nameEditText)
        val colorGroup = dialog.findViewById<RadioGroup>(R.id.colorGroup)
        val saveButton = dialog.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            if (name.isBlank()) {
                Toast.makeText(requireContext(), "Please enter a category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedColorId = colorGroup.checkedRadioButtonId
            if (selectedColorId == -1) {
                Toast.makeText(requireContext(), "Please select a color", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val color = when (selectedColorId) {
                R.id.redRadio -> "#FF5252"
                R.id.blueRadio -> "#4285F4"
                R.id.greenRadio -> "#0F9D58"
                R.id.yellowRadio -> "#FFB900"
                R.id.purpleRadio -> "#9C27B0"
                R.id.pinkRadio -> "#FF4081"
                else -> "#4285F4"
            }

            viewModelScope.launch {
                try {
                    viewModel.addCategory(name, color)
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Category added", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

    private fun showDeleteCategoryDialog(category: Category) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Category")
            .setMessage("Are you sure you want to delete '${category.name}'? All expenses in this category will also be deleted.")
            .setPositiveButton("Delete") { _, _ ->
                viewModelScope.launch {
                    viewModel.deleteCategory(category)
                    Toast.makeText(requireContext(), "Category deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

 */