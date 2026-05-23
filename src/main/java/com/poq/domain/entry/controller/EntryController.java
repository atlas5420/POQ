package com.poq.domain.entry.controller;

import com.poq.domain.entry.dto.EntryRegisterRequest;
import com.poq.domain.entry.dto.EntryResponse;
import com.poq.domain.entry.entity.Entry;
import com.poq.domain.entry.service.EntryService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entries")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;

    @PostMapping
    public ResponseEntity<ApiResponse<UUID>> createEntry(@Valid @RequestBody EntryRegisterRequest request) {
        UUID entryId = entryService.createEntry(request);
        return ResponseEntity.ok(ApiResponse.success(entryId));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<EntryResponse>>> getPetEntries(@PathVariable("petId") UUID petId) {
        List<Entry> entries = entryService.getEntriesByPetId(petId);
        List<EntryResponse> response = entries.stream()
                .map(EntryResponse::new)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
