package com.example.laptoploan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.laptoploan.core.ui.navigation.AppNavigation
import com.example.laptoploan.core.ui.theme.LaptopLoanTheme
import com.example.laptoploan.features.auth.viewmodel.AppViewModel
import com.example.laptoploan.features.auth.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as SALAMApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaptopLoanTheme {
                AppNavigation(viewModel)
            }
        }
    }
}
