package com.jabozaroid.abopay.main.view


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jabozaroid.abopay.application.App
import com.jabozaroid.abopay.core.database.dao.UserDao
import com.jabozaroid.abopay.core.database.model.UserEntity
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.SharedPrefStorage
import com.jabozaroid.abopay.core.domain.infra.offlinestorage.enums.StorageKey
import com.jabozaroid.abopay.core.model.iva.DarkThemeConfig
import com.jabozaroid.abopay.core.model.iva.ThemeBrand
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.main.model.MainViewState
import com.jabozaroid.abopay.main.viewmodel.MainViewModel
import com.jabozaroid.abopay.core.notification.util.checkNotificationPermission
import com.jabozaroid.abopay.core.notification.util.getPermission
import com.jabozaroid.abopay.core.notification.util.initFirebaseMessagingTopics
import com.jabozaroid.abopay.core.notification.util.initialFCM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    //Its only for test database module functionality and must be removed after review
    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var sharedPrefStorage: SharedPrefStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        //initFCM()

        //TrackerManager.onEvent(TrackersEvent.TEST_EVENT)

        enableEdgeToEdge()

        var uiState: MainViewState by mutableStateOf(viewModel.initialState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow
                    .collect {
                        uiState = it
                    }
            }
        }


        splashScreen.setKeepOnScreenCondition { uiState.loading }

        viewModel.getStartupData()


        lifecycleScope.launch {
//            sharedPrefStorage.save(
//                StorageKey.TOKEN,
//                User.create(
//                    User.Companion.Argument(
//                        "09121111111",
//                        "Atieh00000",
//                        "Fereydooni",
//                        "0077884086",
//                        identity = Identity.Companion.Argument(accessToken = "adsasdf","")
//                    )
//                )
//            )

//            Log.d(
//                "secureStorage",
//                sharedPrefStorage.get(StorageKey.TOKEN, User::class.java)?.firstName!!
//            )
        }

        //Its only for test database module functionality and must be removed after review
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertUsers(
                listOf(
                    UserEntity("1", "Atieh", "Fereydooni"),
                    UserEntity("2", "Sanaz", "Ramezanpour")
                )
            )
            Log.d("Users:", userDao.getUsers().first().toString())
            userDao.updateUser(UserEntity("1", "Atieh2", "Fereydooni2"))
            Log.d("Users:", userDao.getUsers().first().toString())
            userDao.updateUserByName("1", "Atieh3", "Fereydooni3")
            Log.d("Users:", userDao.getUsers().first().toString())
        }

        setContent {
            Box(modifier = Modifier.safeDrawingPadding()) {
                MainActivityContent(uiState)
            }
        }

    }


@Composable
private fun MainActivity.MainActivityContent(uiState: MainViewState) {

    val darkTheme = shouldUserDarkTheme(uiState)

//    CheckLoginState(uiState = uiState)

    AppTheme(
        isDarkTheme = darkTheme,
    ) {
        AppBackground(modifier = Modifier.fillMaxSize()) {
            App(
                startDestination = uiState.startDestination,
                user = uiState.user
            )
        }
    }

}

@Composable
private fun CheckLoginState(uiState: MainViewState) {

    val accessToken = sharedPrefStorage.getString(StorageKey.TOKEN)
    if (accessToken.isBlank()) {
        uiState.startDestination = ApplicationRoutes.loginGraphRoute
    }

}


private fun shouldDisableDynamicTheming(uiState: MainViewState): Boolean =
    if (uiState.loading) false else !uiState.useDynamicColor


private fun shouldUseAndroidTheme(uiState: MainViewState): Boolean =
    if (uiState.loading) false else {
        when (uiState.themeBrand) {
            ThemeBrand.DEFAULT -> false
            ThemeBrand.ANDROID -> true
        }
    }


@Composable
private fun shouldUserDarkTheme(uiState: MainViewState): Boolean =
    if (uiState.loading) isSystemInDarkTheme()
    else {
        when (uiState.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }


/**
 * The default light s-crim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

private fun initFCM() {
    initialFCM()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (!checkNotificationPermission(this@MainActivity)) {
            getPermission(this@MainActivity, granted = { isGranted ->

            })
        }
    }
    initFirebaseMessagingTopics()
}

}