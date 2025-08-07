package io.github.kabirnayeem99.kshare_sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform