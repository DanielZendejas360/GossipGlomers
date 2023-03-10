package org.daniel.chapter1;

import java.util.Scanner;

import org.json.JSONObject;

public class Echo {

  String id = "";

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
        case "echo":
          response = handleEcho(message);
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

  private JSONObject handleEcho(JSONObject message) {
    flipSrcAndDst(message);
    changeType(message);
    addInReplyTo(message);

    return message;
  }

  private void flipSrcAndDst(JSONObject jsonObject) {
    String src = jsonObject.getString("src");

    jsonObject.put("src", id);
    jsonObject.put("dest", src);
  }

  private void changeType(JSONObject jsonObject) {
    JSONObject body = jsonObject.getJSONObject("body");
    body.put("type", "echo_ok");
  }

  private void addInReplyTo(JSONObject jsonObject) {
    JSONObject body = jsonObject.getJSONObject("body");
    body.put("in_reply_to", (int) body.get("msg_id"));
  }
}
