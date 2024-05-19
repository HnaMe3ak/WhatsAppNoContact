package dev.theolm.wwc.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.theolm.wwc.ui.core.codes.CountryCodes
import dev.theolm.wwc.ui.core.storage.dataStore
import dev.theolm.wwc.ui.core.storage.saveDefaultCode
import dev.theolm.wwc.ui.core.whats.checkIfWpIsInstalled
import dev.theolm.wwc.ui.core.whats.startWhatsAppChat
import dev.theolm.wwc.ui.main.dialog.ErrorDialog
import dev.theolm.wwc.ui.main.dialog.MainDialog
import dev.theolm.wwc.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(
                isDynamicColorEnabled = true
            ) {
                if (checkIfWpIsInstalled()) {
                    MainDialog(
                        onDismiss = {
                            finish()
                        },
                        onStart = {
                            onStartChatClicked(it)
                        }
                    )
                } else {
                    ErrorDialog(onDismiss = { finish() })
                }
            }
        }
    }

    private fun onStartChatClicked(input: String) = launch {
        CountryCodes.extractCountryCode(input)?.let {
            dataStore.saveDefaultCode(it)
        }

        startWhatsAppChat(input)
    }
}
