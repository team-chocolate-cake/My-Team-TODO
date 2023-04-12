package com.chocolatecake.todoapp.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.chocolatecake.todoapp.R
import com.chocolatecake.todoapp.data.model.request.UserRequest
import com.chocolatecake.todoapp.data.model.response.RegisterResponse
import com.chocolatecake.todoapp.data.network.services.identity.AuthService
import com.chocolatecake.todoapp.databinding.FragmentRegisterBinding
import com.chocolatecake.todoapp.ui.base.fragment.BaseFragment
import com.chocolatecake.todoapp.ui.home.HomeFragment
import com.chocolatecake.todoapp.ui.login.LoginFragment
import com.chocolatecake.todoapp.util.getUsernameStatus
import com.google.gson.Gson

class RegistrationFragment : BaseFragment<FragmentRegisterBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    private var validationUserName: Boolean = false
    private var validationPassword: Boolean = false
    private var validationConfirm: Boolean = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()
        setupUsernameValidation()
        setPasswordValidation()
        validatePasswordMatch()
        registerButtonClickHandler()
    }

    private fun addCallbacks() {
        binding.apply {
            textViewLogin.setOnClickListener {
                changeFragment(LoginFragment())
            }
        }
    }

    private fun setupUsernameValidation() {
        binding.apply {
            editTextUsername.addTextChangedListener {
                when {
                    editTextUsername.text.isNullOrEmpty() -> showValidationMessage(
                        textViewValidateUserName,
                        getString(R.string.username_cannot_be_empty),
                        false,
                        View.VISIBLE
                    )

                    getUsernameStatus(
                        editTextUsername.text.toString(),
                        root.context
                    ) == getString(R.string.error_validation_user_name_special) -> showValidationMessage(
                        textViewValidateUserName,
                        getString(R.string.error_validation_user_name_special),
                        false,
                        View.VISIBLE
                    )
                    getUsernameStatus(
                        editTextUsername.text.toString(),
                        root.context
                    ) == getString(R.string.error_validation_user_name_space) -> showValidationMessage(
                        textViewValidateUserName,
                        getString(R.string.error_validation_user_name_space),
                        false,
                        View.VISIBLE
                    )
                    getUsernameStatus(
                        editTextUsername.text.toString(),
                        root.context
                    ) == getString(R.string.error_validation_user_name_should_grater_the_limit) -> showValidationMessage(
                        textViewValidateUserName,
                        getString(R.string.error_validation_user_name_should_grater_the_limit),
                        false,
                        View.VISIBLE
                    )
                    getUsernameStatus(
                        editTextUsername.text.toString(),
                        root.context
                    ) == getString(R.string.error_validation_user_name_start_with_digit) -> showValidationMessage(
                        textViewValidateUserName,
                        getString(R.string.error_validation_user_name_start_with_digit),
                        false,
                        View.VISIBLE
                    )
                    else -> showValidationMessage(textViewValidateUserName)
                }
            }

        }
    }

    private fun setPasswordValidation() {
        binding.apply {
            textInputEditTextPassword.addTextChangedListener { passwordText ->
                val passwordLength = passwordText!!.length
                when {
                    passwordLength == 0 -> showValidationMessage(
                        textViewValidatePassword, getString(R.string.password_cannot_be_empty),
                        false, View.VISIBLE
                    )
                    passwordLength < VALIDATION_PASSWORD_LENGTH -> showValidationMessage(
                        textViewValidatePassword,
                        getString(R.string.error_validation_password_text_length),
                        false,
                        View.VISIBLE
                    )
                    else -> showValidationMessage(textViewValidatePassword)
                }
            }
        }
    }

    private fun validatePasswordMatch() {
        binding.apply {
            textInputEditTextConfirmPassword.addTextChangedListener {
                when {
                    textInputEditTextConfirmPassword.length() == 0 -> showValidationMessage(
                        textViewValidateConfirm,
                        getString(R.string.confirm_password_cannot_be_empty),
                        false,
                        View.VISIBLE
                    )
                    textInputEditTextPassword.text.toString() == textInputEditTextConfirmPassword.text.toString() -> showValidationMessage(
                        textViewValidateConfirm
                    )
                    else -> showValidationMessage(
                        textViewValidateConfirm,
                        getString(R.string.error_validation_confirm_password_mismatch),
                        false,
                        View.VISIBLE
                    )
                }
            }
        }
    }

    private fun registerButtonClickHandler() {
        binding.apply {
            buttonRegister.setOnClickListener {
                Log.d(
                    "REGISTER_FRAGMENT123",
                    "$validationUserName - $validationPassword - $validationConfirm  "
                )
                if (validationUserName && validationPassword && validationConfirm) {
                    binding.progressBarReload.visibility = View.VISIBLE
                    val auth = AuthService()
                    val username = editTextUsername.text.toString()
                    val password = textInputEditTextPassword.text.toString()
                    val newUser = UserRequest(username, password)
                    auth.register(newUser, onFailure = {
                        requireActivity().runOnUiThread {
                            binding.progressBarReload.visibility = View.GONE
                            Log.d("REGISTER_FRAGMENT123", "$it ")
                            showValidationMessage(
                                editTextUsername,
                                getString(R.string.no_internet_connection)
                            )
                        }
                    },
                        onSuccess = {
                            requireActivity().runOnUiThread {
                                binding.progressBarReload.visibility = View.GONE
                                val result = Gson().fromJson(it,RegisterResponse::class.java)
                                if (!result.isSuccess){
                                    showValidationMessage(textViewValidateUserName,result.message.toString(),false,View.VISIBLE)
                                }else{
                                    changeFragment(HomeFragment())
                                }
                            }
                        })
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showValidationMessage(
        textView: TextView, message: String = "",
        isValid: Boolean = true,
        visibilityView: Int = View.GONE
    ) {
        textView.apply {
            text = message
            visibility = visibilityView
        }
        validationUserName = isValid
        validationPassword = isValid
        validationConfirm = isValid
    }

    companion object {
        const val VALIDATION_PASSWORD_LENGTH = 8
        const val VALIDATION_USERNAME_LENGTH = 3
    }
}