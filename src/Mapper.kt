/**
 * Many projects use data classes. But unfortunately there are public variables, what encapsulation forbids.
 * Example:
 * ---data class Person(val id: Int, val name: String, val surname String)---
 * Better solution with mappers:
 */
interface Person {
    fun <T> personMap(mapper: Mapper<T>): T

    class Base(private val id: Int, private val name: String, private val surname: String) : Person {
        override fun <T> personMap(mapper: Mapper<T>): T {
            return mapper.map(id, name, surname)
        }
    }

    interface Mapper<T> {
        fun map(id: Int, name: String, surname: String): T

        class CompareId(private val id: Int) : Mapper<Boolean> {
            override fun map(id: Int, name: String, surname: String): Boolean {
                return this.id == id
            }
        }

        class Same(private val person: Person) : Mapper<Boolean> {
            override fun map(id: Int, name: String, surname: String): Boolean {
                return person.personMap(CompareId(id))
            }
        }

        class FullName : Mapper<String> {
            override fun map(id: Int, name: String, surname: String): String {
                return "$name $surname"
            }
        }
    }
}


fun main() {
    val personFirst = Person.Base(1, "Jeffrey", "King")
    val personSecond = Person.Base(1, "Manley", "Mary")
    val result = personSecond.personMap(Person.Mapper.Same(personFirst))
    println(result)
    val fullNameFirst = personFirst.personMap(Person.Mapper.FullName())
    val fullNameSecond = personSecond.personMap(Person.Mapper.FullName())
    println(fullNameFirst)
    println(fullNameSecond)
}