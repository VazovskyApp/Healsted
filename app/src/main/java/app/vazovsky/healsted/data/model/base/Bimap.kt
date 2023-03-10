package app.vazovsky.healsted.data.model.base

interface BiMap<K, V> : Map<K, V> {
    val inverse: BiMap<V, K>
    override val values: Set<V>
}

fun <K, V> emptyBiMap(): BiMap<K, V> = @Suppress("UNCHECKED_CAST") (emptyBiMap as BiMap<K, V>)

fun <K, V> Map<K, V>.toBiMap(): BiMap<K, V> =
    if (isNotEmpty()) {
        val inversePairs = entries.map { (key, value) -> value to key }.toMap()
        BiMapImpl(this, inversePairs)
    } else {
        emptyBiMap()
    }

fun <K, V> biMapOf(vararg pairs: Pair<K, V>): BiMap<K, V> = pairs.toMap().toBiMap()

fun <K, V> biMapOf(pair: Pair<K, V>): BiMap<K, V> =
    BiMapImpl(mapOf(pair), mapOf(pair.second to pair.first))

private class BiMapImpl<K, V> private constructor(delegate: Map<K, V>) :
    BiMap<K, V>, Map<K, V> by delegate {
    constructor(forward: Map<K, V>, backward: Map<V, K>) : this(forward) {
        _inverse = BiMapImpl(backward, this)
    }

    private constructor(backward: Map<K, V>, forward: BiMap<V, K>) : this(backward) {
        _inverse = forward
    }

    private lateinit var _inverse: BiMap<V, K>
    override val inverse: BiMap<V, K> get() = _inverse
    override val values: Set<V> get() = inverse.keys

    override fun equals(other: Any?): Boolean = equals(this, other)
    override fun hashCode(): Int = hashCodeOf(this)
}

internal fun equals(bimap: BiMap<*, *>, other: Any?): Boolean {
    if (bimap === other) return true
    if (other !is BiMap<*, *>) return false
    if (other.size != bimap.size) return false
    val i = bimap.entries.iterator()
    while (i.hasNext()) {
        val e = i.next()
        val key = e.key
        val value = e.value
        if (value == null) {
            if (other[key] != null || !other.containsKey(key)) {
                return false
            }
        } else {
            if (value != other[key]) {
                return false
            }
        }
    }
    return true
}

internal fun hashCodeOf(map: Map<*, *>): Int {
    return map.entries.fold(0) { acc, entry ->
        acc + entry.hashCode()
    }
}

private val emptyBiMap = BiMapImpl<Any?, Any?>(emptyMap(), emptyMap())

interface MutableBiMap<K, V> : MutableMap<K, V>, BiMap<K, V> {
    override val inverse: MutableBiMap<V, K>
    override val values: MutableSet<V>

    override fun put(key: K, value: V): V?
    fun forcePut(key: K, value: V): V?
}
