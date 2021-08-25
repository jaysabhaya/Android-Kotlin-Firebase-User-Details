package com.appfireusers.firebaseuserdetail.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appfireusers.firebaseuserdetail.R
import com.appfireusers.firebaseuserdetail.databinding.ActivityHomeBinding
import com.appfireusers.firebaseuserdetail.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    var viewModel: HomeViewModel = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel.userId = intent.getStringExtra("userId")
    }
}