package m.a.poem.api.repository

import m.a.poem.domain.model.HomeCommunication
import m.a.poem.domain.repository.HomeCommunicationRepository
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