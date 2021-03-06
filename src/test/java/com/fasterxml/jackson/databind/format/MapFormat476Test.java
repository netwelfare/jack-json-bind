package com.fasterxml.jackson.databind.format;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.*;

@SuppressWarnings("serial")
public class MapFormat476Test extends BaseMapTest
{
    @JsonPropertyOrder({ "extra" })
    static class Map476Base extends LinkedHashMap<String,Integer> {
        public int extra = 13;
    }

    @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    static class Map476AsPOJO extends Map476Base { }

    @JsonPropertyOrder({ "a", "b", "c" })
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class Bean476Container
    {
        public Map476AsPOJO a;
        public Map476Base b;
        @JsonFormat(shape=JsonFormat.Shape.OBJECT)
        public Map476Base c;

        public Bean476Container(int forA, int forB, int forC) {
            if (forA != 0) {
                a = new Map476AsPOJO();
                a.put("value", forA);
            }
            if (forB != 0) {
                b = new Map476Base();
                b.put("value", forB);
            }
            if (forC != 0) {
                c = new Map476Base();
                c.put("value", forC);
            }
        }
    }

    static class Bean476Override
    {
        @JsonFormat(shape=JsonFormat.Shape.NATURAL)
        public Map476AsPOJO stuff;

        public Bean476Override(int value) {
            stuff = new Map476AsPOJO();
            stuff.put("value", value);
        }
    }

    /*
    /**********************************************************
    /* Test methods
    /**********************************************************
     */

    final private ObjectMapper MAPPER = objectMapper();
    // for [databind#476]: Maps as POJOs

    public void testAsPOJOViaClass() throws Exception
    {
        String result = MAPPER.writeValueAsString(new Bean476Container(1,2,0));
        assertEquals(aposToQuotes("{'a':{'extra':13,'empty':false},'b':{'value':2}}"),
                result);
    }

    // Can't yet use per-property overrides at all
    /*
    
    public void testAsPOJOViaProperty() throws Exception
    {
        String result = MAPPER.writeValueAsString(new Bean476Container(1,0,3));
        assertEquals(aposToQuotes("{'a':{'extra':13,'empty':false},'c':{'empty':false,'value':3}}"),
                result);
    }

    public void testNaturalViaOverride() throws Exception
    {
        String result = MAPPER.writeValueAsString(new Bean476Override(123));
        assertEquals(aposToQuotes("{'stuff':{'value':123}}"),
                result);
    }
    */
}
