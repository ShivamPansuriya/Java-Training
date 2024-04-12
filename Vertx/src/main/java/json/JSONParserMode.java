package org.vertx.core.json;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;

public class JSONParserMode
{
    public static void main(String[] args)
    {
        Vertx vert = Vertx.vertx();

        JsonParser parser = JsonParser.newParser();

        //         parser.objectEventMode() -> Default Behaviour
        //        parser.objectValueMode(); -> Considers the whole JSONObject as one Value

        parser.handler(event -> {
            switch(event.type())
            {
                case START_ARRAY:
                    System.out.println("Start Array");
                    break;
                case END_ARRAY:
                    System.out.println("End Array");
                    break;
                case VALUE:
                    String field = event.fieldName();
                    if(field != null){
                        System.out.println("Field Name : " + field + " value : " + event.value());
                        break;
                    }else{
                        if(event.isString()){
                            System.out.println("String Value : " + event.value());
                        }else{
                            System.out.println("Other Value : " + event.value());
                        }
                        break;
                    }

                    // Handle each object as a value
                    // System.out.println("Object Value: " + event.value());
                    // break;
            }
        });

        parser.handle(Buffer.buffer("[{\"firstName\":\"Bob\",\"lastName\":\"Morane\"},"));
        parser.handle(Buffer.buffer("{\"firstName\":\"Luke\",\"lastName\":\"Lucky\"}]"));
        JsonObject jsonObject = new JsonObject().put("firstName", "Bob").put("lastName", "Morane").put("age", 30);
        parser.handle(jsonObject.toBuffer());

        parser.end();

        vert.close();
    }

}
