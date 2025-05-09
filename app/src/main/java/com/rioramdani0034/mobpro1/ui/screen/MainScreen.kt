package com.rioramdani0034.mobpro1.ui.screen

import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.rioramdani0034.mobpro1.R
import com.rioramdani0034.mobpro1.model.Notes
import com.rioramdani0034.mobpro1.navigation.Screen
import com.rioramdani0034.mobpro1.util.SettingsDataStore
import com.rioramdani0034.mobpro1.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        Icon(
                            imageVector = if (showList) Icons.Filled.GridView else Icons.AutoMirrored.Filled.ViewList,
                            contentDescription = stringResource(
                                if (showList) R.string.tampilan_grid
                                else R.string.tampilan_list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_catatan),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(showList, modifier = Modifier.padding(innerPadding), navController)
    }
}

@Composable
fun ScreenContent(
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        EmptyNotesView(modifier)
    } else {
        AnimatedVisibility(
            visible = showList,
            enter = fadeIn(spring(stiffness = Spring.StiffnessLow)),
            exit = fadeOut(spring(stiffness = Spring.StiffnessLow))
        ) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 84.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(data) { note ->
                    ListItem(note = note) {
                        navController.navigate(Screen.FormUbah.withId(note.id))
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = !showList,
            enter = fadeIn(spring(stiffness = Spring.StiffnessLow)),
            exit = fadeOut(spring(stiffness = Spring.StiffnessLow))
        ) {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 84.dp)
            ) {
                items(data) { note ->
                    GridItem(note = note) {
                        navController.navigate(Screen.FormUbah.withId(note.id))
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyNotesView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                .padding(16.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.empty_list),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.empty_note),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ListItem(note: Notes, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = getColorForCategory(note.categories)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CategoryChip(note.categories)
            }

            note.imageUri?.let { imageUriStr ->
                DisplayNoteImage(imageUriStr, 180.dp)
            }

            HtmlTextView(
                html = note.content,
                maxLines = 3
            )

            Text(
                text = formatDate(note.createdDate),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun GridItem(note: Notes, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = getColorForCategory(note.categories)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = note.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            note.imageUri?.let { imageUriStr ->
                DisplayNoteImage(imageUriStr, 120.dp)
            }

            HtmlTextView(
                html = note.content,
                maxLines = 5
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryChip(note.categories)
                Text(
                    text = formatDate(note.createdDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}


@Composable
fun DisplayNoteImage(imageUriStr: String, height: androidx.compose.ui.unit.Dp) {
    val context = LocalContext.current
    val uri = remember(imageUriStr) { Uri.parse(imageUriStr) }

    // Cek apakah file ada jika menggunakan URI file
    val fileExists = remember(uri) {
        if (uri.scheme == "file") {
            val path = uri.path
            path != null && File(path).exists()
        } else true
    }

    if (fileExists) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(uri)
                .crossfade(true)
                .build(),
            contentDescription = "Note Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(8.dp)),
            onError = {
                // Log error jika gagal memuat gambar
                Log.e("NoteImage", "Failed to load image: $imageUriStr")
            }
        )
    } else {
        // Tampilkan placeholder jika file tidak ditemukan
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Image not found",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CategoryChip(category: String) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun HtmlTextView(html: String, modifier: Modifier = Modifier, maxLines: Int = Int.MAX_VALUE) {
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

    AndroidView(
        factory = { context ->
            TextView(context).apply {
                setTextAppearance(android.R.style.TextAppearance_DeviceDefault)
                setTextColor(textColor)
                linksClickable = true
                this.maxLines = maxLines
                ellipsize = android.text.TextUtils.TruncateAt.END
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    @Suppress("DEPRECATION")
                    text = Html.fromHtml(html)
                }
            }
        },
        modifier = modifier,
        update = { textView ->
            textView.maxLines = maxLines
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                textView.text = Html.fromHtml(html)
            }
        }
    )
}

// Helper functions
@Composable
fun getColorForCategory(category: String): Color {
    return when {
        category.contains("Personal", ignoreCase = true) ->
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        category.contains("Work", ignoreCase = true) ->
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
        category.contains("Study", ignoreCase = true) ->
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f)
        category.contains("Important", ignoreCase = true) ->
            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
        category.contains("Projects", ignoreCase = true) ->
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
        category.contains("Diary", ignoreCase = true) ->
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f)
        else -> MaterialTheme.colorScheme.surface
    }
}

fun formatDate(dateString: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
        val date = parser.parse(dateString)
        date?.let { formatter.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}