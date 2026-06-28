package com.example.laptoploan.core.ui.navigation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.laptoploan.core.data.model.UserRole
import com.example.laptoploan.features.admin.AdminHomeScreen
import com.example.laptoploan.features.auth.presentation.CompleteProfileScreen
import com.example.laptoploan.features.auth.presentation.LoginScreen
import com.example.laptoploan.features.auth.presentation.RegisterScreen
import com.example.laptoploan.features.auth.viewmodel.AppViewModel
import com.example.laptoploan.features.laptop.LaptopListScreen
import com.example.laptoploan.features.notification.NotificationScreen
import com.example.laptoploan.features.profile.ProfileScreen
import com.example.laptoploan.features.sarpras.SarprasHomeScreen
import com.example.laptoploan.features.student.LoanSuccessScreen
import com.example.laptoploan.features.student.StudentHomeScreen
import com.example.laptoploan.features.student.SuccessScreen
import com.example.laptoploan.features.student.TermsScreen
import com.example.laptoploan.features.student.VerificationScreen

@Composable
fun AppNavigation(viewModel: AppViewModel, navController: NavHostController = rememberNavController()) {
    val state by viewModel.uiState.collectAsState()

    state.error?.let {
        AlertDialog(
            onDismissRequest = viewModel::clearError,
            confirmButton = { TextButton(onClick = viewModel::clearError) { Text("OK") } },
            text = { Text(it) }
        )
    }

    NavHost(navController = navController, startDestination = Routes.StudentLogin) {
        composable(Routes.Splash) {
            SuccessScreen(
                title = "Pengumpulan berhasil !",
                subtitle = "Terimakasih, semoga bermanfaat :)",
                onNext = {
                    navController.navigateToLoginRoot()
                }
            )
        }
        composable(Routes.StudentLogin) {
            val role = UserRole.SISWA
            LoginScreen(
                role = role,
                onBack = { },
                onCreateAccount = { navController.navigate(Routes.StudentRegister) },
                onLogin = { email, password ->
                    viewModel.login(email, password, role) {
                        navController.navigateHome(UserRole.SISWA)
                    }
                }
            )
        }
        composable(Routes.StudentRegister) {
            val role = UserRole.SISWA
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegister = { name, email, password ->
                    viewModel.register(name, email, password, role) {
                        navController.navigate(Routes.CompleteProfile)
                    }
                }
            )
        }
        composable(Routes.CompleteProfile) {
            CompleteProfileScreen(
                user = state.currentUser,
                onBack = { navController.popBackStack() },
                onSave = { nisn, kelas, jurusan ->
                    viewModel.completeProfile(nisn, kelas, jurusan) {
                        navController.navigate(Routes.StudentHome) { popUpTo(Routes.StudentLogin) }
                    }
                }
            )
        }
        composable(Routes.StudentHome) {
            StudentHomeScreen(
                state = state,
                onLaptop = { navController.navigate(Routes.LaptopList) },
                onNotification = { navController.navigate(Routes.Notification) },
                onProfile = { navController.navigate(Routes.Profile) },
                onLogout = { viewModel.logout { navController.navigateToLoginRoot() } }
            )
        }
        composable(Routes.LaptopList) {
            LaptopListScreen(
                state = state,
                onBack = { navController.popBackStack() },
                onBorrow = { code ->
                    navController.navigate(Routes.terms(code.trim().uppercase()))
                }
            )
        }
        composable(
            Routes.Terms,
            arguments = listOf(navArgument("code") { type = NavType.StringType })
        ) { backStackEntry ->
            val code = backStackEntry.arguments?.getString("code").orEmpty()
            TermsScreen(
                onBack = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onAgree = { navController.navigate(Routes.verification(code)) }
            )
        }
        composable(
            Routes.Verification,
            arguments = listOf(navArgument("code") { type = NavType.StringType })
        ) { backStackEntry ->
            val code = backStackEntry.arguments?.getString("code").orEmpty()
            VerificationScreen(
                onBack = { navController.popBackStack() },
                onCancel = { navController.popBackStack(Routes.LaptopList, false) },
                onOk = {
                    viewModel.borrowLaptop(code) {
                        navController.navigate(Routes.LoanSuccess) {
                            popUpTo(Routes.LaptopList) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Routes.LoanSuccess) {
            LoanSuccessScreen(
                onNext = {
                    navController.navigate(Routes.StudentHome) {
                        popUpTo(Routes.StudentHome) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Notification) {
            NotificationScreen(
                state = state,
                onBack = { navController.popBackStack() },
                onFinish = {
                    viewModel.returnActiveBorrowing {
                        navController.navigate(Routes.Splash) { popUpTo(Routes.StudentHome) { inclusive = false } }
                    }
                }
            )
        }
        composable(Routes.Profile) {
            ProfileScreen(
                user = state.currentUser,
                onBack = { navController.popBackStack() },
                onLogout = { viewModel.logout { navController.navigateToLoginRoot() } }
            )
        }
        composable(Routes.AdminHome) {
            AdminHomeScreen(state, viewModel, onLogout = { viewModel.logout { navController.navigateToLoginRoot() } })
        }
        composable(Routes.SarprasHome) {
            SarprasHomeScreen(state, viewModel, onLogout = { viewModel.logout { navController.navigateToLoginRoot() } })
        }
    }
}

private fun NavHostController.navigateHome(role: UserRole) {
    val route = when (role) {
        UserRole.SISWA -> Routes.StudentHome
        UserRole.ADMIN -> Routes.AdminHome
        UserRole.SARPRAS -> Routes.SarprasHome
    }
    navigate(route) { popUpTo(Routes.StudentLogin) }
}

private fun NavHostController.navigateToLoginRoot() {
    navigate(Routes.StudentLogin) {
        popUpTo(graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}
