package com.github.jing332.frpandroid.model

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.github.jing332.frpandroid.R
import com.github.jing332.frpandroid.ui.SwitchServerActivity


object ShortCuts {
    private inline fun <reified T> buildIntent(context: Context): Intent {
        val intent = Intent(context, T::class.java)
        intent.action = Intent.ACTION_VIEW
        return intent
    }


    private fun buildAlistSwitchShortCutInfo(context: Context): ShortcutInfoCompat {
        val msSwitchIntent = buildIntent<SwitchServerActivity>(context)
        return ShortcutInfoCompat.Builder(context, "alist_switch")
            .setShortLabel(context.getString(R.string.alist_switch))
            .setLongLabel(context.getString(R.string.alist_switch))
            .setIcon(IconCompat.createWithResource(context, R.drawable.baseline_trending_up_24))
            .setIntent(msSwitchIntent)
            .build()
    }


    fun buildShortCuts(context: Context) {
        ShortcutManagerCompat.setDynamicShortcuts(
            context, listOf(
                buildAlistSwitchShortCutInfo(context),
            )
        )
    }


}