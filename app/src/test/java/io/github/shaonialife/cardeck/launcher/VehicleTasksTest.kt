package io.github.shaonialife.cardeck.launcher

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VehicleTasksTest {
    @Test fun samsungHeadersResolveEachDisplayAndIgnoreNestedActivityLines() {
        val parsed =
            parseTaskDisplays(
                """
                Display #189 (activities from top to bottom):
                  * Task{a49ef59 #15224 type=standard A=10594:com.autonavi.minimap U=0}
                    * Hist #0: ActivityRecord{abc com.autonavi.minimap/.Map t15224}
                Display #188 (activities from top to bottom):
                  * Task{9399927 #15219 type=standard A=10444:io.github.shaonialife.cardeck U=0}
                Display #0 (activities from top to bottom):
                  * Task{abcd #14826 type=standard A=10594:com.autonavi.minimap U=0}
                """.trimIndent(),
            )
        assertEquals(listOf("15224"), parsed[189]?.get("com.autonavi.minimap"))
        assertEquals(listOf("14826"), parsed[0]?.get("com.autonavi.minimap"))
        assertEquals(3, parsed.size)
    }

    @Test fun honorHeadersAndRepeatedTasksRemainSupported() {
        val parsed =
            parseTaskDisplays(
                """
                Display: mDisplayId=22
                  * Task{abc #800 type=standard A=10100:com.spotify.music U=0}
                  * Task{abc #800 type=standard A=10100:com.spotify.music U=0}
                  * Task{def #801 type=standard A=10100:com.spotify.music U=0}
                """.trimIndent(),
            )
        assertEquals(listOf("800", "801"), parsed[22]?.get("com.spotify.music"))
    }

    @Test fun closingTargetsExcludePhoneDockAndEveryCarDeckAffinity() {
        val own = "io.github.shaonialife.cardeck"
        val snapshot =
            mapOf(
                0 to mapOf("com.spotify.music" to listOf("1")),
                20 to mapOf("com.example.dock" to listOf("2")),
                21 to mapOf(own to listOf("3"), "$own.recents" to listOf("4"), "com.spotify.music" to listOf("5")),
                22 to mapOf("com.spotify.music" to listOf("6")),
                -1 to mapOf("com.other" to listOf("7")),
            )
        val scoped = vehicleTasks(snapshot, setOf(21, 22, -1), own)
        assertEquals(mapOf("com.spotify.music" to listOf("5", "6")), scoped)
    }

    @Test fun malformedOutputCannotInventTaskTargets() {
        assertTrue(parseTaskDisplays("Permission denied").isEmpty())
        assertTrue(parseTaskDisplays("Task{abc #10 type=standard A=1000:com.example.app U=0}").isEmpty())
    }
}
