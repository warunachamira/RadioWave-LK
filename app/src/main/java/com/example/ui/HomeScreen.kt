package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.Station
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel, onNavigateToPlayer: () -> Unit) {
    var selectedCategory by remember { mutableStateOf("All") }
    val stations by viewModel.stations.collectAsState()
    val playingStation by viewModel.currentStation.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    
    val categories = listOf("All", "Favorites") + stations.map { it.category }.distinct()
    
    val filteredStations = when (selectedCategory) {
        "All" -> stations
        "Favorites" -> stations.filter { it.isFavorite }
        else -> stations.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            "LIVE STREAM", 
                            style = MaterialTheme.typography.labelSmall, 
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "LankaWave", 
                            style = MaterialTheme.typography.titleLarge, 
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            ) 
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { 
                            Text(
                                category.uppercase(), 
                                style = MaterialTheme.typography.labelLarge,
                                color = if (selectedCategory == category) Color.Black else Color.Gray
                            ) 
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(0xFF1C1C22),
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color.Transparent,
                            selectedBorderColor = Color.Transparent,
                            enabled = true, selected = selectedCategory == category
                        ),
                        shape = RoundedCornerShape(100)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredStations) { station ->
                    StationCard(
                        station = station,
                        onClick = { viewModel.playStation(station) },
                        onFavoriteClick = { viewModel.toggleFavorite(station) }
                    )
                }
            }

            playingStation?.let { station ->
                MiniPlayer(
                    station = station,
                    isPlaying = isPlaying,
                    onPlayPause = { viewModel.togglePlayPause() },
                    onClick = onNavigateToPlayer
                )
            }
        }
    }
}

@Composable
fun StationCard(station: Station, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(station.logoUrl)
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .crossfade(true)
                    .build(),
                contentDescription = station.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                        startY = 100f
                    )
                )
            )
            
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if(station.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if(station.isFavorite) Color.Red else Color.White
                )
            }

            Column(modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)) {
                Text(station.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
                Text(station.language, color = Color.Gray, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun MiniPlayer(station: Station, isPlaying: Boolean, onPlayPause: () -> Unit, onClick: () -> Unit) {
    Surface(
        color = Color(0xFF2D2D35),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 16.dp,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp).height(72.dp).clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(station.logoUrl)
                        .addHeader("User-Agent", "Mozilla/5.0")
                        .crossfade(true)
                        .build(),
                    contentDescription = null, 
                    modifier = Modifier.fillMaxSize(), 
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(station.name.uppercase(), style = MaterialTheme.typography.labelLarge, maxLines = 1, color = Color.White)
                Text(if (isPlaying) "Streaming Live..." else "Paused", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
            }
            Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(100)).background(Color.White).clickable { onPlayPause() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow, 
                    contentDescription = "Play/Pause",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
