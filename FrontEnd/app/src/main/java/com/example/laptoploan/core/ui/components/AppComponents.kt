package com.example.laptoploan.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.ui.theme.DarkBlueText
import com.example.laptoploan.core.ui.theme.LightGrayInput
import com.example.laptoploan.core.ui.theme.OrangeAccent
import com.example.laptoploan.core.ui.theme.PrimaryBlue
import com.example.laptoploan.core.ui.theme.SoftPink
import com.example.laptoploan.core.ui.theme.WhiteCard

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val pale = Color.White.copy(alpha = 0.11f)
            repeat(11) { index ->
                val x = (index % 3) * size.width / 2.4f + 24
                val y = index * size.height / 9f - 80
                drawCircle(pale, radius = 72f, center = Offset(x, y), style = Stroke(7f))
                drawArc(pale, 20f, 260f, false, topLeft = Offset(x - 90, y - 90), size = androidx.compose.ui.geometry.Size(180f, 180f), style = Stroke(5f))
                drawLine(pale, Offset(x - 80, y + 20), Offset(x + 90, y - 45), strokeWidth = 4f, cap = StrokeCap.Round)
            }
        }
        content()
    }
}

@Composable
fun SchoolHeader(modifier: Modifier = Modifier, compact: Boolean = false) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 28.dp else 36.dp)
                .clip(CircleShape)
                .background(Color(0xFF174EA6)),
            contentAlignment = Alignment.Center
        ) {
            Text("SM", color = Color.Yellow, fontSize = if (compact) 9.sp else 11.sp, fontWeight = FontWeight.Black)
        }
        Spacer(Modifier.width(6.dp))
        Column {
            Text("Support by", color = Color.White, fontSize = if (compact) 10.sp else 12.sp, fontWeight = FontWeight.ExtraBold)
            Text("SMK ISLAM MADANI", color = Color.White, fontSize = if (compact) 10.sp else 12.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun RoundedWhiteContainer(
    modifier: Modifier = Modifier,
    topRadius: Int = 42,
    contentPadding: PaddingValues = PaddingValues(horizontal = 28.dp, vertical = 28.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = topRadius.dp, topEnd = topRadius.dp))
            .background(WhiteCard)
            .padding(contentPadding)
    ) {
        content()
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue, contentColor = Color.White),
        shape = RoundedCornerShape(30.dp)
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OrangeCircleButton(text: String = "<", onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(OrangeAccent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
    }
}

@Composable
fun UserAvatar(modifier: Modifier = Modifier, large: Boolean = false) {
    Canvas(
        modifier
            .size(if (large) 112.dp else 64.dp)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(OrangeAccent)
            .border(5.dp, Color.White.copy(alpha = 0.85f), CircleShape)
    ) {
        drawCircle(Color.White.copy(alpha = 0.9f), radius = size.minDimension * 0.18f, center = Offset(size.width * 0.55f, size.height * 0.36f))
        drawCircle(Color.White.copy(alpha = 0.9f), radius = size.minDimension * 0.28f, center = Offset(size.width * 0.5f, size.height * 0.68f))
        drawCircle(Color.White.copy(alpha = 0.45f), radius = size.minDimension * 0.05f, center = Offset(size.width * 0.28f, size.height * 0.28f))
    }
}

@Composable
fun RoleCard(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar()
        Spacer(Modifier.height(18.dp))
        Text(label, color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    password: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null,
    readOnly: Boolean = false,
    gray: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier) {
        Text(label, color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Spacer(Modifier.height(7.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            visualTransformation = if (password && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (password && onTogglePassword != null) {
                { IconButton(onClick = onTogglePassword) { Text(if (passwordVisible) "Hide" else "See", fontSize = 11.sp, color = Color.Black) } }
            } else null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = if (gray) LightGrayInput else Color.White,
                unfocusedContainerColor = if (gray) LightGrayInput else Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = PrimaryBlue,
                unfocusedIndicatorColor = PrimaryBlue,
                focusedTextColor = DarkBlueText,
                unfocusedTextColor = DarkBlueText
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

@Composable
fun StatsCard(label: String, value: String, modifier: Modifier = Modifier, alert: Boolean = false, onClick: (() -> Unit)? = null) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(label, color = Color.White, fontSize = 12.sp)
        Spacer(Modifier.height(6.dp))
        Card(
            modifier = Modifier
                .size(width = 80.dp, height = 76.dp)
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
            colors = CardDefaults.cardColors(containerColor = if (alert) SoftPink else Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(9.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(value, color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 32.sp)
                if (alert) Box(Modifier.align(Alignment.TopEnd).padding(14.dp).size(10.dp).background(Color.Red, CircleShape))
            }
        }
    }
}

@Composable
fun LaptopListItem(laptop: Laptop, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        LaptopIcon()
        Spacer(Modifier.width(10.dp))
        Column {
            Text(laptop.name.uppercase(), color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 13.sp)
            Text("KODE: ${laptop.code}", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 8.sp)
        }
    }
}

@Composable
fun LaptopIcon(modifier: Modifier = Modifier) {
    Canvas(modifier.size(width = 34.dp, height = 26.dp)) {
        drawRect(Color(0xFFB0BEC5), topLeft = Offset(2f, 0f), size = androidx.compose.ui.geometry.Size(size.width - 4f, size.height * 0.72f))
        drawRect(Color.White, topLeft = Offset(5f, 3f), size = androidx.compose.ui.geometry.Size(size.width - 10f, size.height * 0.5f))
        drawLine(Color(0xFFFFC400), Offset(6f, 17f), Offset(26f, 4f), 7f)
        drawLine(Color(0xFFE8505B), Offset(15f, 18f), Offset(30f, 6f), 7f)
        drawRect(Color(0xFF8D8D8D), topLeft = Offset(0f, size.height * 0.72f), size = androidx.compose.ui.geometry.Size(size.width, 5f))
    }
}

@Composable
fun NotificationCard(message: String, done: Boolean, onDoneChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(7.dp))
            .clip(RoundedCornerShape(7.dp))
            .background(Color(0xFFF7F7F7))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(message, color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text("11:00", color = Color(0xFF8A83A4), fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Checkbox(checked = done, onCheckedChange = onDoneChange)
    }
}

@Composable
fun StudentIllustration(modifier: Modifier = Modifier, happy: Boolean = false) {
    Canvas(modifier.size(width = 260.dp, height = 260.dp)) {
        fun person(cx: Float, hijab: Boolean) {
            val skin = Color(0xFFFFD2C4)
            drawCircle(skin, 32f, Offset(cx, 72f))
            if (hijab) drawCircle(Color(0xFFEAF6FF), 43f, Offset(cx, 74f), style = Stroke(20f))
            else drawArc(Color(0xFF1F1F1F), 190f, 160f, true, topLeft = Offset(cx - 35, 36f), size = androidx.compose.ui.geometry.Size(70f, 54f))
            drawCircle(Color.Black, 3f, Offset(cx - 10, 70f))
            drawCircle(Color.Black, 3f, Offset(cx + 10, 70f))
            drawArc(Color(0xFFE8505B), if (happy) 0f else 180f, if (happy) 180f else -180f, false, topLeft = Offset(cx - 9, 80f), size = androidx.compose.ui.geometry.Size(18f, 12f), style = Stroke(3f))
            drawRoundRect(Color(0xFFDFF4FF), topLeft = Offset(cx - 34, 108f), size = androidx.compose.ui.geometry.Size(68f, 118f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f))
            drawRect(Color(0xFFFFD21F), topLeft = Offset(cx + 12, 134f), size = androidx.compose.ui.geometry.Size(16f, 24f))
            drawLine(skin, Offset(cx - 34, 126f), Offset(cx - 78, if (happy) 92f else 116f), 14f, StrokeCap.Round)
            drawLine(skin, Offset(cx + 34, 126f), Offset(cx + 78, if (happy) 92f else 116f), 14f, StrokeCap.Round)
        }
        person(88f, false)
        person(174f, true)
    }
}

@Composable
fun CheckBadge(modifier: Modifier = Modifier) {
    Canvas(modifier.size(120.dp)) {
        drawCircle(PrimaryBlue, radius = size.minDimension / 2)
        val path = Path().apply {
            moveTo(size.width * 0.28f, size.height * 0.54f)
            lineTo(size.width * 0.45f, size.height * 0.70f)
            lineTo(size.width * 0.75f, size.height * 0.34f)
        }
        drawPath(path, Color.White, style = Stroke(width = 10f, cap = StrokeCap.Round))
    }
}

@Composable
fun EmptyState(text: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, color = PrimaryBlue.copy(alpha = 0.25f), fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, textAlign = TextAlign.Center)
    }
}
