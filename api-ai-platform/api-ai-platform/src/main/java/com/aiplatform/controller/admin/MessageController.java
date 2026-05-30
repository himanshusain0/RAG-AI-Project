package com.aiplatform.controller.admin;

import com.aiplatform.dto.request.CreateMessageRequest;
import com.aiplatform.dto.response.ApiResponse;
import com.aiplatform.dto.response.MessageResponse;
import com.aiplatform.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // POST /admin/api/messages
    @PostMapping
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(
            @Valid @RequestBody CreateMessageRequest request) {
        MessageResponse data = messageService.createMessage(request);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Message created successfully", data));
    }

    // GET /admin/api/messages?sessionId=1
    @GetMapping
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getMessages(
            @RequestParam Long sessionId) {
        List<MessageResponse> data = messageService.getMessagesBySession(sessionId);
        return ResponseEntity.ok(ApiResponse.success("Messages fetched successfully", data));
    }

    // DELETE /admin/api/messages/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("Message deleted successfully", null));
    }
}