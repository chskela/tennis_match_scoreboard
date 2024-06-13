package models.dao

import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import models.database.PersistenceUtil
import models.entities.Match

class MatchDao(
    private val entityManager: EntityManager = PersistenceUtil.entityManagerFactory.createEntityManager()
) {
    fun getMatchByPlayerName(playerName: String, page: Int = 0): Result<List<Match>>  {
        val query: TypedQuery<Match>  = entityManager.createQuery(
            """SELECT m FROM Match m 
                |WHERE m.playerOne.name = :playerName OR m.playerTwo.name = :playerName 
                |limit 5 offset :page""".trimMargin(),
            Match::class.java
        )
        query.setParameter("playerName", playerName)
        query.setParameter("page", page)

        return runCatching {
            query.resultList
        }
    }

    fun save(match: Match): Result<Match> {
        val txn = entityManager.transaction

        return runCatching {
            txn.begin()
            entityManager.merge(match)
        }.onSuccess { txn.commit() }
            .onFailure { txn.rollback() }
    }
}