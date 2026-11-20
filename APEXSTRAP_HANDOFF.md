# Apexstrap source handoff — Stage 1

## Source basis
- Base: the supplied Chevstrap 2.0 source tree.
- Fast Flags addition: the supplied `Chevstrap-2.0-fastflags` source variant.
- The Chevstrap 2.0 source tree is retained, including its existing Discord Gateway/RPC, theme, custom background, logs, and other launcher code.

## Fast Flags files added by the supplied Fast Flags variant
- `app/src/main/java/com/chevstrap/rbx/FastFlagsManager.kt`
- `app/src/main/java/com/chevstrap/rbx/FastFlagsDialog.kt`
- `app/src/main/java/com/chevstrap/rbx/ui/views/settings/pages/Chevstrap.kt` (adds the Fast Flags Editor settings entry)

## Important limitations — not yet production-validated
1. The current `FastFlagsManager` stores `ClientAppSettings.json` under the launcher's private `filesDir/Modifications/ClientSettings` directory. This handoff does **not** prove that Roblox running inside a Virtual Space can read that file. A Virtual Space-specific file bridge/import mechanism still needs to be designed and tested against the selected Virtual Space app.
2. The current dialog is a basic editor. It does not yet provide the requested full feature set such as categorized presets, robust import/export, validation, search, or a verified apply-to-Roblox workflow.
3. No APK build is included. Build validation was attempted with `bash gradlew :app:assembleDebug --offline --no-daemon`, but the Gradle wrapper tried to download Gradle 9.7.1 and network access was unavailable (`UnknownHostException: services.gradle.org`). Therefore compilation is **unverified**, not passed.

## Recommended next steps
1. Import this source tree into the target repository on a new branch.
2. Run the existing GitHub Actions debug workflow and fix any compile errors.
3. Decide which Virtual Space app is the first target, then implement and verify its supported file handoff path before claiming Fast Flags are applied to Roblox.
4. Expand the editor only after the apply path is confirmed.

Do not remove existing Chevstrap 2.0 functionality while doing these steps.
