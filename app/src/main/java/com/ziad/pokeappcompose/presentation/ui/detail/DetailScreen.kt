package com.ziad.pokeappcompose.presentation.ui.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ziad.pokeappcompose.domain.model.pokemon.detail.AbilitiesData
import com.ziad.pokeappcompose.domain.model.pokemon.detail.AbilityItem
import com.ziad.pokeappcompose.domain.model.pokemon.detail.FormsItem
import com.ziad.pokeappcompose.domain.model.pokemon.detail.PokemonDetailResponse
import com.ziad.pokeappcompose.domain.model.pokemon.detail.SpritesItem
import com.ziad.pokeappcompose.domain.model.pokemon.detail.StatsData
import com.ziad.pokeappcompose.domain.model.pokemon.detail.StatItem
import com.ziad.pokeappcompose.domain.model.pokemon.detail.TypesData
import com.ziad.pokeappcompose.domain.model.pokemon.detail.TypeItem
import com.ziad.pokeappcompose.presentation.viewmodel.HomeViewModel

@Composable
fun DetailRoute(
    url: String,
    viewModel: HomeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(url) {
        viewModel.getDetailPokemon(url)
    }

    val state by viewModel.detailUiState.collectAsStateWithLifecycle()
    DetailScreen(state = state, onBack = onBack)
}

@Composable
fun DetailScreen(
    state: DetailUIState,
    onBack: () -> Unit
) {
    val purple = Color(0xFF3C3489)

    when (state) {
        is DetailUIState.Idle -> {
            Box(modifier = Modifier.fillMaxSize())
        }

        is DetailUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = purple
                )
            }
        }

        is DetailUIState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        is DetailUIState.Success -> {
            val pokemon = state.data


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                Box(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .background(purple)
                                .padding(start = 28.dp, end = 28.dp, top = 44.dp)
                        ) {
                            // Decorative circles tetap sama
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

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White.copy(alpha = 0.15f))
                                        .clickable { onBack() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        "Detail",
                                        color = Color.White.copy(alpha = 0.65f),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(pokemon?.forms?.firstOrNull()?.name?.replaceFirstChar { it.uppercase() }
                                        ?: "",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleLarge)
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        pokemon?.types?.forEach { typeData -> TypeBadge(name = typeData.type.name) }
                                    }
                                }
                            }
                        }

                        // Area Lengkungan Putih
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(purple)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                                    .background(MaterialTheme.colorScheme.background)
                            )
                        }
                    }

                    // Gambar Pokemon (Overlap)
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = 45.dp)
                            .clip(CircleShape)
                            .background(purple)
                            .border(4.dp, MaterialTheme.colorScheme.background, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = pokemon?.sprites?.front_default,
                            contentDescription = null,
                            modifier = Modifier.size(90.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        label = "Height",
                        value = "${"%.1f".format((pokemon?.height ?: 0) / 10.0)} m",
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        label = "Weight",
                        value = "${"%.1f".format((pokemon?.weight ?: 0) / 10.0)} kg",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── 3. Bagian SCROLLABLE: Mulai dari Base Stats ke bawah ──
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Mengambil sisa layar yang tersedia
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp)
                ) {
                    // Base Stats Content
                    if (!pokemon?.stats.isNullOrEmpty()) {
                        Text(
                            text = "BASE STATS",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            letterSpacing = 1.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                        pokemon?.stats?.forEach { statData ->
                            StatRow(
                                name = statData.stat.name,
                                value = statData.base_stat,
                                purple = purple
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Abilities Content
                    Text(
                        text = "ABILITIES",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        letterSpacing = 1.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                    pokemon?.abilities?.forEach { abilitySlot ->
                        AbilityItemRow(
                            abilityName = abilitySlot.ability?.name ?: "",
                            purple = purple
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AbilityItemRow(abilityName: String, purple: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                0.5.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(purple.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Star, null, tint = purple, modifier = Modifier.size(14.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = abilityName.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TypeBadge(name: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.18f))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = name.replaceFirstChar { it.uppercase() },
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun InfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                0.5.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                RoundedCornerShape(14.dp)
            )
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StatRow(
    name: String,
    value: Int,
    purple: Color
) {
    val animatedProgress by animateFloatAsState(
        targetValue = value / 255f,
        animationSpec = tween(durationMillis = 800),
        label = "stat_progress"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name
                .replace("-", " ")
                .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(30.dp),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = purple,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        state = DetailUIState.Success(
            data = PokemonDetailResponse(
                height = 7,
                weight = 69,
                forms = listOf(FormsItem(name = "bulbasaur")),
                sprites = SpritesItem(front_default = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                abilities = listOf(
                    AbilitiesData(ability = AbilityItem(name = "Overgrow")),
                    AbilitiesData(ability = AbilityItem(name = "Chlorophyll"))
                ),
                types = listOf(
                    TypesData(type = TypeItem(name = "grass")),
                    TypesData(type = TypeItem(name = "poison"))
                ),
                stats = listOf(
                    StatsData(base_stat = 45, stat = StatItem(name = "hp")),
                    StatsData(base_stat = 49, stat = StatItem(name = "attack")),
                    StatsData(base_stat = 49, stat = StatItem(name = "defense")),
                    StatsData(base_stat = 65, stat = StatItem(name = "special-attack")),
                    StatsData(base_stat = 65, stat = StatItem(name = "special-defense")),
                    StatsData(base_stat = 45, stat = StatItem(name = "speed")),
                )
            )
        ),
        onBack = {}
    )
}