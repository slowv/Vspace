package com.vssoft.vspace.service;

import com.vssoft.vspace.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDTO save(ProductDTO productDTO);

    /**
     * Updates a product.
     *
     * @param productDTO the entity to update.
     * @return the persisted entity.
     */
    ProductDTO update(ProductDTO productDTO);

    /**
     * Partially updates a product.
     *
     * @param productDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductDTO> partialUpdate(ProductDTO productDTO);

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductDTO> findAll(Pageable pageable);

    /**
     * Get the "id" product.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDTO> findOne(String id);

    /**
     * Delete the "id" product.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the product corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductDTO> search(String query, Pageable pageable);
}
