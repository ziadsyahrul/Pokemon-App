package com.ziad.pokeappcompose.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(purple)
                        .padding(start = 28.dp, end = 28.dp, top = 52.dp)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .clickable { onBack() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Detail",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.65f)
                            )
                            Text(
                                text = pokemon?.forms?.firstOrNull()?.name
                                    ?.replaceFirstChar { it.uppercase() } ?: "",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }

                // image-overlap
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            .size(120.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = 20.dp)
                            .clip(CircleShape)
                            .background(purple)
                            .border(4.dp, MaterialTheme.colorScheme.background, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = pokemon?.sprites?.front_default,
                            contentDescription = pokemon?.forms?.firstOrNull()?.name,
                            modifier = Modifier.size(90.dp)
                        )
                    }
                }

                // ── Content ──
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Abilities label
                    Text(
                        text = "ABILITIES",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        letterSpacing = 1.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )

                    // Abilities list
                    pokemon?.abilities?.forEach { abilitySlot ->
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
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = purple,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = abilitySlot.ability?.name
                                    ?.replaceFirstChar { it.uppercase() } ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        state = DetailUIState.Success(
            data = PokemonDetailResponse(
                forms = listOf(FormsItem(name = "bulbasaur")),
                sprites = SpritesItem(front_default = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"),
                abilities = listOf(
                    AbilitiesData(ability = AbilityItem(name = "Overgrow")),
                    AbilitiesData(ability = AbilityItem(name = "Chlorophyll"))
                )
            )
        ),
        onBack = {}
    )
}