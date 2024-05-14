package models.dao

import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import models.database.PersistenceUtil
import models.entities.Player

class PlayerDao(
    private val entityManager: EntityManager = PersistenceUtil.entityManagerFactory.createEntityManager()
) {
    fun findByName(name: String): Result<Player> {
        val query: TypedQuery<Player> = entityManager
            .createQuery("SELECT p FROM Player p WHERE p.name = :name", Player::class.java)
        query.setParameter("name", name)

        return runCatching {
            query.resultList.first()
        }
    }

    fun save(name: String): Result<Player> {
        val txn = entityManager.transaction

        return runCatching {
            txn.begin()
            entityManager.merge(Player(name = name))
        }.onSuccess { txn.commit() }
            .onFailure { txn.rollback() }
    }
}