package com.spring.demo.service;

import com.spring.demo.dto.request.BookRequest;
import com.spring.demo.dto.response.BookResponse;
import com.spring.demo.dto.response.CategorySimple;
import com.spring.demo.entity.*;
import com.spring.demo.exception.BusinessException;
import com.spring.demo.exception.ResourceNotFoundException;
import com.spring.demo.repository.*;
import com.spring.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
	
	private BookRepository bookRepository;
	private CategoryRepository categoryRepository;
	private BookCategoryRepository bookCategoryRepository;
	
	
	@Override
	public BookResponse create(BookRequest request) {
		// TODO Auto-generated method stub
		if(bookRepository.existsByIsbn(request.getIsbn())) {
			throw new BusinessException("ISBN đã tồn tại");
		}
		
		Book book = Book.builder()
				.isbn(request.getIsbn())
				.title(request.getTitle())
				.author(request.getAuthor())
				.publisher(request.getPublisher())
				.publicationDate(request.getPublicationDate())
				.summary(request.getSummary())
				.stockQuantity(request.getStockQuantity())
				.build();
		book = bookRepository.save(book);
		attachCategories(book, request.getCategoryIds());
		return toResponse(book);
	}

	@Override
	public BookResponse update(Long id, BookRequest request) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("Book not found with id = " + id)
		);
		
		book.setTitle(request.getTitle());
		book.setAuthor(request.getAuthor());
		book.setPublisher(request.getPublisher());
		book.setPublicationDate(request.getPublicationDate());
		book.setSummary(request.getSummary());
		book.setStockQuantity(request.getStockQuantity());
		bookRepository.save(book);
		
		bookCategoryRepository.deleteByBookId(id);
		attachCategories(book, request.getCategoryIds());
		return toResponse(book);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book not found with id = " + id)
			);
		
		bookRepository.delete(book);		
	}

	@Override
	public BookResponse getById(Long id) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book not found with id = " + id)
			);
		return toResponse(book);
	}

	@Override
	public Page<BookResponse> search(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return bookRepository.search(keyword, pageable).map(this::toResponse);
	}

	@Override
	public Page<BookResponse> findByCategoryId(Long categoryId, Pageable pageable) {
		// TODO Auto-generated method stub
		return bookRepository.findByCategoryId(categoryId, pageable).map(this::toResponse);
	}

	@Override
	public Book getEntityById(Long id) {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("Book not found with id = " + id)
		);
	}
	
	
	
	private void attachCategories(Book book, Set<Long> categoryIds) {
		if(categoryIds == null || categoryIds.isEmpty()) return;
		for(Long catId : categoryIds) {
			Category category = categoryRepository.findById(catId)
					.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục id=" + catId));
			BookCategoryId bookCategoryId = new BookCategoryId(book.getId(), category.getId());
			BookCategory bookCategory = BookCategory.builder()
					.id(bookCategoryId)
					.book(book)
					.category(category)
					.build();
			
			bookCategoryRepository.save(bookCategory);
		}
	}
	
	private BookResponse toResponse(Book book) {
		List<CategorySimple> categorySimples  = bookCategoryRepository.findByBookId(book.getId())
				.stream()
				.map(bc -> new CategorySimple(bc.getCategory().getId(), bc.getCategory().getName()))
				.collect(Collectors.toList());
		
		return BookResponse.builder()
				.id(book.getId())
				.isbn(book.getIsbn())
				.title(book.getTitle())
				.author(book.getAuthor())
				.publisher(book.getPublisher())
				.publicationDate(book.getPublicationDate())
				.summary(book.getSummary())
				.stockQuantity(book.getStockQuantity())
				.categories(categorySimples)
				.build();
	}


//    private final BookRepository bookRepository;
//    private final CategoryRepository categoryRepository;
//    private final BookCategoryRepository bookCategoryRepository;
//
//    @Override
//    public BookResponse create(BookRequest request) {
//        if (bookRepository.existsByIsbn(request.getIsbn())) {
//            throw new BusinessException("ISBN đã tồn tại");
//        }
//
//        Book book = Book.builder()
//                .isbn(request.getIsbn())
//                .title(request.getTitle())
//                .author(request.getAuthor())
//                .publisher(request.getPublisher())
//                .publicationDate(request.getPublicationDate())
//                .summary(request.getSummary())
//                .stockQuantity(request.getStockQuantity())
//                .build();
//        book = bookRepository.save(book);
//
//        attachCategories(book, request.getCategoryIds());
//        return toResponse(book);
//    }
//
//    @Override
//    public BookResponse update(Long id, BookRequest request) {
//        Book book = getEntityById(id);
//        book.setTitle(request.getTitle());
//        book.setAuthor(request.getAuthor());
//        book.setPublisher(request.getPublisher());
//        book.setPublicationDate(request.getPublicationDate());
//        book.setSummary(request.getSummary());
//        book.setStockQuantity(request.getStockQuantity());
//        book = bookRepository.save(book);
//
//        bookCategoryRepository.deleteByBookId(book.getId());
//        attachCategories(book, request.getCategoryIds());
//
//        return toResponse(book);
//    }
//
//    @Override
//    public void delete(Long id) {
//        Book book = getEntityById(id);
//        bookRepository.delete(book);
//    }
//
//    @Override
//    public BookResponse getById(Long id) {
//        return toResponse(getEntityById(id));
//    }
//
//    @Override
//    public Page<BookResponse> search(String keyword, Pageable pageable) {
//        return bookRepository.search(keyword, pageable).map(this::toResponse);
//    }
//
//    @Override
//    public Book getEntityById(Long id) {
//        return bookRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sách id=" + id));
//    }
//
//    private void attachCategories(Book book, Set<Long> categoryIds) {
//        if (categoryIds == null || categoryIds.isEmpty()) return;
//        for (Long catId : categoryIds) {
//            Category category = categoryRepository.findById(catId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục id=" + catId));
//            BookCategoryId bcId = new BookCategoryId(book.getId(), category.getId());
//            BookCategory bookCategory = BookCategory.builder()
//                    .id(bcId).book(book).category(category).build();
//            bookCategoryRepository.save(bookCategory);
//        }
//    }
//
//    private BookResponse toResponse(Book book) {
//        List<CategorySimple> categories = bookCategoryRepository.findByBookId(book.getId())
//                .stream()
//                .map(bc -> new CategorySimple(bc.getCategory().getId(), bc.getCategory().getName()))
//                .collect(Collectors.toList());
//
//        return BookResponse.builder()
//                .id(book.getId())
//                .isbn(book.getIsbn())
//                .title(book.getTitle())
//                .author(book.getAuthor())
//                .publisher(book.getPublisher())
//                .publicationDate(book.getPublicationDate())
//                .summary(book.getSummary())
//                .stockQuantity(book.getStockQuantity())
//                .categories(categories)
//                .build();
//    }
//
//    @Override
//    public Page<BookResponse> findByCategoryId(Long categoryId, Pageable pageable) {
//        return bookRepository.findByCategoryId(categoryId, pageable).map(this::toResponse);
//    }
	
	
	
	
	
	
	
}