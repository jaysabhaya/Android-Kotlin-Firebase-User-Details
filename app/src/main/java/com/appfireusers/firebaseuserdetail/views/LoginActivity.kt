package com.appfireusers.firebaseuserdetail.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.appfireusers.firebaseuserdetail.R
import com.appfireusers.firebaseuserdetail.base.BaseActivity
import com.appfireusers.firebaseuserdetail.databinding.ActivityLoginBinding
import com.appfireusers.firebaseuserdetail.viewmodel.LoginViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {
            showToast("onVerificationFailed")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)

            viewModel.codeSent = verificationId
        }

    }

    private lateinit var binding: ActivityLoginBinding
    private var viewModel: LoginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel.auth = FirebaseAuth.getInstance()
        initObserver()
        initListener()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModel.auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Login Success")
                    viewModel.auth!!.addAuthStateListener { firebaseAuth ->
                        Log.e("user", firebaseAuth.currentUser?.uid)
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("userId", firebaseAuth.currentUser?.uid)
                        startActivity(intent)
                    }
                } else {
                    showToast("OTP is wrong")
                }
            }
    }

    private fun initObserver() {
        viewModel.mobNoError.observe(this, Observer {
            if (it != null && it != 1) {
                showSnackBar(binding.rlMain, getString(it))
            }
        })
    }

    private fun initListener() {

        binding.btnSignIn.setOnClickListener {
            if (viewModel.loginValidate(
                    binding.etMobNo.text.toString()
                )
            ) {
                if (binding.pinView.visibility == View.GONE) {
                    binding.pinView.visibility = View.VISIBLE
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        binding.etMobNo.text.toString(),
                        60,
                        TimeUnit.SECONDS,
                        this,
                        callbacks
                    )
                } else {
                    if (!viewModel.codeSent.isNullOrEmpty()) {
                        val credential = PhoneAuthProvider.getCredential(
                            viewModel.codeSent!!,
                            binding.pinView.value
                        )
                        signInWithPhoneAuthCredential(credential)
                    }
                }
            }
        }
    }
}
