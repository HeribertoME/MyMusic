package com.heriberto.mymusic.testutil

import java.nio.charset.StandardCharsets

fun readResource(path: String): String {
    val stream = requireNotNull(
        Thread.currentThread().contextClassLoader?.getResourceAsStream(path)
    ) { "Resource not found: $path" }
    return stream.readBytes().toString(StandardCharsets.UTF_8)
}