package com.github.jing332.frpandroid.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jing332.frpandroid.ui.widgets.AppDialog
import com.github.jing332.frpandroid.ui.widgets.Markdown
import com.github.jing332.frpandroid.util.ClipboardUtils

@Preview
@Composable
private fun PreviewAppUpdateDialog() {
    var show by remember { androidx.compose.runtime.mutableStateOf(true) }
    if (show)
        AppUpdateDialog(
            onDismissRequest = {
                show = false
            }, version = "1.0.0", content = "## 更新内容\n\n- 123", downloadUrl = "url"
        )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUpdateDialog(
    onDismissRequest: () -> Unit,
    version: String,
    content: String,
    downloadUrl: String
) {
    val context = LocalContext.current
    fun openDownloadUrl(url: String) {
        ClipboardUtils.copyText("FrpAndroid下载链接", url)
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    AppDialog(onDismissRequest = onDismissRequest,
        title = {
            Text(
                "新版本",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        content = {
            Column {
                Text(
                    text = version,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                val scrollState = rememberScrollState()
                Column(
                    Modifier
                        .padding(8.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center
                ) {
                    Markdown(
                        content = content,
                        modifier = Modifier
                            .padding(4.dp),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                }
            }
        },
        buttons = {
            Row {
                TextButton(onClick = { openDownloadUrl(downloadUrl) }) {
                    Text("下载(Github)")
                }
                TextButton(onClick = { openDownloadUrl("https://ghproxy.com/${downloadUrl}") }) {
                    Text("下载(ghproxy加速)")
                }
            }
        }
    )
}