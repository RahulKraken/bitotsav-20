package `in`.bitotsav.bitotsav_20.schedule.data

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
    fun getAllEvents() = eventDao.getAllEvents()

    @WorkerThread
    fun getAllEventsForDay(day: String) = eventDao.getEventsForDay(day)

    @WorkerThread
    fun getAllFlagshipEvents() = eventDao.getAllFlagshipEvents()
}