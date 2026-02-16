package db.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import web.pageable.PageResult
import web.pageable.Pagination

interface BaseRepository<T, ID> {
    suspend fun save(entity: T): T
    suspend fun update(entity: T): T
    suspend fun findById(id: ID): T?
    suspend fun findAll(): Flow<T>
    fun saveAll(entities: Flow<T>): Flow<T> {
        return entities.map { save(it) }
    }
    suspend fun findAll(pagination: Pagination): PageResult<T>
    suspend fun delete(entity: T)
    suspend fun deleteAll()
    suspend fun deleteAll(ids: List<ID>)
    suspend fun deleteById(id: ID)
    suspend fun count(): Long
    suspend fun exists(id: ID): Boolean
    suspend fun saveAll(entities: Iterable<T>): List<T> {
        return entities.map { save(it) }
    }
}