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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.step5app.R
import com.example.step5app.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 32.dp)) {
            Icon(painterResource(
                R.drawable.arrow_left),
                contentDescription = stringResource(R.string.back),
                Modifier
                    .scale(1.6f)
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.onSurface)

            Text(stringResource(R.string.settings), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Theme
        SectionTitle(stringResource(R.string.theme))

        ThemeToggleRow(
            text = stringResource(R.string.dark_mode),
            icon = R.drawable.moon,
            selected = uiState.themePreference == "Dark",
            onClick = { viewModel.updateTheme("Dark") }
        )
        ThemeToggleRow(
            text = stringResource(R.string.light_mode),
            icon = R.drawable.sun,
            selected = uiState.themePreference == "Light",
            onClick = { viewModel.updateTheme("Light") }
        )
        ThemeToggleRow(
            text = stringResource(R.string.use_system_setting),
            icon = R.drawable.phone,
            selected = uiState.themePreference == "System",
            onClick = { viewModel.updateTheme("System") }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Language
        SectionTitle(stringResource(R.string.language))

        LanguageToggleRow(
            language = stringResource(R.string.arabic),
            countryCode = "SA",
            selected = uiState.language == "AR",
            onClick = { viewModel.updateLanguage("AR") }
        )
        LanguageToggleRow(
            language = stringResource(R.string.english),
            countryCode = "US",
            selected = uiState.language == "EN",
            onClick = { viewModel.updateLanguage("EN") }
        )
        Spacer(modifier = Modifier.height(28.dp))

        // Contact
        SectionTitle(stringResource(R.string.contact_us))
        ContactRow(R.drawable.phone, stringResource(R.string.contact_us_number))
        ContactRow(R.drawable.mail, stringResource(R.string.contact_us_email))

        Spacer(modifier = Modifier.height(24.dp))

        // About
        SectionTitle(stringResource(R.string.about_us))
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(0.5f)
                .size(60.dp)
                .background(Color.LightGray)
        )

        Text(
            text = stringResource(R.string.about_us_description),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Social Icons
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(horizontalArrangement = Arrangement.spacedBy(26.dp)) {
                Icon(
                    painterResource(R.drawable.whatsapp),
                    contentDescription = stringResource(R.string.whatsapp),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.scale(1.5f)
                )
                Icon(
                    painterResource(R.drawable.telegram),
                    contentDescription = stringResource(R.string.telegram),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.scale(1.5f)
                )
                Icon(
                    painterResource(R.drawable.instagram),
                    contentDescription = stringResource(R.string.instagram),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.scale(1.5f)
                )
                Icon(
                    painterResource(R.drawable.facebook),
                    contentDescription = stringResource(R.string.facebook),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.scale(1.5f)
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Version
        Text(stringResource(R.string.app_version), color = MaterialTheme.colorScheme.surfaceVariant, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(vertical = 4.dp),
        fontSize = 18.sp
    )
    HorizontalDivider(
        modifier = Modifier.width(title.length.dp * 13),
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun ThemeToggleRow(text: String, icon: Int, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.scale(1.2f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier
            .weight(1f)
            .padding(start = 5.dp), fontSize = 14.sp)
        Switch(
            checked = selected,
            onCheckedChange = { onClick() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.secondaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                checkedTrackColor = MaterialTheme.colorScheme.onTertiaryContainer, // Bright green
                uncheckedTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
                checkedBorderColor = MaterialTheme.colorScheme.onTertiaryContainer, // To give a visible border
                uncheckedBorderColor = Color.Transparent,
            ),
            modifier = Modifier.scale(0.8f) // Optional: Slightly shrink switch to match the compact look
        )
    }
}

@Composable
fun LanguageToggleRow(language: String, countryCode: String, selected: Boolean, onClick: () -> Unit) {
    val flag = when (countryCode) {
        "US" -> "\uD83C\uDDFA\uD83C\uDDF8" // 🇺🇸
        "SA" -> "\uD83C\uDDF8\uD83C\uDDE6" // 🇸🇦
        else -> ""
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp)
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.onTertiaryContainer else Color.Transparent
            )
            .background(if (selected) MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(8.dp)

    ) {
        Text(
            text = "$flag $language",
            color = if (selected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun ContactRow(icon: Int, text: String) {
    Row(
        verticalAlignment =
            Alignment.CenterVertically,
        modifier = Modifier.padding(top = 22.dp, )) {
        Icon(
            painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .scale(1.2f)
                .padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle.Default.copy(textDirection = TextDirection.Ltr),
                fontSize = 16.sp
            )
        }
    }
}
