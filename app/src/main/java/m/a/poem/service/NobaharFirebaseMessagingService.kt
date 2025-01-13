package m.a.poem.service

import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import m.a.poem.api.storage.optional
import m.a.poem.config.PrefKeys
import m.a.poem.domain.storage.LocalStorage
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
}