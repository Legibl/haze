// Copyright 2024, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("NOTHING_TO_INLINE")

package dev.chrisbanes.haze

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionOnScreen
import kotlin.math.hypot

internal fun calculateLength(
  start: Offset,
  end: Offset,
  size: Size,
): Float {
  val (startX, startY) = start
  val endX = end.x.coerceAtMost(size.width)
  val endY = end.y.coerceAtMost(size.height)
  return hypot(endX - startX, endY - startY)
}

internal fun Size.expand(expansion: Float): Size {
  return Size(width = width + expansion, height = height + expansion)
}

internal fun lerp(start: Float, stop: Float, fraction: Float): Float {
  return start + fraction * (stop - start)
}

internal inline val Offset.orZero: Offset get() = takeOrElse { Offset.Zero }

internal inline fun <T> unsynchronizedLazy(noinline initializer: () -> T): Lazy<T> {
  return lazy(mode = LazyThreadSafetyMode.NONE, initializer)
}

/**
 * Workaround for https://youtrack.jetbrains.com/issue/CMP-7380
 */
internal fun LayoutCoordinates.positionOnScreenCatching(): Offset {
  return try {
    positionOnScreen()
  } catch (t: Throwable) {
    Offset.Unspecified
  }
}
