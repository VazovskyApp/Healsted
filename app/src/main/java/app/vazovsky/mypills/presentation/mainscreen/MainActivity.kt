package app.vazovsky.mypills.presentation.mainscreen

import android.os.Bundle
import app.vazovsky.mypills.R
import app.vazovsky.mypills.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onBindViewModel() = Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}