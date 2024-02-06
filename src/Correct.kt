interface Person {

    fun <T> map(mapper: Mapper<T>): T

    class Base(private val id: Int, private val name: String, private val surname: String) : Person {
        override fun <T> map(mapper: Mapper<T>): T {
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
    }
}

fun main() {
    val person = Person.Base(1,"1","123")
    val result = person.map(Person.Mapper.CompareId(4))
    println(result)
}