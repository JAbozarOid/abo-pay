package com.jabozaroid.abopay.feature.bill.view.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.toolbar.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.QuestionnaireBottomSheet
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.component.model.FrequentUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.domain.model.bill.BillInquiryParam
import com.jabozaroid.abopay.core.domain.model.bill.BillTypeResult
import com.jabozaroid.abopay.feature.bill.model.BillUiModel
import com.jabozaroid.abopay.feature.bill.view.bottomSheet.AddBillBottomSheet
import com.jabozaroid.abopay.feature.bill.view.bottomSheet.EditeBillBottomSheet
import com.jabozaroid.abopay.feature.bill.view.bottomSheet.InquiryBillBottomSheet
import com.jabozaroid.abopay.feature.bill.view.bottomSheet.PaymentBillBottomSheet
import com.jabozaroid.abopay.feature.bill.view.bottomSheet.PaymentInputsBillBottomSheet

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun BillHomeContent(
    state: BillUiModel,
    onItemClick: (billType: BillTypeResult?) -> Unit,
    onButtonInquiry: (billInquiryParam: BillInquiryParam) -> Unit,
    onDismissAddBillBottomSheet: () -> Unit,
    onConfirmButtonPaymentInputBottomSheet: (paymentId: String, billId: String) -> Unit,
    onDismissInquiryBottomSheet: () -> Unit,
    onBackButtonClick: () -> Unit,
    onValueChangeFirstTextFiles: (value: String) -> Unit,
    onFrequentEditIconClicked: (item: FrequentUiModel) -> Unit,
    onFrequentRemoveIconClicked: (item: FrequentUiModel) -> Unit,
    onHideRemoveBottomSheet: () -> Unit,
    onHideEditeBottomSheet: () -> Unit,
    onConfirmEditeBottomSheet: () -> Unit,
    onPaymentFromInquiryBottomSheet: () -> Unit,
    onFrequentItemClick: (FrequentUiModel) -> Unit,
    onShowPaymentInputBottomSheet: () -> Unit,
    onClosePaymentInputBottomSheet: () -> Unit,
    onDisMissPaymentBillBottomSheet: () -> Unit,
    onCancelButtonPaymentBillBottomSheet: () -> Unit,
    onPaymentButtonPaymentBillBottomSheet: () -> Unit,
    onDeleteFrequentBillDeleteBottomSheet: (FrequentUiModel) -> Unit,
    onValueChangeNameTetFiledEditeBottomSheet: (String) -> Unit,
    isOverlayViewVisibility: Boolean = false,
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.aboBackgroundScreen)
    ) {
        var bottomSheetLogo by remember {
            mutableStateOf("")
        }
        var inquiryBottomSheetLogo by remember {
            mutableStateOf("")
        }

        var firstTextFiledValue by rememberSaveable { mutableStateOf("") }
        var secondTextFiledValue by rememberSaveable { mutableStateOf("") }

        var firstTextFiledPaymentInputValue by rememberSaveable { mutableStateOf("") }
        var secondTextFiledPaymentInputValue by rememberSaveable { mutableStateOf("") }

        if (state.paymentBottomSheet.isVisible) {
            onClosePaymentInputBottomSheet()
            PaymentBillBottomSheet(
                title = aboPayStringResource(id = R.string.bill_inquiry),
                paymentParam = state.paymentBottomSheet.uiBillPaymentParam,
                onDismiss = {
                    onDisMissPaymentBillBottomSheet()

                },
                onPaymentButton = onPaymentButtonPaymentBillBottomSheet,
                onCancelButton = onCancelButtonPaymentBillBottomSheet
            )

        }

        if (state.inquiryBottomSheet.isVisible) {
            //when user wants to do payment with paymentId
            InquiryBillBottomSheet(
                onDismiss = {
                    onDismissInquiryBottomSheet()
                },
                title = aboPayStringResource(id = R.string.bill_title),
                logo = inquiryBottomSheetLogo,
                onPayment = {
                    onPaymentFromInquiryBottomSheet()
                },
                onCancel = { onDismissInquiryBottomSheet() },

                btn2Text = aboPayStringResource(id = R.string.cancel),
                btn1Text = aboPayStringResource(R.string.pay),
                model = state.inquiryBottomSheet.uiBillInquiry,
                telephone = null,
                phoneNumber = null
            )
        }

        if (state.addBillBottomSheet.isVisible) {

            AddBillBottomSheet(
                title = aboPayStringResource(R.string.bill_information),
                onDismiss = {
                    onDismissAddBillBottomSheet()
                },
                onBtnClickCancel = {
                    onDismissAddBillBottomSheet()
                },
                onBtnClickInquiry = {
                    val inquiryParam = BillInquiryParam(
                        id = firstTextFiledValue,
                        type = state.addBillBottomSheet.billType?.id ?: -1,
                        name = secondTextFiledValue,
                        termType = null
                    )
                    onButtonInquiry(inquiryParam)
                    inquiryBottomSheetLogo = state.addBillBottomSheet.billType?.darkLogo ?: ""
                },
                error = state.addBillBottomSheet.error.aboPayStringResource() ?: "",
                billType = state.addBillBottomSheet.billType?.id ?: -1,
                firstTextFiledMaxLength = state.addBillBottomSheet.billType?.parameterMaxLength
                    ?: 20,
                secondTextFiledTitle = aboPayStringResource(id = R.string.title_optional),
                firstTextFiledTitle = state.addBillBottomSheet.billType?.parameterName
                    ?: aboPayStringResource(id = R.string.payment_id),
                secondTextFiledPlaceHolder = aboPayStringResource(id = R.string.name_of_bill_place_holder),
                firstTextFiledPlaceHolder = "${state.addBillBottomSheet.billType?.parameterName} را وارد کنید ",
                logo = state.addBillBottomSheet.billType?.darkLogo ?: "",
                btn1Title = aboPayStringResource(R.string.inquiry),
                btn2Title = aboPayStringResource(id = R.string.cancel),
                onValueChangeFirstTextFiled = {
                    firstTextFiledValue = it
                    onValueChangeFirstTextFiles(it)
                },
                onValueChangeSecondTextFiled = {
                    secondTextFiledValue = it
                }

            )
        }

        if (state.paymentInputBottomSheet.isVisible) {
            PaymentInputsBillBottomSheet(
                onValueChangeSecondTextFiled = {
                    secondTextFiledPaymentInputValue = it
                },
                onValueChangeFirstTextFiled = {
                    firstTextFiledPaymentInputValue = it
                },
                onBtnClickCancel = {
                    onClosePaymentInputBottomSheet()
                },
                onBtnClickConfirm = {
                    onConfirmButtonPaymentInputBottomSheet(
                        firstTextFiledPaymentInputValue,
                        secondTextFiledPaymentInputValue
                    )
                },
                onDismiss = {
                    onClosePaymentInputBottomSheet()
                },
                billIdError = state.paymentInputBottomSheet.billIdError,
                payIdError = state.paymentInputBottomSheet.paymentIdError
            )
        }

        if (state.editeBottomSheet.isVisible) {
            EditeBillBottomSheet(
                firstTextFiledTitle = state.editeBottomSheet.item?.billTypeName ?: "",
                secondTextFiledTitle = aboPayStringResource(id = R.string.title),
                billId = state.editeBottomSheet.item?.secondText ?: "",
                billName = state.editeBottomSheet.item?.firstText ?: "",
                billType = state.editeBottomSheet.item?.id ?: -1,
                onBtnClickCancel = {
                    onHideEditeBottomSheet()
                },
                onBtnClickEdite = {
                    onConfirmEditeBottomSheet()
                },
                onDismiss = {
                    onHideEditeBottomSheet()
                },
                onValueChangeSecondTextFiled = { onValueChangeNameTetFiledEditeBottomSheet(it) }
            )
        }

        if (state.removeBottomSheet.visible) {
            QuestionnaireBottomSheet(
                bottomSheetTitle = aboPayStringResource(R.string.delete_bill),
                bottomSheetQuestion = aboPayStringResource(id = R.string.delete_bill_message),
                onHideBottomSheet = {
                    onHideRemoveBottomSheet()
                },
                onBtnConfirmClicked = {
                    onDeleteFrequentBillDeleteBottomSheet(state.removeBottomSheet.item)
                }
            )
        }

        val (toolBarRef, content) = createRefs()

        AppToolbar(modifier = Modifier
            .constrainAs(toolBarRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = Dimens.size_8)
            .fillMaxWidth(),
            toolbarTitle = aboPayStringResource(R.string.bill_title),
            onRightIconClicked = onBackButtonClick)

        ContentCard(
            modifier = Modifier.constrainAs(
                content
            ) {
                top.linkTo(toolBarRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            },
            state = state,
            onItemClick = { it ->
                bottomSheetLogo = it.logo ?: ""
                onItemClick(
                    it.model?.let {
                        it as BillTypeResult
                    })
            },
            isSelectedItem = state.addBillBottomSheet.isVisible,
            onFrequentRemoveIconClicked = {
                onFrequentRemoveIconClicked(it)
            },
            onFrequentEditIconClicked = {
                onFrequentEditIconClicked(it)
                //passDataTo bottomSheet
//                showAddBillBottomSheet = true
                //TODO:Open Eedit Bottom Sheet
            },
            onFrequentItemClick = onFrequentItemClick,
            onShowPaymentInputBottomSheet = onShowPaymentInputBottomSheet,
            isOverlayViewVisibility = isOverlayViewVisibility,
        )
    }
}

@Preview(showBackground = true)
@ThemePreviews
@Composable
internal fun PreviewBillHomeContent() {
    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        BillHomeContent(
            state = BillUiModel(),
            onItemClick = {},
            onButtonInquiry = {},
            onDismissAddBillBottomSheet = {},
            onConfirmButtonPaymentInputBottomSheet = { paymentId, billId -> },
            onDismissInquiryBottomSheet = {},
            onBackButtonClick = {},
            onValueChangeFirstTextFiles = {},
            onHideRemoveBottomSheet = {},
            onFrequentEditIconClicked = {},
            onFrequentRemoveIconClicked = {},
            onConfirmEditeBottomSheet = {},
            onHideEditeBottomSheet = {},
            onPaymentFromInquiryBottomSheet = {},
            onFrequentItemClick = {},
            onShowPaymentInputBottomSheet = {},
            onClosePaymentInputBottomSheet = {},
            onDisMissPaymentBillBottomSheet = {},
            onPaymentButtonPaymentBillBottomSheet = {},
            onCancelButtonPaymentBillBottomSheet = {},
            onDeleteFrequentBillDeleteBottomSheet = {},
            onValueChangeNameTetFiledEditeBottomSheet = {}
        )

    }
}

