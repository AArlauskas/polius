package application.business;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.views.ViewsOpenResponse;
import com.github.seratch.jslack.api.model.block.InputBlock;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.composition.OptionObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.PlainTextInputElement;
import com.github.seratch.jslack.api.model.block.element.StaticSelectElement;
import com.github.seratch.jslack.api.model.view.View;
import com.github.seratch.jslack.api.model.view.ViewClose;
import com.github.seratch.jslack.api.model.view.ViewSubmit;
import com.github.seratch.jslack.api.model.view.ViewTitle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SlackManager {

    private Slack slack;
    private String token;

    public SlackManager(){
        slack = Slack.getInstance();
        token = "xoxp-788630701380-792156960199-814638357476-9067376b0ac0c0dc57ae61448abe706d";
    }

    public void sendInitialModalResponse(String triggerId){
        // Question area
        LayoutBlock questionBlock = InputBlock.builder()
                .label(PlainTextObject.builder().text("Question").build())
                .element(PlainTextInputElement.builder()
                        .actionId("question_action")
                        .multiline(false)
                        .placeholder(PlainTextObject.builder().text("Your question goes here").build())
                        .build())
                .blockId("question_input")
                .build();

        //Questions count select
        LayoutBlock questionsCountInputBlock = InputBlock.builder()
                .label(PlainTextObject.builder().text("How many options do you want to specify?").build())
                .element(StaticSelectElement.builder()
                        .actionId("questions_count_select")
                        .options(Arrays.asList(
                                OptionObject.builder().text(PlainTextObject.builder().text("2").build()).value("2").build(),
                                OptionObject.builder().text(PlainTextObject.builder().text("3").build()).value("3").build(),
                                OptionObject.builder().text(PlainTextObject.builder().text("4").build()).value("4").build(),
                                OptionObject.builder().text(PlainTextObject.builder().text("5").build()).value("5").build()))
                        .initialOption(OptionObject.builder().text(PlainTextObject.builder().text("3").build()).value("3").build())
                        .build())
                .blockId("questions_count_select")
                .build();

        List<LayoutBlock> blocks = Arrays.asList(questionBlock, questionsCountInputBlock);

        View view = View.builder()
                .type("modal")
                .callbackId("create_poll_callback")
                .title(ViewTitle.builder().type("plain_text").text("Create a poll").build())
                .submit(ViewSubmit.builder().type("plain_text").text("Select question options").build())
                .close(ViewClose.builder().type("plain_text").text("Close").build())
                .notifyOnClose(false)
                .blocks(blocks)
                .build();

        try {
            // Call to Slack API
            ViewsOpenResponse apiResponse = slack.methods(token).viewsOpen(req -> req
                    .view(view)
                    .triggerId(triggerId));

            System.out.println(apiResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SlackApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCreteQuestionsResponse(String triggerId)
    {
        LayoutBlock questionBlock1 = InputBlock.builder()
                .label(PlainTextObject.builder().text("Question 1").build())
                .element(PlainTextInputElement.builder()
                        .actionId("question_action1")
                        .multiline(false)
                        .placeholder(PlainTextObject.builder().text("Question nr 1").build())
                        .build())
                .blockId("question_input1")
                .build();

        LayoutBlock questionBlock2 = InputBlock.builder()
                .label(PlainTextObject.builder().text("Question 2").build())
                .element(PlainTextInputElement.builder()
                        .actionId("question_action1")
                        .multiline(false)
                        .placeholder(PlainTextObject.builder().text("Question nr 2").build())
                        .build())
                .blockId("question_input2")
                .build();

        LayoutBlock questionBlock3 = InputBlock.builder()
                .label(PlainTextObject.builder().text("Question 3").build())
                .element(PlainTextInputElement.builder()
                        .actionId("question_action3")
                        .multiline(false)
                        .placeholder(PlainTextObject.builder().text("Question nr 3").build())
                        .build())
                .blockId("question_input3")
                .build();

        List<LayoutBlock> blocks = Arrays.asList(questionBlock1,questionBlock2,questionBlock3);

        View view = View.builder()
                .type("modal")
                .callbackId("create_poll_callback")
                .title(ViewTitle.builder().type("plain_text").text("Enter the questions").build())
                .submit(ViewSubmit.builder().type("plain_text").text("Submit").build())
                .close(ViewClose.builder().type("plain_text").text("Close").build())
                .notifyOnClose(false)
                .blocks(blocks)
                .build();

        try {
            ViewsOpenResponse apiResponse = slack.methods(token).viewsOpen(req -> req
                    .view(view)
                    .triggerId(triggerId));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SlackApiException e) {
            e.printStackTrace();
        }
    }
}
