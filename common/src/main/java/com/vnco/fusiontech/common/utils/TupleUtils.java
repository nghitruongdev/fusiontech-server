package com.vnco.fusiontech.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import java.util.ArrayList;
import java.util.List;

public class TupleUtils {
    public static List<ObjectNode> convertToJsonNode(
            List<Tuple> list) {

        List<ObjectNode> json = _toJson(list);

        return json;
    }

    private static List<ObjectNode> _toJson(List<Tuple> results) {

        List<ObjectNode> json = new ArrayList<ObjectNode>();

        ObjectMapper mapper = new ObjectMapper();

        for (Tuple t : results) {
            List<TupleElement<?>> cols = t.getElements();

            ObjectNode one = mapper.createObjectNode();

            for (TupleElement col : cols) {
                if(col == null) continue;;
                one.put(col.getAlias(),String.valueOf(t.get(col.getAlias())));
            }

            json.add(one);
        }

        return json;
    }
}
