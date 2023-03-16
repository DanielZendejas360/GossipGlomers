package org.daniel.chapter2;

import org.json.JSONObject;

import java.util.Scanner;
import java.util.UUID;

public class UniqueIdGenerator {

    String id;
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (true) {
            JSONObject message = new JSONObject(input);

            JSONObject response = null;
            switch (message.getJSONObject("body").getString("type")) {
                case "init":
                    response = handleInit(message);
                    break;
                case "generate":
                    response = handleGenerate(message);
                    break;
            }

            System.out.println(response);

            input = scanner.nextLine();
        }
    }

    private JSONObject handleInit(JSONObject message) {
        JSONObject body = message.getJSONObject("body");
        id = body.getString("node_id");

        JSONObject newMessage = new JSONObject();
        newMessage.put("src", id);
        newMessage.put("dest", message.get("src"));

        JSONObject newBody = new JSONObject();
        newBody.put("type", "init_ok");
        newBody.put("in_reply_to", (int) body.get("msg_id"));
        newMessage.put("body", newBody);

        return newMessage;
    }

    private JSONObject handleGenerate(JSONObject message) {
        flipSrcAndDst(message);
        addInReplyTo(message);

        JSONObject body = message.getJSONObject("body");

        body.put("type", "generate_ok");
        body.put("id", UUID.randomUUID().toString());

        message.put("body", body);

        return message;
    }

    private void flipSrcAndDst(JSONObject jsonObject) {
        String src = jsonObject.getString("src");

        jsonObject.put("src", id);
        jsonObject.put("dest", src);
    }

    private void addInReplyTo(JSONObject jsonObject) {
        JSONObject body = jsonObject.getJSONObject("body");
        body.put("in_reply_to", (int) body.get("msg_id"));
    }
}
