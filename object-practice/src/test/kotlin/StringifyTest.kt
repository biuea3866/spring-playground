import com.biuea.objectpractice.kotlinpractice.Ex
import com.biuea.objectpractice.kotlinpractice.Name
import com.biuea.objectpractice.kotlinpractice.Stringify.stringify
import org.junit.jupiter.api.Test

class StringifyTest {
    @Test
    fun `should stringify`() {
        class A(@Ex val a: Int = 0, @Name("title") val b: String = "b", val c: String? = null)
        println(stringify(A())) // {a:0,b:"b"}
    }
}