package `in`.bitotsav.bitotsav_20.repository

import `in`.bitotsav.bitotsav_20.dao.EventDao
import `in`.bitotsav.bitotsav_20.entity.Event
import androidx.annotation.WorkerThread

class EventRepository(private val eventDao: EventDao) {

    @WorkerThread
    suspend fun insertEvent(event: Event) = eventDao.insert(event)

    @WorkerThread
    suspend fun updateEvent(event: Event) = eventDao.update(event)

    @WorkerThread
    suspend fun deleteEvent(event: Event) = eventDao.delete(event)

    @WorkerThread
    suspend fun deleteAllEvents() = eventDao.deleteAll()

    @WorkerThread
    suspend fun getEvent(id: Int) = eventDao.getEvent(id)

    @WorkerThread
    suspend fun getAllEvents() = eventDao.getAllEvents()

    @WorkerThread
    suspend fun getAllEventsForDay(day: String) = eventDao.getEventsForDay(day)

    @WorkerThread
    suspend fun getAllFlagshipEvents() = eventDao.getAllFlagshipEvents()
}