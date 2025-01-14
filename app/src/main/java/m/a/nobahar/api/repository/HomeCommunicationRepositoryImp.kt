package m.a.nobahar.api.repository

import m.a.nobahar.domain.model.HomeCommunication
import m.a.nobahar.domain.repository.HomeCommunicationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeCommunicationRepositoryImp @Inject constructor() : HomeCommunicationRepository {
    private var communication: HomeCommunication? = null
    override fun getCommunication(): HomeCommunication? = communication

    override fun setCommunication(communication: HomeCommunication) {
        this.communication = communication
    }
}