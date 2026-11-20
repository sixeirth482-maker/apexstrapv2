package com.chevstrap.rbx

import org.json.JSONObject
import java.io.File

/**
 * Fast Flags storage, ported from Chevstrap 1.9 (FFlagsSettingsManager / ListFFlagsPresets).
 * Flags are kept in Modifications/ClientSettings/ClientAppSettings.json inside the app's files dir.
 */
object FastFlagsManager {

    // Suggested flag names carried over from 1.9's ListFFlagsPresets.
    val presetKeys: List<String> = listOf(
        "FIntDebugForceMSAASamples",
        "FFlagDebugGraphicsPreferD3D11",
        "FFlagDebugGraphicsPreferD3D11FL10",
        "FFlagDebugGraphicsPreferVulkan",
        "FFlagDebugGraphicsPreferOpenGL",
        "FFlagDebugGraphicsDisableDirect3D11",
        "FIntRenderShadowIntensity",
        "FIntRenderShadowmapBias",
        "DFIntBandwidthManagerApplicationDefaultBps",
        "DFIntBandwidthManagerDataSenderMaxWorkCatchupMs",
        "DFIntAssetPreloading",
        "DFIntNumAssetsMaxToPreload",
        "DFIntConnectionMTUSize",
        "FFlagDisablePostFx",
        "FIntFRMMaxGrassDistance",
        "FIntRenderGrassDetailStrands",
        "FIntFRMMinGrassDistance",
        "FFlagDebugDisplayFPS",
        "DFIntTaskSchedulerTargetFps",
        "FFlagRenderUnifiedLighting12",
        "FIntRobloxGuiBlurIntensity"
    )

    private fun settingsFile(): File {
        val base = requireNotNull(App.appContext?.filesDir) { "App context not ready" }
        val dir = File(base, "Modifications/ClientSettings")
        if (!dir.exists()) dir.mkdirs()
        return File(dir, "ClientAppSettings.json")
    }

    fun load(): JSONObject {
        val file = settingsFile()
        return try {
            if (file.exists()) JSONObject(file.readText()) else JSONObject()
        } catch (e: Exception) {
            JSONObject()
        }
    }

    fun save(json: JSONObject) {
        settingsFile().writeText(json.toString(4))
    }

    fun set(name: String, rawValue: String) {
        val json = load()
        json.put(name, parseValue(rawValue))
        save(json)
    }

    fun remove(name: String) {
        val json = load()
        json.remove(name)
        save(json)
    }

    // Roblox reads typed values, so keep integers and booleans as their JSON types.
    private fun parseValue(raw: String): Any {
        raw.toIntOrNull()?.let { return it }
        raw.toBooleanStrictOrNull()?.let { return it }
        return raw
    }
}
