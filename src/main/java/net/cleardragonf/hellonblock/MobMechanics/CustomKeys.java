package net.cleardragonf.hellonblock.MobMechanics;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.MapValue;

public class CustomKeys {

    // Example usage of the mapKey method
    public static final Key<MapValue<String, Integer>> CUSTOM_MAP_KEY =
            mapKey(ResourceKey.of("hob", "custom_map_key"), String.class, Integer.class);

    private static <K, V> Key<MapValue<K, V>> mapKey(final ResourceKey resourceKey, final Class<K> keyType, final Class<V> valueType) {
        return Key.builder().key(resourceKey).mapElementType(keyType, valueType).build();
    }
}
