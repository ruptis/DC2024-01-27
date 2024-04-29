package by.bsuir.discussionservice.controller;

import by.bsuir.discussionservice.dto.request.MessageRequestTo;
import by.bsuir.discussionservice.dto.response.MessageResponseTo;
import by.bsuir.discussionservice.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponseTo> getAllMessages(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return messageService.getAllMessages(PageRequest.of(page, size));
    }

    @GetMapping("/story/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponseTo> getMessagesByStoryId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return messageService.getMessagesByStoryId(id, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo getMessageById(
            @PathVariable Long id
    ) {
        return messageService.getMessageById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseTo createMessage(
            @Valid @RequestBody MessageRequestTo message,
            HttpServletRequest request) {
        return messageService.createMessage(getRequestWithCountry(message, request));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo updateMessage(
            @Valid @RequestBody MessageRequestTo message,
            HttpServletRequest request) {
        return messageService.updateMessage(getRequestWithCountry(message, request));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseTo updateMessage(
            @PathVariable Long id,
            @Valid @RequestBody MessageRequestTo message,
            HttpServletRequest request
    ) {
        return messageService.updateMessage(id, getRequestWithCountry(message, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(
            @PathVariable Long id
    ) {
        messageService.deleteMessage(id);
    }

    private static MessageRequestTo getRequestWithCountry(MessageRequestTo message, HttpServletRequest request) {
        return message.country() == null || message.country().isEmpty()
                ? message.withCountry(getCountry(request))
                : message;
    }

    private static String getCountry(HttpServletRequest request) {
        String requestCountry = request.getLocale().getDisplayCountry();
        return requestCountry.isEmpty() ? "Unspecified" : requestCountry;
    }
}
