package com.example.moneyways

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moneywaysapp.SignupActivity

// MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        // Setup ViewPager with fragments
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DashboardFragment(), "Dashboard")
        adapter.addFragment(ExpensesFragment(), "Expenses")
        adapter.addFragment(CategoriesFragment(), "Categories")
        adapter.addFragment(GoalsFragment(), "Goals")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        // Set icons for tabs
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_dashboard)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_expenses)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_categories)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_goals)

        //
        // Logout button
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Observe current user
        viewModel.currentUser.observe(this) { user ->
            if (user == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                binding.welcomeText.text = "Welcome, ${user.username}"
            }
        }
    }
}

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
}