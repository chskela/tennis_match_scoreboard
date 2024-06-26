package services

import models.entities.CurrentMatch
import models.entities.Player
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object OngoingMatchesService {
    fun createNewMatch(player1: Player, player2: Player): UUID {
        val uuid = UUID.randomUUID()
        currentMatch[uuid] = CurrentMatch(player1, player2)
        log.info("Create new Match with UUID $uuid and players $player1 and $player2")
        return uuid
    }

    fun getCurrentMatch(uuid: UUID): Result<CurrentMatch> {
        return runCatching {
            currentMatch.getOrElse(uuid) { throw NoSuchElementException("No match with UUID $uuid found") }
        }
    }

    fun updateMatch(uuid: UUID, match: CurrentMatch) {
        currentMatch[uuid] = match
        log.info("Update match with UUID $uuid")
    }

    fun deleteMatch(uuid: UUID): CurrentMatch? {
        log.info("Remove match with UUID $uuid")
        return currentMatch.remove(uuid)
    }

    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val currentMatch: ConcurrentHashMap<UUID, CurrentMatch> = ConcurrentHashMap()
}