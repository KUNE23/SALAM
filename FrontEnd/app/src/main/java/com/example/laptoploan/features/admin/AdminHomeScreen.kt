package com.example.laptoploan.features.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.AppTextField
import com.example.laptoploan.core.ui.components.LaptopListItem
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.theme.LightGrayInput
import com.example.laptoploan.core.ui.theme.PrimaryBlue
import com.example.laptoploan.features.auth.viewmodel.AppUiState
import com.example.laptoploan.features.auth.viewmodel.AppViewModel

@Composable
fun AdminHomeScreen(
    state: AppUiState,
    viewModel: AppViewModel,
    onLogout: () -> Unit
) {
    DashboardScaffold(
        title = "Admin Dashboard",
        onLogout = onLogout
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MiniStat("Total", state.laptops.size.toString(), Modifier.weight(1f))
            MiniStat("Tersedia", state.availableCount.toString(), Modifier.weight(1f))
            MiniStat("Dipinjam", state.borrowedCount.toString(), Modifier.weight(1f))
            MiniStat("Siswa", state.studentCount.toString(), Modifier.weight(1f))
        }

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuChip("Data Laptop")
            MenuChip("Data Peminjaman")
        }

        Spacer(Modifier.height(18.dp))

        LaptopForm(onAdd = viewModel::addLaptop)

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Data Laptop",
            color = PrimaryBlue,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(10.dp))

        state.laptops.forEach { laptop ->
            AdminLaptopRow(
                laptop = laptop,
                onDelete = { viewModel.deleteLaptop(laptop) }
            )
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Peminjaman Aktif",
            color = PrimaryBlue,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(10.dp))

        state.activeBorrowings.forEach { borrowing ->
            BorrowingRow(
                text = "User #${borrowing.userId} meminjam Laptop #${borrowing.laptopId}",
                onReturn = { viewModel.returnBorrowing(borrowing) }
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun DashboardScaffold(
    title: String,
    onLogout: () -> Unit,
    content: @Composable () -> Unit
) {
    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 24.dp, top = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OrangeCircleButton(
                    text = "O",
                    onClick = onLogout
                )

                Spacer(Modifier.weight(1f))

                SchoolHeader(compact = true)
            }

            Spacer(Modifier.height(82.dp))

            RoundedWhiteContainer(
                modifier = Modifier.weight(1f),
                topRadius = 34
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = title,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    content()

                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun MiniStat(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(76.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                color = PrimaryBlue,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )

            Text(
                text = label,
                color = PrimaryBlue,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun MenuChip(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(PrimaryBlue, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 9.dp)
    )
}

@Composable
fun LaptopForm(
    onAdd: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(
                color = LightGrayInput.copy(alpha = 0.45f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp)
    ) {
        AppTextField(
            name,
            { name = it },
            "Nama Laptop"
        )

        Spacer(Modifier.height(8.dp))

        AppTextField(
            code,
            { code = it },
            "Kode Laptop"
        )

        Spacer(Modifier.height(10.dp))

        PrimaryButton(
            text = "Tambah Laptop",
            onClick = {
                if (name.isNotBlank() && code.isNotBlank()) {
                    onAdd(name, code)
                    name = ""
                    code = ""
                }
            }
        )
    }
}

@Composable
fun AdminLaptopRow(
    laptop: Laptop,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LaptopListItem(
            laptop = laptop,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Edit",
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = "Hapus",
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onDelete)
        )
    }
}

@Composable
fun BorrowingRow(
    text: String,
    onReturn: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = "Kembalikan",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(PrimaryBlue, RoundedCornerShape(20.dp))
                .clickable(onClick = onReturn)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}