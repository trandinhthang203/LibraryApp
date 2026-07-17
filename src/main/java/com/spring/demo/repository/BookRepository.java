package com.spring.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import com.spring.demo.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query("""
			FROM Book b WHERE (
				:keyword is null
				or lower(b.title) like lower(concat('%', cast(:keyword as string), '%'))
				or lower(b.author) like lower(concat('%', cast(:keyword as string), '%'))
			)
			""")
	Page<Book> search(@Param("keyword") String keyword, Pageable pageable);
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//    // Query Method
//    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
//
//    // JPQL - tìm kiếm theo nhiều tiêu chí
//    @Query("""
//    	    select b from Book b
//    	    where (:keyword is null 
//    	           or lower(b.title) like lower(concat('%', cast(:keyword as string), '%'))
//    	           or lower(b.author) like lower(concat('%', cast(:keyword as string), '%')))
//    	    """)
//    Page<Book> search(@Param("keyword") String keyword, Pageable pageable);
//
//    // JPQL - lấy sách theo category
//    @Query("""
//           SELECT bc.book FROM BookCategory bc
//           WHERE bc.category.id = :categoryId
//           """)
//    Page<Book> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
//
//    boolean existsByIsbn(String isbn);
}