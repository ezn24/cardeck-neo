package io.github.shaonialife.cardeck.audio

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream

class PcmPipeTest {
    @Test
    fun pipeShortReadsAreCombinedWithoutDroppingSamples() {
        val samples = ByteArray(1408) { it.toByte() }
        val pipe =
            object : ByteArrayInputStream(samples) {
                override fun read(
                    buffer: ByteArray,
                    offset: Int,
                    length: Int,
                ): Int = super.read(buffer, offset, minOf(length, 37))
            }
        val frame = ByteArray(samples.size)
        assertEquals(samples.size, readPcmFrame(pipe, frame))
        assertArrayEquals(samples, frame)
        assertEquals(-1, readPcmFrame(pipe, frame))
    }

    @Test
    fun truncatedPipeDoesNotSendHalfOfAnAudioFrame() {
        assertEquals(-1, readPcmFrame(ByteArrayInputStream(ByteArray(120)), ByteArray(1408)))
    }
}
