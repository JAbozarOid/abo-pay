# AboPay About

This App is developed by Clean Architecture with the approach of modular per feature and MVI
design pattern for the presentation layer with the help of viewmodel class.

## Tech Stack

It is an app based on the following technologies and patterns:

- Clean Architecture
- Kotlin Coroutines
- Kotlin Flow
- Pagination
- MVI
- Retrofit
- Room
- Jetpack Compose
- Dependency Injection -Hilt

## How to use

The develop branch is the most updated branch but you can Switch between branches
to checkout from a more initial to a more advanced state of the application

## Code Structure for UI Module

- Core Directory
    - ui module
        - com.jabozaroid.abopay.core.ui
            - viewmodel directory
                - abstract base viewmodel class
            - model directory
                - IAction
                - IEvent
                - IViewState
            - view directory
                - abstract base basescreen class

## Code Structure for Navigation

- Core Directory
    - ui module
        - com.jabozaroid.abopay.core.ui
            - navigation directory
                - ApplicationRoutes(Const values for navigation routes)
                - NavigationAnimation(Some navigation as sample witch can be extended or changes
                  according to the requirements)
                - NavigationCommand(A sealed class containing navigation routing operations)
                - NavigationParam(An enum class containing the parameters are supposed to be passed
                  between screens)
    - app module
        - navigation directory
            - AppNavHost(NavHost containing the main branch and subGraphs)

* Every feature has a Navigation file containing its navigation graph

## Code Structure for Network Module

- Core Directory
    - network module
        - com.jabozaroid.abopay.core.network
            - api directory
                - auth directory
                    - AuthenticationApi(Contain Network Api )
            - dataSource directory
                - login directory
                    - AuthenticationRemoteDataSourceImp(Implementation of
                      AuthenticationRemoteDataSource interface)
            - di directory
                - annotation directory
                    - InfoKey(Contain Key value for information interceptor)
                    - Order(Contain interface for create interceptor key map)
                - module directory
                    - DataSourceModule(A abstract class for bind datasource interfaces to data
                      source implementation)
                    - InfoModule(A class for provide info annotation class)
                    - NetworkModule(A class that provides Interceptors, RetrofitBuilder and APIs )
            - helper directory
                - ApiErrorParser(Parses network Exceptions)
                - ApiErrorResponse
                - infoType
              - ResData
              - Execute
                  - RetrofitHelper(Create okHttp Client and Retrofit instance)
            - interceptor directory
                - AuthenticationInterceptor(Add Token to Header of retrofit instance)
                - InformationInterceptor(Add Information about user and device to Header of retrofit
                  instance)
                - RefreshTokenInterceptor(update Token after refresh token)
              - PublicKeyPinningInterceptor(contain public key for SSL pinning)
            - model directory(Contains all NetworkModelParams and NetworkModelResult)

## Code Structure for Data Module

- Core Directory
    - data module
        - com.jabozaroid.abopay.core.data
            - dataSource directory
                - AuthenticationRemoteDataSource(Contain Interface and functions that related to
                  Authentication)
            - di directory
                - RepositoryModule(A abstract class for bind repository interfaces to repository
                  implementation)
            - repository directory
                - AuthenticationRepositoryImpl(Implementation of
                  AuthenticationRepository interface)

## Code Structure for Domain Module

- Core Directory
    - domain module
        - com.jabozaroid.abopay.core.domain
            - AppError(Contain sealed class for error types )
            - Result(contain sealed class and extension function for handle type of data error and
              failure )
                - interface directory
                    - offlinestorage directory
                        - StorageKey
                        - OfflineStorage
                        - SecureStorage
                - model directory
                    - auth directory
                        - LoginOtpParam
                        - LoginOtpResult
                        - LoginParam
                        - LoginResult
                    - exception directory
                        - BusinessException
                    - user directory
                        - Identity
                        - User
                - repository directory
                    - AuthenticationRepository
                - usecase directory
                    - Auth directory
                        - GetLoginPtpUseCase
                        - LogoutUseCase
                    - user directory
                        - GetCurrentUserUseCase
                        - StreamCurrentUserUseCase
                        - UpsertCurrentUserUseCase
                    - BaseStreamUseCase
                    - BaseUseCase

## Module Build-logic

