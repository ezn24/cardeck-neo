package io.github.shaonialife.cardeck.launcher

/** 两种系统 dump 标题都要支持；只取 Task 行，忽略 ActivityRecord 等嵌套重复。 */
internal fun parseTaskDisplays(dump: String): Map<Int, Map<String, List<String>>> {
    val display = Regex("""(?:Display #(\d+)|Display: mDisplayId=(\d+))""")
    val task = Regex("""Task\{[^\x7D\r\n]*?#(\d+)[^\x7D\r\n]*?\bA=\d+:([A-Za-z0-9_.]+)(?=\s|:|\x7D)""")
    var id = -1
    val out = linkedMapOf<Int, MutableMap<String, MutableSet<String>>>()
    for (line in dump.lineSequence()) {
        display.find(line)?.let { id = (it.groupValues[1].ifEmpty { it.groupValues[2] }).toInt() }
        val match = task.find(line) ?: continue
        if (id < 0) continue
        out.getOrPut(id) { linkedMapOf() }.getOrPut(match.groupValues[2]) { linkedSetOf() }.add(match.groupValues[1])
    }
    return out.mapValues { (_, apps) -> apps.mapValues { it.value.toList() } }
}

/** 手机主屏与 CarDeck 自身永远不能成为关闭目标。 */
internal fun vehicleTasks(
    snapshot: Map<Int, Map<String, List<String>>>,
    displays: Set<Int>,
    ownPackage: String,
): Map<String, List<String>> {
    val result = linkedMapOf<String, MutableSet<String>>()
    snapshot.filterKeys { it >= 0 && it in displays }.values.forEach { apps ->
        apps.filterKeys { it != ownPackage && !it.startsWith("$ownPackage.") }.forEach { (pkg, tasks) ->
            result.getOrPut(pkg) { linkedSetOf() }.addAll(tasks)
        }
    }
    return result.mapValues { it.value.toList() }
}
