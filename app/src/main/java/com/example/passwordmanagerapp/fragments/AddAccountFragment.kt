package com.example.passwordmanagerapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.databinding.FragmentAddAccountBinding
import com.example.passwordmanagerapp.models.Account
import com.example.passwordmanagerapp.security.AESUtils
import com.example.passwordmanagerapp.viewmodels.AccountViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddAccountFragment : BottomSheetDialogFragment(R.layout.fragment_add_account) {

    private var _binding : FragmentAddAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel
    private var aes = AESUtils()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddAccountBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.passwordInputMeter.setEditText(binding.password)

        binding.addNewAccount.setOnClickListener {
           saveAccount()
        }

    }

    private fun saveAccount() {
        val accountName  = binding.accountName.text.toString().trim()
        val userNameOrEmail = binding.usernameEmail.text.toString().trim()
        val password = binding.password.text.toString().trim()
        if(accountName.isEmpty() || userNameOrEmail.isEmpty() || password.isEmpty()){
            Toast.makeText(context,"Fields are Empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val encryptedPass = aes.encrypt(password)
        val account = encryptedPass?.let { Account(0,accountName,userNameOrEmail, it) }
        Log.e("ADD_FRAGMENT","Encrypted password -> ${account?.encryptedPassword}")
        context?.let {
            if (account != null) {
                viewModel.insert(it,account)
            }
        }
        findNavController().navigate(R.id.action_addAccountFragment_to_mainFragment)
    }




}