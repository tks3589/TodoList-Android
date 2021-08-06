package com.aaron.todolist_android.page

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aaron.todolist_android.R
import com.aaron.todolist_android.TodoViewModel
import com.aaron.todolist_android.UIModePreference
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.coroutines.launch

class SettingsFragment: Fragment() {
    private val GOOGLE_LOGIN = 123
    private var account:GoogleSignInAccount?= null
    private lateinit var mGoogleSignInClient:GoogleSignInClient
    private lateinit var todoViewModel:TodoViewModel
    private var progressDialog:ProgressDialog?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings,container,false)
        checkUiModeStatus(view)
        progressDialog = ProgressDialog(requireContext()).apply {
            setMessage("登入中...")
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        return view
    }

    private fun checkUiModeStatus(view: View){
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                view.theme_switch.isChecked = false
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                view.theme_switch.isChecked = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        theme_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            setTheme(isChecked)
        }
        account = GoogleSignIn.getLastSignedInAccount(context)
        if(account!=null){
            login_status.text = account?.email
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = mGoogleSignInClient.signInIntent

        login_block.setOnClickListener {
            if(account == null) {
                startActivityForResult(signInIntent, GOOGLE_LOGIN)
            }else{
                AlertDialog.Builder(view.context)
                    .setMessage("${account?.displayName}  要登出嗎？")
                    .setPositiveButton("否"){_, _ ->
                    }
                    .setNegativeButton("是"){_, _ ->
                        mGoogleSignInClient.signOut().addOnCompleteListener {
                            login_status.text = "未登入"
                            Toast.makeText(context,"已登出",Toast.LENGTH_SHORT).show()
                            account = null
                        }
                    }.create().show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN && resultCode==Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            account = task.getResult(ApiException::class.java)
            lifecycleScope.launch {
                progressDialog?.show()
                if (todoViewModel.login(account?.id.toString()).indexOf("200")!=-1){
                    progressDialog?.dismiss()
                    login_status.text = account?.email
                    Toast.makeText(context, account?.displayName + " 已登入", Toast.LENGTH_SHORT).show()
                    //Log.d("iddd",account?.id.toString())
                }else{
                    progressDialog?.dismiss()
                    mGoogleSignInClient.signOut()
                    account = null
                    Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setTheme(isChecked: Boolean){
        val uiDataStore = UIModePreference(requireContext())
        lifecycleScope.launch {
            uiDataStore.saveToDataStore(isChecked)
        }
    }

}