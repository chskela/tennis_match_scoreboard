package services

import models.dao.MatchDao
import models.entities.CurrentMatch
import models.entities.Match
import models.entities.toMatch

class FinishedMatchesPersistenceService {

    fun saveFinishedMatch(currentMatch: CurrentMatch): Result<Match> {
        val matchDao = MatchDao()
        return matchDao.save(currentMatch.toMatch())
    }
}