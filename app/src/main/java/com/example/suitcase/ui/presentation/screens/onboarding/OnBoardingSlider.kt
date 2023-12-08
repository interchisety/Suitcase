package com.example.suitcase.ui.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import com.example.suitcase.R

sealed class OnBoardingSlider(
    @DrawableRes
    val image: Int,
    val description: String
) {
    object First : OnBoardingSlider(
        image = R.drawable.undraw_empty_cart_co35,
        description = "Create your vacation list of items on a single click. Easy and Quicker"
    )

    object Second : OnBoardingSlider(
        image = R.drawable.undraw_sharing_knowledge_03vp,
        description = "Share your created list of items to your love ones, via all platform"
    )
}