- build-logic directory
    - convention directory
        - main directory
            - java directory
                - com.jabozaroid.abopay (project configurations)
                    - AndroidApplicationComposeConventionPlugin
                    - AndroidApplicationConventionPlugin
                    - AndroidApplicationFirebaseConventionPlugin
                    - AndroidApplicationFlavorsConventionPlugin
                    - AndroidApplicationJacocoConventionPlugin
                    - AndroidFeatureConventionPlugin
                    - AndroidHiltConventionPlugin
                    - AndroidLibraryComposeConventionPlugin
                    - AndroidLibraryConventionPlugin
                    - AndroidLibraryJacocoConventionPlugin
                    - AndroidLintConventionPlugin
                    - AndroidRoomConventionPlugin
                    - AndroidTestConventionPlugin
                    - JvmLibraryConventionPlugin
                - com.jabozaroid.abopay (third parties configurations)
                    - androidCompose.kt
                    - AndroidInstrumentedTests.kt
                    - Badging.kt
                    - GradleManagedDevices.kt
                    - Jacoco.kt
                    - KotlinAndroid.kt
                    - PrintTEstApks.kt
                    - ProjectBuildTypes
                    - ProjectExtensions.kt
                    - ProjectFlavor.kt

## Code Structure for database module

- Core Directory
    - database module
        - com.jabozaroid.abopay.core.database
            - dao directory
                - UserDao(A dao interface for user table functionalities)
            - di directory
                - DaosModule(A di module for providing daos)
                - DatabaseModule(A di module for providing the database instance)
          - model directory(this directory contains the database model entities such as
            UserEntity)
            - util directory
                - DatabaseMigrations(A sample migration if we need to have some changes and database
                  migration in a versioning)
                - UserTypeConvertor(A sample convertor which can be extended according to the future
                  requirements)
          - AboPayDatabase(is the implementation of creating the database instance)
    - test database
      -UserDaoTest(A sample dao test which has the test structure for the other daos)

- ## Code Structure for offlineStorage module

- Core Directory
    - offlinestorage module
        - com.jabozaroid.abopay.core.offlinestorage
            - di directory
                - StorageModule(A di module for providing binding storage interface and its
                  implementations)
            - storage directory
                - datastorestorage
                    - DataStoreStorage(An implementation of OfflineStorage interface for storing and
                      retrieving data from datastore)
                - securestorage
                    - KeyStoreSecureStorage(An implementation of SecureStorage interface for storing
                      encrypted data and retrieving decrypted data from datastore)
                    - SecretKeyGenerator(An object containing key encryption generator functions)
                - SharedPreferences
                    - SharedPreferencesStorage(An implementation of OfflineStorage interface for
                      storing and retrieving data from SharedPreferences)

## Code Structure for DesignSystem

- Core Directory
    - data designsystem
        - com.jabozaroid.abopay.core.designsystem
            - component directory
                - model directory
                    - BannerUiModel
                    - FeatureUiModel
                    - UserAccountUiNModel
                    - UserCardUiModel
                - scrollbar directory
                    - AppScrollbar
                    - LazyScrollbarUtilities
                    - Scrollbar
                    - ThumbExt
                - AppTextFiled(Contain types of textFiled input)
                - Background
                - Banner
                - Button(Contain types of Buttons input)
                - Chip
                - Fab(Contain types of fab input)
                - Feature
                - LoadingWheel
                - Navigation
                - Swappable
                - Tabs
                - Tags
                - TopAppBar
                - UserAccount
                - UserCards
            - icon directory
                - AppIcon(Contains icon that use whole the app)
            - theme directory
                - designsystem directory
                    - AppFont(place for custom font)
                    - Background(place for implement custom background)
                    - Color(place for colors)
                    - colorScheme(place for handle type of color for use in theme class)
                    - Dimens(Contain padding and gap size)
                    - Gradiant(contain data class for gradiant colors)
                    - Shape(contain data class shape for use in Theme)
                    - size(contain data class for basic sizes)
                    - Typography(contain data class for different typography)
                - AppTheme(this file replace to Material theme because this project has custom them)

## Code Structure for notification module

- Core Directory
    - notification module
        - com.jabozaroid.abopay.core.notification
            - util directory
                - FirebaseUtil(Containing required FCM initialization,
                  provideNotificationManager, ... )
            - AboPayFirebaseMessagingService (A service class for received message management)

## Code Structure for analytics module

- Core Directory
    - analytics module
        - com.jabozaroid.abopay.core.analytics
            - di directory
                - TrackerModule(A di module for providing binding Tracker interface and its
                  implementations)
            - tracker directory
                - enum directory(Containing TrackerEvents and BundleKey enum classes)
                - FirebaseTracker(Implementation of Firebase tracker (analytics) for raising events)
                - MetricaTracker(Implementation of Metrica tracker (analytics) for raising events)
                - MetrixTracker(Implementation of Metrix tracker (analytics) for raising events)
                - Tracker(An interface containing tracker functions)
                - TrackerErrorData
                - TrackerManager(an object as an interface for the usage from all modules in app to
                  raise an event or error)

***