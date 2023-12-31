package com.project.bookforeast.code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookforeast.code.dto.CodeDTO;
import com.project.bookforeast.code.entity.Code;
import com.project.bookforeast.code.repository.CodeRepository;

@Service
public class CodeServiceImpl implements CodeService {
	
	private final CodeRepository codeRepository;

	@Autowired
	public CodeServiceImpl(CodeRepository codeRepository) {
		this.codeRepository = codeRepository;
	}
	
	
	public CodeDTO findAllByCodename(String codename) {
		Code code = codeRepository.findAllByCodename(codename);
		
		if(code == null) {
			return null;
		}
		
		return code.toDTO();
	}


	@Override
	public CodeDTO findByCodename(String codename) {
		Code code = codeRepository.findByCodename(codename);
		
		if(code == null) {
			return null;
		}
		
		return code.toDTO();
	}
	
	
	public void save(CodeDTO codeDTO) {
		codeRepository.save(codeDTO.toEntity());
	}
}
