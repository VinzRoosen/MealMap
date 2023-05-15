package be.LarsVinz.MealMap.Models

interface Repository <T> {

    fun save(toSave : T)
    fun saveAll(toSaveList : List<T>)

    fun load(toLoad : String) : T
    fun loadAll() : List<T>

    fun delete(toDelete: T)
    fun deleteAll(toDeleteList: List<T>)
}