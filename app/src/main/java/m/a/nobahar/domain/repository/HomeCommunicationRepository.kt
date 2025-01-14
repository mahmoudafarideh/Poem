package m.a.nobahar.domain.repository

import m.a.nobahar.domain.model.HomeCommunication

interface HomeCommunicationRepository {
    fun getCommunication(): HomeCommunication?
    fun setCommunication(communication: HomeCommunication)
}