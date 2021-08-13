package com.bake.bakery.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bake.bakery.R
import com.bake.bakery.data.UserHelper
import com.bake.bakery.databinding.ActivityLoginBinding
import com.bake.bakery.dummy.DataDummy

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDbHelper: UserHelper
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDbHelper = UserHelper.getInstance(this)
        pref = getSharedPreferences("auth", MODE_PRIVATE)

        if (pref.getBoolean("logged_in", false)) gotoMainActivity()

        binding.btnLogin.setOnClickListener {
            login(
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        DataDummy.generateDummyUser(this)
    }

    private fun login(username: String, password: String) {
        userDbHelper.open()
        val admin = userDbHelper.queryByUsername(username)
        if (admin?.password == password) {
            val editor = pref.edit()
            editor.putBoolean("logged_in", true)
            editor.apply()
            gotoMainActivity()
        } else {
            Toast.makeText(this, getString(R.string.wrong_auth_text), Toast.LENGTH_SHORT).show()
        }
        userDbHelper.close()
    }

    private fun gotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}