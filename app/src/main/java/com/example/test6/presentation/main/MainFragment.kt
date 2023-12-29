package com.example.test6.presentation.main

import android.content.Context
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
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun setUp() {
        setUpRecyclerView()
    }

    override fun bindViewActionListener() {
        passCodeSetup()
    }

    private fun setUpRecyclerView() {
        adapter = MainFragmentRvAdapter { clickedData ->
            viewModel.handleButtonClick(clickedData)
        }

        binding.recyclerView.layoutManager = NonScrollableGridLayoutManager(requireContext(), 3)
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

    class NonScrollableGridLayoutManager(context: Context, spanCount: Int) :
        GridLayoutManager(context, spanCount) {

        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    private fun passCodeSetup() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passcodeState.collect { state ->
                    with(binding) {
                        passCode.setText(state)

                        if (state == "0934") {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            passCode.setText("")
                            viewModel.resetPasscode()
                        }

                        if (state.length == 4 && state != "0934") {
                            passCode.setText("")
                            viewModel.resetPasscode()
                        }
                    }
                }
            }
        }
    }
}

