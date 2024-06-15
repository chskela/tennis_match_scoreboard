package models.entities

data class MatchesState(
    val matches: List<Match>,
    val lastPageNumber: Long,
)
