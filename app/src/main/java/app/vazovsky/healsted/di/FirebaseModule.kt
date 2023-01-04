package app.vazovsky.healsted.di

import android.content.Context
import app.vazovsky.healsted.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideGso(@ApplicationContext context: Context) = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

//    @Provides
//    @Singleton
//    fun provideGoogleClient(
//        @ApplicationContext context: Context,
//        gso: GoogleSignInOptions,
//    ) = GoogleSignIn.getClient(context, gso)

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()
}