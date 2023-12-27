package com.example.test6.presentation.main

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test6.MainFragmentRvAdapter
import com.example.test6.R
import com.example.test6.data.Data
import com.example.test6.data.Type
import com.example.test6.databinding.FragmentMainBinding
import com.example.test6.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var adapter: MainFragmentRvAdapter
    private lateinit var passcodeTextView: TextView
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun bindViewActionListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passcodeState.collect { passcode ->
                    if (passcode != "Success") {
                        passcodeTextView.text = passcode
                    }

                    if (passcode == "Success") {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun setUp() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        passcodeTextView = binding.password
        adapter = MainFragmentRvAdapter { clickedData ->
            viewModel.handleButtonClick(clickedData)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        val numericButtons = listOf(
            Data(1, Type.NUMBER),
            Data(2, Type.NUMBER),
            Data(3, Type.NUMBER),
            Data(4, Type.NUMBER),
            Data(5, Type.NUMBER),
            Data(6, Type.NUMBER),
            Data(7, Type.NUMBER),
            Data(8, Type.NUMBER),
            Data(9, Type.NUMBER),
            Data(R.drawable.touch_id, Type.FINGERPRINT, R.drawable.touch_id),
            Data(0, Type.NUMBER),
            Data(R.drawable.ic_remove, Type.DELETE, R.drawable.ic_remove)
        )

        adapter.submitList(numericButtons)
    }
}

