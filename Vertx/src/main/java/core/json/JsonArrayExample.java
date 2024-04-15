package core.json;

import io.vertx.core.json.JsonArray;

public class JsonArrayExample
{
    public static void main(String[] args)
    {

        JsonArray emptyArray = new JsonArray();
        String jsonString = "[\"foo\",\"bar\"]";
        JsonArray array = new JsonArray(jsonString);

        array.add("baz").add(42).add(true);

        String firstValue = array.getString(0);

        Integer secondValue = array.getInteger(3);

        Boolean thirdValue = array.getBoolean(4);

        System.out.println("JSON Array: " + array.encode());

        System.out.println("First Value: " + firstValue);

        System.out.println("Second Value: " + secondValue);

        System.out.println("Third Value: " + thirdValue);

        String jsonArrayString = array.encode();

        System.out.println("JSON Array String: " + jsonArrayString);
    }
}