package com.androiddevs.shoppinglist.ui.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.shoppinglist.R
import com.androiddevs.shoppinglist.data.db.ShoppingDatabase
import com.androiddevs.shoppinglist.data.db.entities.ShoppingItem
import com.androiddevs.shoppinglist.data.repositories.ShoppingRepository
import com.androiddevs.shoppinglist.databinding.ActivityShoppingBinding
import com.androiddevs.shoppinglist.other.ShoppingItemAdapter

class ShoppingActivity : AppCompatActivity() {

    private val binding get() = _binding!!
    private var _binding: ActivityShoppingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)
        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItem().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener {
            AddShoppingItemDialog(this,
                object : AddDialogListener{
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }
}