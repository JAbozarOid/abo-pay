package com.jabozaroid.abopay.core.network.di.module

import com.jabozaroid.abopay.core.data.dataSource.auth.AuthenticationRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.balance.BalanceRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.bill.BillRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.c2c.C2CDataSource
import com.jabozaroid.abopay.core.data.dataSource.cardmanagement.CardManagementRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.charge.ChargeRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.home.HomeServicesRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.internet.InternetRemoteDataSource
import com.jabozaroid.abopay.core.data.dataSource.payment.PaymentDataSource
import com.jabozaroid.abopay.core.data.dataSource.tsm.TsmRemoteDataSource
import com.jabozaroid.abopay.core.network.dataSource.balance.BalanceMockDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.balance.BalanceRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.bill.BillRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.c2c.C2CMockDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.c2c.C2CRemoteDataSourceImp
import com.jabozaroid.abopay.core.network.dataSource.cardmanagement.CardManagementRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.charge.ChargeRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.home.HomeServicesMockDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.home.HomeServicesRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.internet.InternetMockDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.internet.InternetRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.login.AuthenticationRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.payment.PaymentMockDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.payment.PaymentRemoteDataSourceImpl
import com.jabozaroid.abopay.core.network.dataSource.tsm.TsmRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAuthenticationDataSource(authenticationDataSourceImpl: AuthenticationRemoteDataSourceImpl): AuthenticationRemoteDataSource


    @Binds
    abstract fun bindChargeRemoteDataSource(chargeRemoteDataSourceImpl: ChargeRemoteDataSourceImpl): ChargeRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.HOME_SERVICE_REMOTE_DATASOURCE)
    abstract fun bindHomeServicesDataSource(homeServicesRemoteDataSourceImpl: HomeServicesRemoteDataSourceImpl): HomeServicesRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.HOME_SERVICE_MOCK_DATASOURCE)
    abstract fun bindHomeServicesMockDataSource(homeServicesMockDataSourceImpl: HomeServicesMockDataSourceImpl): HomeServicesRemoteDataSource


    @Binds
    abstract fun bindBillRemoteDataSource(billRemoteDataSourceImpl: BillRemoteDataSourceImpl): BillRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.INTERNET_REMOTE_DATASOURCE)
    abstract fun bindInternetRemoteDataSource(internetRemoteDataSourceImpl: InternetRemoteDataSourceImpl): InternetRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.INTERNET_MOCK_DATASOURCE)
    abstract fun bindInternetMockDataSource(internetMockDataSourceImpl: InternetMockDataSourceImpl): InternetRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.BALANCE_REMOTE_DATASOURCE)
    abstract fun bindBalanceRemoteDataSource(balanceRemoteDataSourceImpl: BalanceRemoteDataSourceImpl): BalanceRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.BALANCE_MOCK_DATASOURCE)
    abstract fun bindBalanceMockDataSource(balanceMockDataSourceImpl: BalanceMockDataSourceImpl): BalanceRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.C2C_REMOTE_DATASOURCE)
    abstract fun bindC2CRemoteDataSource(c2cRemoteDataSourceImp: C2CRemoteDataSourceImp): C2CDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.C2C_MOCK_DATASOURCE)
    abstract fun bindC2CMockDataSource(c2CMockDataSourceImpl: C2CMockDataSourceImpl): C2CDataSource

    @Binds
    abstract fun bindTsmRemoteDataSource(tsmRemoteDataSourceImpl: TsmRemoteDataSourceImpl) : TsmRemoteDataSource

    @Binds
    abstract fun bindCardManagementRemoteDataSource(cardManagementRemoteDataSourceImpl: CardManagementRemoteDataSourceImpl): CardManagementRemoteDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.PAYMENT_REMOTE_DATASOURCE)
    abstract fun bindPaymentRemoteDataSource(paymentRemoteDataSource: PaymentRemoteDataSourceImpl): PaymentDataSource

    @Binds
    @Named(com.jabozaroid.abopay.core.common.PAYMENT_MOCK_DATASOURCE)
    abstract fun bindPaymentMockDataSource(paymentMockDataSource: PaymentMockDataSourceImpl): PaymentDataSource
}



