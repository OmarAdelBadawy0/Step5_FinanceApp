import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.step5app.R
import com.example.step5app.presentation.common.ContactRow
import com.example.step5app.presentation.common.LanguageToggleRow
import com.example.step5app.presentation.common.SectionTitle
import com.example.step5app.presentation.common.ToggleRow
import com.example.step5app.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

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
                    .padding(end = 16.dp)
                    .clickable {onBackClick()},
                tint = MaterialTheme.colorScheme.onSurface)

            Text(stringResource(R.string.settings), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Theme
        SectionTitle(stringResource(R.string.theme))

        ToggleRow(
            text = stringResource(R.string.dark_mode),
            icon = R.drawable.moon,
            selected = uiState.themePreference == "Dark",
            onClick = {
                viewModel.updateTheme("Dark")
                viewModel.setTheme("Dark")
                viewModel.restartApp(context)
            }
        )
        ToggleRow(
            text = stringResource(R.string.light_mode),
            icon = R.drawable.sun,
            selected = uiState.themePreference == "Light",
            onClick = {
                viewModel.updateTheme("Light")
                viewModel.setTheme("Light")
                viewModel.restartApp(context)
            }
        )
        ToggleRow(
            text = stringResource(R.string.use_system_setting),
            icon = R.drawable.phone,
            selected = uiState.themePreference == "System",
            onClick = {
                viewModel.updateTheme("System")
                viewModel.setTheme("System")
                viewModel.restartApp(context)
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Language
        SectionTitle(stringResource(R.string.language))

        LanguageToggleRow(
            language = stringResource(R.string.arabic),
            countryCode = "SA",
            selected = uiState.language == "AR",
            onClick = {
                viewModel.updateLanguage("AR")
                viewModel.setLanguage(context, "ar")
                (context as? Activity)?.recreate()
            }
        )
        LanguageToggleRow(
            language = stringResource(R.string.english),
            countryCode = "US",
            selected = uiState.language == "EN",
            onClick = {
                viewModel.updateLanguage("EN")
                viewModel.setLanguage(context, "en")
                (context as? Activity)?.recreate()
            }
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






