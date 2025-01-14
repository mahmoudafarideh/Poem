package m.a.nobahar.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import m.a.nobahar.api.storage.optional
import m.a.nobahar.config.PrefKeys
import m.a.nobahar.domain.storage.LocalStorage
import javax.inject.Inject

@AndroidEntryPoint
class NobaharFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var localStorage: LocalStorage

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        var firebaseToken: String? by localStorage.optional(PrefKeys.FirebaseToken)
        firebaseToken = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }
}