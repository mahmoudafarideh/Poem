package m.a.poem.domain.repository

import m.a.poem.domain.model.HomeCommunication

interface HomeCommunicationRepository {
    fun getCommunication(): HomeCommunication?
    fun setCommunication(communication: HomeCommunication)
}