package com.project.bookforeast.book.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookforeast.book.dto.BookDTO;
import com.project.bookforeast.book.dto.BookInfosDTO;
import com.project.bookforeast.book.dto.DetailBookInfoDTO;
import com.project.bookforeast.book.entity.Book;
import com.project.bookforeast.book.error.BookErrorResult;
import com.project.bookforeast.book.error.BookException;
import com.project.bookforeast.book.repository.BookRepository;
import com.project.bookforeast.common.domain.constant.Content;
import com.project.bookforeast.common.security.service.SecurityService;
import com.project.bookforeast.file.entity.File;
import com.project.bookforeast.file.service.FileService;
import com.project.bookforeast.user.entity.User;
import com.project.bookforeast.user.service.UserService;


@Service
public class BookServiceImpl implements BookService {
	private final BookApiService bookApiService;
	private final BookRepository bookRepository;
	private final SecurityService securityService;
	private final UserService userService;
	private final FileService fileService;
	
	@Autowired
	public BookServiceImpl(BookApiService bookApiService, 
						   BookRepository bookRepository, 
						   SecurityService securityService, 
						   UserService userService, 
						   FileService fileService) {
		this.bookApiService = bookApiService;
		this.bookRepository = bookRepository;
		this.securityService = securityService;
		this.userService = userService;
		this.fileService = fileService;
	}

	@Override
	public BookInfosDTO getBookInfo(int itemSize, String cursor, String searchValue) {
		return null;
	}
	
		

	@Override
	public BookInfosDTO getBookBestSellerInfos(int itemSize, String cursor) {		
		return bookApiService.getBestSellerList(itemSize, cursor);
	}
	

	public DetailBookInfoDTO getDetailBookInfo(String id) {
		DetailBookInfoDTO detailBookInfoDTO = bookApiService.getDetailBookInfo(id);
		if(detailBookInfoDTO == null) {
			detailBookInfoDTO = findByIsbn(id);
		}

		return detailBookInfoDTO;

	}


	private DetailBookInfoDTO findByIsbn(String isbn) {
		Book book = bookRepository.findByIsbn(isbn);
		return book.toDetailBookInfoDTO();
	}


	@Override
	public void insBookInfo(String accessToken, BookDTO bookDTO, MultipartFile file) {
		User findUser = userService.findUserInSecurityContext();

		Book book = bookDTO.toEntity();
		book.setRegistUser(findUser);
		
		if(file != null) {
			File savedFile = fileService.fileUpload(file, null, Content.BOOK.getContentName());
			book.setThumbnailFileGroup(savedFile.getFileGroup());
		}

		bookRepository.save(book);
	}


	@Override
	public void updBookInfo(String accessToken, BookDTO bookDTO, MultipartFile file) {
		User findUser = userService.findUserInSecurityContext();

		Book book = bookRepository.findByIsbnAndRegistUser(bookDTO.getIsbn(), findUser);
		if(book == null) {
			throw new BookException(BookErrorResult.NOT_REGISTED_BOOK);
		} else {
			setUpdBookInfo(bookDTO, book, file);
		}
		
		bookRepository.save(book);
	}
	
	
	private void setUpdBookInfo(BookDTO updBookDTO, Book book, MultipartFile file) {
		if(updBookDTO.getIsbn() != null || updBookDTO.getIsbn().length() > 0) {
			book.setIsbn(updBookDTO.getIsbn());
		}
		
		if(updBookDTO.getTitle() != null || updBookDTO.getTitle().length() > 0) {
			book.setTitle(updBookDTO.getTitle());
		}
		
		if(updBookDTO.getPublisher() != null || updBookDTO.getPublisher().length() > 0) {
			book.setPublisher(updBookDTO.getPublisher());
		}
		
		if(updBookDTO.getWriter() != null || updBookDTO.getWriter().length() > 0) {
			book.setWriter(updBookDTO.getWriter());
		}

		if(updBookDTO.getDescription() != null || updBookDTO.getDescription().length() > 0) {
			book.setDescription(updBookDTO.getDescription());
		}
		
		if(updBookDTO.getPrice() != 0) {
			book.setPrice(updBookDTO.getPrice());
		}
		
		
		if(file != null) {
			File updatedFile = fileService.fileUpdate(file, book.getThumbnailFileGroup(), Content.BOOK.getContentName());
			book.setThumbnailFileGroup(updatedFile.getFileGroup());
		}
	}
	
	

	
//	public BookInfosDTO getRecommandBookInfos(int itemSize, String cursor) {
//		// 좋아하는 장르, 자주읽는 장르, 생년월일 별 추천 도서 검색
//		
//		// 추천도서가 없는 경우나 개수가 부족한 경우 알라딘에서 편집자 추천 리스트 가져오기
//		
//		
//	}
}
