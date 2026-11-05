package io.github.shaonialife.cardeck.audio

import java.io.InputStream

/** 管线每次 read 可短读；必须拼成完整 PCM 帧，不能把短读当作整包丢弃。 */
internal fun readPcmFrame(
    input: InputStream,
    frame: ByteArray,
): Int {
    var offset = 0
    while (offset < frame.size) {
        val count = input.read(frame, offset, frame.size - offset)
        if (count <= 0) return -1
        offset += count
    }
    return offset
}
