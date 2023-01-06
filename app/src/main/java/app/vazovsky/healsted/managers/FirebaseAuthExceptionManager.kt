package app.vazovsky.healsted.managers

import android.content.Context
import app.vazovsky.healsted.R
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseAuthExceptionManager @Inject constructor(@ApplicationContext val context: Context) {

    fun getErrorMessage(exception: FirebaseAuthException) = context.getString(
        when (exception) {
            is FirebaseAuthInvalidUserException -> R.string.firebase_auth_invalid_user_exception_message
            is FirebaseAuthEmailException -> R.string.firebase_auth_email_exception_message
            is FirebaseAuthActionCodeException -> R.string.firebase_auth_action_code_exception_message
            is FirebaseAuthMultiFactorException -> R.string.firebase_auth_multi_factor_exception_message
            is FirebaseAuthUserCollisionException -> R.string.firebase_auth_user_collision_exception_message
            is FirebaseAuthInvalidCredentialsException -> R.string.firebase_auth_invalid_credentials_exception_message
            else -> R.string.firebase_auth_exception_message
        }
    )
}