package com.ziad.pokeappcompose.presentation.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ziad.pokeappcompose.presentation.ui.navigation.GraphNavigation
import com.ziad.pokeappcompose.presentation.viewmodel.AuthViewModel


@Composable
fun ProfileRoute(
    rootNavController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.profileUiState.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state,
        onLogout = {
            viewModel.logout()

            rootNavController.navigate(GraphNavigation.Auth.route) {
                popUpTo(GraphNavigation.Home.route) { inclusive = true }
            }
        }
    )
}

@Composable
fun ProfileScreen(
    state: ProfileUIState,
    onLogout: () -> Unit
) {
    val purple = Color(0xFF3C3489)
    val initial = state.username?.take(1)?.uppercase() ?: "?"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(purple)
                .padding(start = 28.dp, end = 28.dp, top = 40.dp, bottom = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 80.dp, y = (-40).dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
            )
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 20.dp, y = 20.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f))
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Akun saya",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.65f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Profil Screen",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(purple)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(MaterialTheme.colorScheme.background)
            )
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(purple)
                    .border(4.dp, MaterialTheme.colorScheme.background, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initial,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = state.username ?: "-",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Member aktif",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        0.5.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        RoundedCornerShape(14.dp)
                    )
            ) {
                ProfileInfoRow(
                    icon = Icons.Default.Person,
                    label = "Username",
                    value = state.username ?: "-",
                    purple = purple,
                    showDivider = true
                )
                ProfileInfoRow(
                    icon = Icons.Default.DateRange,
                    label = "Bergabung sejak",
                    value = "April 2026",
                    purple = purple,
                    showDivider = true
                )
                ProfileInfoRow(
                    icon = Icons.Default.ShoppingCart,
                    label = "Sisa Credit",
                    value = "0",
                    purple = purple,
                    showDivider = false
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.error),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keluar dari Akun",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    purple: Color,
    showDivider: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(purple.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = purple,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            state = ProfileUIState(username = "Ziad Syahrul"),
            onLogout = {}
        )
    }
}