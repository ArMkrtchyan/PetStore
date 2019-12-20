package am.store.PeetStore.user.model

data class Device(var id: Long,
                  var deviceId: String?,
                  var firebaseToken: String?,
                  var firstInstallDate: String?,
                  var uid: String?,
                  var language: String?,
                  var model: String?,
                  var platform: String?,
                  var sdkVersion: String?,
                  var appVersion: String?
)