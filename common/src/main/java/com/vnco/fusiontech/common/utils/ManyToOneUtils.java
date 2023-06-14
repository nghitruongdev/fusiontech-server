package com.vnco.fusiontech.common.utils;

import com.vnco.fusiontech.common.entity.ManyToOneRelation;
import lombok.NonNull;

import java.util.Collection;

public interface ManyToOneUtils {
    
    static <Item extends ManyToOneRelation<Entity>, Entity> void
    addItem(Entity entity, Collection<Item> collection, Item item) {
        item.set(entity);
        collection.add(item);
    }
    
    static <Item extends ManyToOneRelation<Entity>, Entity> void
    removeItem(Entity entity, Collection<Item> collection, Item item) {
        item.set(null);
        collection.remove(item);
    }
    
    static <Item extends ManyToOneRelation<Entity>, K extends Collection<Item>, Entity> K
    replace(Entity entity, K oldCollection, @NonNull K newCollection) {
        if (oldCollection != null)
            oldCollection.forEach(item -> item.set(null));
        
        newCollection.forEach(item -> item.set(entity));
        
        return newCollection;
    }
    
}
