package com.example.laptoploan

import android.app.Application
import com.example.laptoploan.core.data.repository.LaptopLoanRepository
import com.example.laptoploan.core.network.ApiClient
import com.example.laptoploan.core.session.SessionManager

class SALAMApplication : Application() {
    val sessionManager by lazy { SessionManager(this) }
    val apiService by lazy { ApiClient.create(sessionManager) }
    val repository by lazy { LaptopLoanRepository(apiService, sessionManager) }
}
