package com.vssoft.vspace.service.impl;

import com.vssoft.vspace.domain.Product;
import com.vssoft.vspace.repository.ProductRepository;
import com.vssoft.vspace.repository.search.ProductSearchRepository;
import com.vssoft.vspace.service.ProductService;
import com.vssoft.vspace.service.dto.ProductDTO;
import com.vssoft.vspace.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductSearchRepository productSearchRepository;


    @Override
    public ProductDTO save(final ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        var product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        productSearchRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO update(final ProductDTO productDTO) {
        log.debug("Request to update Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        productSearchRepository.index(product);
        return productMapper.toDto(product);
    }

    @Override
    public Optional<ProductDTO> partialUpdate(final ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);
        return productRepository
                .findById(productDTO.getId())
                .map(existingProduct -> {
                    productMapper.partialUpdate(existingProduct, productDTO);
                    return existingProduct;
                })
                .map(productRepository::save)
                .map(savedProduct -> {
                    productSearchRepository.index(savedProduct);
                    return savedProduct;
                })
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(final Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(final String id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    public void delete(final String id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
        productSearchRepository.deleteFromIndexById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> search(final String query, final Pageable pageable) {
        log.debug("Request to search for a page of Products for query {}", query);
        return productSearchRepository.search(query, pageable).map(productMapper::toDto);
    }
}
