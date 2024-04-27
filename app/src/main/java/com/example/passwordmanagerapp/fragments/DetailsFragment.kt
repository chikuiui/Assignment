package com.example.passwordmanagerapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.databinding.FragmentDetailsBinding
import com.example.passwordmanagerapp.models.Account
import com.example.passwordmanagerapp.security.AESUtils
import com.example.passwordmanagerapp.viewmodels.AccountViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DetailsFragment : BottomSheetDialogFragment(R.layout.fragment_details) {

    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : AccountViewModel
    private val args : DetailsFragmentArgs by navArgs()
    private lateinit var currAccount : Account
    private var aes = AESUtils()
    private var decryptedPass : String? = null
    private var encryptedPass : String? = null

    private var check = false // == SAVE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currAccount = args.account!!

        binding.accType.setText(currAccount.accountName)
        binding.accUserEmail.setText(currAccount.userNameOrEmail)

        Log.e("DETAILS_FRAGMENT","encrypted Pass -> ${currAccount.encryptedPassword}")
        decryptedPass = aes.decrypt(currAccount.encryptedPassword)
        encryptedPass = currAccount.encryptedPassword
        binding.accPassword.setText(decryptedPass)

        binding.accType.isEnabled = false
        binding.accUserEmail.isEnabled = false
        binding.accPassword.isEnabled = false

        binding.delete.setOnClickListener {
            deleteAccount()
        }
        binding.editAndSave.setOnClickListener {
           editAccount()
        }

    }

    private fun deleteAccount() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete a Account")
            setMessage("You want to delete this Account?")
            setPositiveButton("Delete"){_,_->
                viewModel.delete(context,currAccount)
                findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    private fun editAccount() {
        if(!check){ // if check is false means i have to edit
            binding.accType.isEnabled = true
            binding.accUserEmail.isEnabled = true
            binding.accPassword.isEnabled = true
            binding.editAndSave.text = "Save"
            check = true
        }else{ // means they want to save the currentAccount
            val accType = binding.accType.text.toString().trim()
            val accUserAndEmail = binding.accUserEmail.text.toString().trim()
            val accPass = binding.accPassword.text.toString().trim()

            if(accType.isEmpty() || accUserAndEmail.isEmpty() || accPass.isEmpty()){
                Toast.makeText(context,"Fields are Empty!", Toast.LENGTH_SHORT).show()
                return
            }
            val ePass : String?
            // means we do some changes in the current password
            if(accPass != decryptedPass) ePass = aes.encrypt(accPass)
            else ePass = encryptedPass

            Log.e("DETAILS_FRAGMENT","error first")
            val updatedAccount =
                ePass?.let { Account(currAccount.id,accType,accUserAndEmail, it) }

            Log.e("DETAILS_FRAGMENT","error second")
            context?.let {
                if (updatedAccount != null) {
                    viewModel.update(it,updatedAccount)
                }
            }

            binding.accType.isEnabled = false
            binding.accUserEmail.isEnabled = false
            binding.accPassword.isEnabled = false
            binding.editAndSave.text = "Edit"
            check = false
            findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
        }
    }

}