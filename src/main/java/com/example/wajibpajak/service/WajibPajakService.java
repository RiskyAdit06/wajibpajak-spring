package com.example.wajibpajak.service;

import com.example.wajibpajak.dto.WajibPajakDTO;
import com.example.wajibpajak.dto.ResultResponse;
import com.example.wajibpajak.exception.DuplicateException;
import com.example.wajibpajak.exception.ResourceNotFoundException;
import com.example.wajibpajak.exception.ValidationException;
import com.example.wajibpajak.model.WajibPajakModel;
import com.example.wajibpajak.repository.WajibPajakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WajibPajakService {

    // region initial
    private final WajibPajakRepository wajibPajakRepository;

    // region get all
    public Page<WajibPajakModel> getAll(int page, int size) {
        return wajibPajakRepository.findAll(PageRequest.of(page, size));
    }
    
    // region get by id
    public WajibPajakModel getById(Long id) {
        return wajibPajakRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wajib Pajak tidak ditemukan"));
    }

    // region create
    public ResultResponse<WajibPajakModel> create(WajibPajakDTO dto) {
        validateDTO(dto, true);

        wajibPajakRepository.findByNpwpd(dto.getNpwpd()).ifPresent(wp -> {
            throw new DuplicateException("NPWPD sudah terdaftar");
        });

        WajibPajakModel wp = new WajibPajakModel();
        wp.setNama(dto.getNama());
        wp.setNpwpd(dto.getNpwpd());
        wp.setAlamat(dto.getAlamat());
        wp.setJenisUsaha(dto.getJenisUsaha());

        WajibPajakModel saved = wajibPajakRepository.save(wp);

        return new ResultResponse<>("Data Wajib Pajak berhasil ditambahkan", saved);
    }

    // region update
    public ResultResponse<WajibPajakModel> update(Long id, WajibPajakDTO dto) {
        WajibPajakModel wp = getById(id);

        validateDTO(dto, false);

        if (dto.getNpwpd() != null && !dto.getNpwpd().equals(wp.getNpwpd())) {
            wajibPajakRepository.findByNpwpd(dto.getNpwpd()).ifPresent(existing -> {
                throw new ValidationException("NPWPD sudah terdaftar");
            });
            wp.setNpwpd(dto.getNpwpd());
        }

        if (dto.getNama() != null) wp.setNama(dto.getNama());
        if (dto.getAlamat() != null) wp.setAlamat(dto.getAlamat());
        if (dto.getJenisUsaha() != null) wp.setJenisUsaha(dto.getJenisUsaha());

        WajibPajakModel updated = wajibPajakRepository.save(wp);

        return new ResultResponse<>("Data Wajib Pajak berhasil diperbarui", updated);
    }
    
    // region delete
    public void delete(Long id) {
        WajibPajakModel wp = getById(id);
        wajibPajakRepository.delete(wp);
    }

    // region helper
    private void validateDTO(WajibPajakDTO dto, boolean isCreate) {
        if (isCreate || dto.getNama() != null) {
            if (dto.getNama() == null || dto.getNama().isBlank()) {
                throw new ValidationException("Nama wajib diisi");
            }
        }

        if (isCreate || dto.getNpwpd() != null) {
            if (dto.getNpwpd() == null || dto.getNpwpd().isBlank()) {
                throw new ValidationException("NPWPD wajib diisi");
            }
            if (dto.getNpwpd().length() < 10 || dto.getNpwpd().length() > 20) {
                throw new ValidationException("NPWPD harus 10-20 karakter");
            }
        }

        if (isCreate || dto.getJenisUsaha() != null) {
            if (dto.getJenisUsaha() == null || dto.getJenisUsaha().isBlank()) {
                throw new ValidationException("Jenis usaha wajib diisi");
            }
        }
    }
}