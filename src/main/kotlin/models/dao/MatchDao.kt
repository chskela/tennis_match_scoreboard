package models.dao

import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import models.database.PersistenceUtil
import models.entities.Match

class MatchDao(
    private val entityManager: EntityManager = PersistenceUtil.entityManagerFactory.createEntityManager()
) {
    fun getMatchesByPlayerName(playerName: String, pageNumber: Int = 1, recordsPerPage: Int): Result<List<Match>> {
        val query: TypedQuery<Match> = entityManager.createQuery(
            """SELECT m FROM Match m 
                |WHERE m.player1.name = :playerName 
                |OR m.player2.name = :playerName """.trimMargin(),
            Match::class.java
        )
        query.setParameter("playerName", playerName)
        query.maxResults = recordsPerPage
        query.firstResult = recordsPerPage * (pageNumber - 1)
        return runCatching {
            query.resultList
        }
    }

    fun getAllMatches(pageNumber: Int = 1, recordsPerPage: Int): Result<List<Match>> {
        val query: TypedQuery<Match> = entityManager.createQuery(
            "SELECT m FROM Match m",
            Match::class.java
        )
        query.maxResults = recordsPerPage
        query.firstResult = recordsPerPage * (pageNumber - 1)
        return runCatching {
            query.resultList
        }
    }

    fun countMatchesByPlayerName(playerName: String): Result<Long> {
        val query: TypedQuery<Long> = entityManager.createQuery(
            """SELECT count(m.id) FROM Match m 
                |WHERE m.player1.name = :playerName 
                |OR m.player2.name = :playerName """.trimMargin(),
            Long::class.java
        )
        query.setParameter("playerName", playerName)
        return runCatching {
            query.singleResult
        }
    }

    fun countAllMatches(): Result<Long> {
        val query: TypedQuery<Long> = entityManager.createQuery(
            "SELECT count(m.id) FROM Match m", Long::class.java
        )
        return runCatching {
            query.singleResult
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