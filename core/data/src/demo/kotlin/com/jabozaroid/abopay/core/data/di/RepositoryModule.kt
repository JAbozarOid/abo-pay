package com.jabozaroid.abopay.core.data.di

import com.jabozaroid.abopay.core.data.repository.auth.AuthenticationRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.balance.BalanceRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.bill.BillRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.c2c.C2CRepositoryImp
import com.jabozaroid.abopay.core.data.repository.cardmanagement.CardManagementRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.charge.ChargeRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.home.HomeServicesRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.internet.InternetRepositoryImpl
import com.jabozaroid.abopay.core.data.repository.payment.PaymentRepositoryImp
import com.jabozaroid.abopay.core.data.repository.tsm.TsmRepositoryImpl
import com.jabozaroid.abopay.core.domain.repository.auth.AuthenticationRepository
import com.jabozaroid.abopay.core.domain.repository.balance.BalanceRepository
import com.jabozaroid.abopay.core.domain.repository.bill.BillRepository
import com.jabozaroid.abopay.core.domain.repository.c2c.C2CRepository
import com.jabozaroid.abopay.core.domain.repository.cardmanagement.CardManagementRepository
import com.jabozaroid.abopay.core.domain.repository.charge.ChargeRepository
import com.jabozaroid.abopay.core.domain.repository.home.HomeServicesRepository
import com.jabozaroid.abopay.core.domain.repository.internet.InternetRepository
import com.jabozaroid.abopay.core.domain.repository.payment.PaymentRepository
import com.jabozaroid.abopay.core.domain.repository.tsm.TsmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    abstract fun bindHomeServicesRepository(homeServicesRepositoryImpl: HomeServicesRepositoryImpl): HomeServicesRepository

    @Binds
    abstract fun bindChargeRepository(chargeRepositoryImpl: ChargeRepositoryImpl): ChargeRepository

    @Binds
    abstract fun bindBillRepository(billRepositoryImpl: BillRepositoryImpl): BillRepository

    @Binds
    abstract fun bindInternetRepository(internetRepositoryImpl: InternetRepositoryImpl): InternetRepository

    @Binds
    abstract fun bindBalanceRepository(balanceRepositoryImpl: BalanceRepositoryImpl): BalanceRepository

    @Binds
    abstract fun bindC2CRepository(c2cRepositoryImp: C2CRepositoryImp): C2CRepository

    @Binds
    abstract fun bindCardManagementRepository(cardManagementRepositoryImpl: CardManagementRepositoryImpl): CardManagementRepository

    @Binds
    abstract fun bindTsmRepository(tsmRepositoryImpl: TsmRepositoryImpl): TsmRepository

    @Binds
    abstract fun bindPaymentRepository(paymentRepositoryImp: PaymentRepositoryImp): PaymentRepository
}