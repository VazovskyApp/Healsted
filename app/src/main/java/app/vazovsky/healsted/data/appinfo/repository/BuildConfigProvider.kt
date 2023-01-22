package app.vazovsky.healsted.data.appinfo.repository

/** Класс, который провайдит для конкретного апликейшена BuildConfig */
interface BuildConfigProvider {
    val buildType: String
    val versionCode: Int
    val versionName: String
}
