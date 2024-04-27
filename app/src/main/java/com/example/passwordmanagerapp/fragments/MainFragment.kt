package com.example.passwordmanagerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.adapters.MyAdapter
import com.example.passwordmanagerapp.databinding.FragmentMainBinding
import com.example.passwordmanagerapp.viewmodels.AccountViewModel


class MainFragment : Fragment(R.layout.fragment_main) {


    // to avoid memory leaks when we go to other fragments
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : MyAdapter
    private lateinit var viewModel: AccountViewModel
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
          _binding = FragmentMainBinding.inflate(inflater,container,false)
          viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
          return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.addAccount.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addAccountFragment)
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = MyAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        context?.let { viewModel.getAllAccounts(it) }
        viewModel.accountList.observe(viewLifecycleOwner, Observer {
            adapter.setContentList(it)
            binding.recyclerView.also { recycler ->
                recycler.adapter = adapter
            }
        })

    }


}