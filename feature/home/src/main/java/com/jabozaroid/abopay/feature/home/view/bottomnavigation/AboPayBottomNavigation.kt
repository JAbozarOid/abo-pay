package com.jabozaroid.abopay.feature.home.view.bottomnavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppTextNavItem
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_0
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_16
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_8
import com.jabozaroid.abopay.core.ui.DevicePreviews
import com.jabozaroid.abopay.feature.home.BottomNavItem
import com.jabozaroid.abopay.feature.home.R

/**
 * Created on 27,August,2024
 */
@Composable
internal fun HomeBottomNavigation() {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(2)
    }
    val bottomNavItems = listOf(
        BottomNavItem(
            aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bottom_nav_item_profile),
            R.drawable.nav_item_profile
        ),
        BottomNavItem(
            aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bottom_nav_item_iva),
            R.drawable.nav_item_abo_pay_plus
        ),
        BottomNavItem(
            aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bottom_nav_item_home),
            R.drawable.nav_item_home
        ),
        BottomNavItem(
            aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bottom_nav_item_wallet),
            R.drawable.nav_item_wallet
        ),
        BottomNavItem(
            aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.bottom_nav_item_services),
            R.drawable.nav_item_services
        )
    )
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(size_8, size_0, size_8, size_0)
            .clip(RoundedCornerShape(size_16, size_16, size_0, size_0))
            .absoluteOffset(y = (-4).dp),
        windowInsets = WindowInsets(top = size_0),
        containerColor = AppTheme.colorScheme.bottomNavBackground
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        Spacer(modifier = Modifier.size((screenWidth / 14).dp, 60.dp))
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                modifier = Modifier.padding(top = size_0),
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
//                        navController.navigate(tabBarItem.title)
                },
                icon = {
                    Column {
                        if (selectedTabIndex == index)
                            Spacer(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(Dimens.size_4)
                                    .clip(RoundedCornerShape(size_8))
                                    .background(AppTheme.colorScheme.bottomNavItem)
                                    .align(Alignment.CenterHorizontally)
                            )
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = size_8),
                            painter = painterResource(id = bottomNavItem.selectedIcon),
                            contentDescription = null
                        )
                    }

                },
                label = {
                    AppTextNavItem(
                        modifier = Modifier.padding(bottom = Dimens.size_25),
                        value = bottomNavItem.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colorScheme.bottomNavItem,
                    unselectedIconColor = AppTheme.colorScheme.bottomNavItem,
                    selectedTextColor = AppTheme.colorScheme.bottomNavItem,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent
                )
            )
        }
        Spacer(modifier = Modifier.size((screenWidth / 14).dp, 60.dp))

    }
}

@DevicePreviews
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ThemePreviews
@Composable
internal fun AboPayServiceItemPreview() {
    AppTheme {
        Scaffold(
            bottomBar = { HomeBottomNavigation() }
        ) {
            Column {

            }
        }
    }
}