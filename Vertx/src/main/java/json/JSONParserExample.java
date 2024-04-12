package org.vertx.core.json;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.JsonParser;

public class JSONParserExample
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        JsonParser parser = JsonParser.newParser();

        parser.handler(event -> {
            switch(event.type())
            {
                case START_OBJECT:
                    System.out.println("Start Object");
                    break;
                case END_OBJECT:
                    System.out.println("End Object");
                    break;
                case START_ARRAY:
                    System.out.println("Start Array");
                    break;
                case END_ARRAY:
                    System.out.println("End Array");
                    break;
                case VALUE:
                    // Handle a value
                    String field = event.fieldName();
                    if(field != null)
                    {
                        System.out.println("Field: " + field + ", Value: " + event.value());
                    }
                    else
                    {
                        if(event.isString())
                        {
                            System.out.println("String Value: " + event.value());
                        }
                        else
                        {
                            System.out.println("Other Value: " + event.value());
                        }
                    }
                    break;
            }
        });


        parser.handle(Buffer.buffer("[{\"firstName\":\"Bob\","));
        parser.handle(Buffer.buffer("\"lastName\":\"Morane\"},"));
        parser.handle(Buffer.buffer("{\"firstName\":\"Luke\",\"lastName\":\"Lucky\"}"));
        parser.handle(Buffer.buffer("]"));

        parser.end();

        vertx.close();
    }
}
