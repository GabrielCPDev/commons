package db.repositories

import web.pageable.PageResult
import web.pageable.Pagination

interface BaseRepository <T, ID> {
    suspend fun save(entity: T): T
    suspend fun update(entity: T): T
    suspend fun findById(id: ID): T?
    suspend fun findAll(): List<T>
    suspend fun findAll(pagination: Pagination): PageResult<T>
    suspend fun delete(entity: T)
    suspend fun deleteAll()
    suspend fun deleteAll(ids: List<ID>)
    suspend fun deleteById(id: ID)
    suspend fun existsById(id: ID): Boolean
    suspend fun count(): Long
    suspend fun exists(): Boolean

}