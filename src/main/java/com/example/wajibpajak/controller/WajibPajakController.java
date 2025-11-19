package com.example.wajibpajak.controller;

import com.example.wajibpajak.dto.ResultResponse;
import com.example.wajibpajak.dto.WajibPajakDTO;
import com.example.wajibpajak.model.WajibPajakModel;
import com.example.wajibpajak.service.WajibPajakService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wajib-pajak")
@RequiredArgsConstructor
public class WajibPajakController {

    // region initial
    private final WajibPajakService wajibPajakService;

    // region get all
    @GetMapping
    public ResponseEntity<Page<WajibPajakModel>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(wajibPajakService.getAll(page, size));
    }

    // region get by id
    @GetMapping("/{id}")
    public ResponseEntity<WajibPajakModel> getById(@PathVariable Long id) {
        return ResponseEntity.ok(wajibPajakService.getById(id));
    }

    // region insert
    @PostMapping
    public ResponseEntity<ResultResponse<WajibPajakModel>> create(@RequestBody WajibPajakDTO dto) {
        ResultResponse<WajibPajakModel> response = wajibPajakService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // region update
    @PutMapping("/{id}")
    public ResponseEntity<ResultResponse<WajibPajakModel>> update(
            @PathVariable Long id,
            @RequestBody WajibPajakDTO dto
    ) {
        ResultResponse<WajibPajakModel> response = wajibPajakService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // region delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse<Void>> delete(@PathVariable @NonNull Long id) {
        wajibPajakService.delete(id);
        return ResponseEntity.ok(
                new ResultResponse<>("Data berhasil dihapus", null)
        );
    }
}