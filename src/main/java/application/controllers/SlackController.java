package application.controllers;

import application.business.SlackManager;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {
    private SlackManager slackManager;

    public SlackController(){
        slackManager = new SlackManager();
    }

    @RequestMapping(value = "/slack/slash", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void onSlashCommandAccepted(@RequestParam("trigger_id") String triggerId) {
        slackManager.sendInitialModalResponse(triggerId);
    }

    @RequestMapping(value = "/slack/interact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void onInteraction(@RequestParam(name = "payload") String payload){
        Gson snakeCase = GsonFactory.createSnakeCase();
        BlockActionPayload pl = snakeCase.fromJson(payload, BlockActionPayload.class);
        System.out.println();
        System.out.println();
        System.out.println(pl);
        System.out.println(pl.getTriggerId());
        slackManager.sendInitialModalResponse(pl.getTriggerId());
    }
}
