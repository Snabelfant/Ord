package dag.ord.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

import java.io.InputStream
import java.io.OutputStream

class JsonMapper {
    private val writerPretty: ObjectWriter
    private val writer: ObjectWriter
    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.registerModule(JavaTimeModule())
        writerPretty = objectMapper.writerWithDefaultPrettyPrinter()
        writer = objectMapper.writer()
    }

    fun write(outputStream: OutputStream, any: Any, pretty: Boolean = false) =
            if (pretty) {
                writerPretty.writeValue(outputStream, any)
            } else {
                writer.writeValue(outputStream, any)
            }

    fun write(any: Any, pretty: Boolean = false) : String =
            if (pretty) {
                writerPretty.writeValueAsString( any)
            } else {
                writer.writeValueAsString(any)
            }

    fun <T> read(inputStream: InputStream, typeReference: TypeReference<T>): T =
            objectMapper.readValue(inputStream, typeReference)

    fun<T> read(s : String, type : TypeReference<T>) : T = objectMapper.readValue(s, type)
}
