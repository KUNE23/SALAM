package com.example.laptoploan.core.ui.navigation

object Routes {
    const val Splash = "splash"
    const val StudentLogin = "student-login"
    const val StudentRegister = "student-register"
    const val Login = "login/{role}"
    const val Register = "register/{role}"
    const val CompleteProfile = "complete-profile"
    const val StudentHome = "student-home"
    const val LaptopList = "laptop-list"
    const val Terms = "terms/{code}"
    const val Verification = "verification/{code}"
    const val LoanSuccess = "loan-success"
    const val Notification = "notification"
    const val Profile = "profile"
    const val AdminHome = "admin-home"
    const val SarprasHome = "sarpras-home"

    fun login(role: String) = "login/$role"
    fun register(role: String) = "register/$role"
    fun terms(code: String) = "terms/$code"
    fun verification(code: String) = "verification/$code"
}
