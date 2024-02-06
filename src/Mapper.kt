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

        class CompareId(private val id:Int) : Mapper<Boolean> {
            override fun map(id: Int, name: String, surname: String): Boolean {
                return this.id == id
            }
        }

        class Same(private val person: Person) : Mapper<Boolean> {
            override fun map(id: Int, name: String, surname: String): Boolean {
                return person.personMap(CompareId(id))
            }
        }
    }
}


fun main() {
    val person = Person.Base(1, "1", "123")
    val personTwo = Person.Base(1, "1", "12")
    val result = personTwo.personMap(Person.Mapper.Same(person))
    println(result)
}