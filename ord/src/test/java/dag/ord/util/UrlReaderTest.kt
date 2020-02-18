package dag.ord.util

import dag.ord.util.UrlReader
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*
import java.net.UnknownHostException

class UrlReaderTest {

    @Test (expected = UnknownHostException::class)
    fun read1() {
        UrlReader.read("https://bbbvg.no/zyx")
    }

    @Test
    fun read2() {
        val s = UrlReader.read("https://vg.no")
        assertThat(s.isNullOrBlank(), `is`(false))
    }

}