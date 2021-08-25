package com.appfireusers.firebaseuserdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appfireusers.firebaseuserdetail.R
import com.appfireusers.firebaseuserdetail.customview.fab.Fab
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gordonwong.materialsheetfab.MaterialSheetFab

class LoginViewModel : ViewModel() {
    var mobNoError: MutableLiveData<Int> = MutableLiveData()
    var auth: FirebaseAuth? = null
    var codeSent: String? = null

    fun loginValidate(mobno: String): Boolean {
        var isAllOk = true

        when {
            mobno.isEmpty() -> {
                mobNoError.value = R.string.validate_mobile_blank
                isAllOk = false
            }
             mobno.length != 13 -> {
                mobNoError.value = R.string.validate_mobile_length
                isAllOk = false
            }

            else -> {
                mobNoError.value = 1
            }
        }
        return isAllOk
    }
}
