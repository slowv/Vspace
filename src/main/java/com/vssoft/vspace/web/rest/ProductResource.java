package com.vssoft.vspace.web.rest;

import com.vssoft.vspace.common.utils.HeaderUtil;
import com.vssoft.vspace.common.utils.PaginationUtil;
import com.vssoft.vspace.common.utils.ResponseUtil;
import com.vssoft.vspace.repository.ProductRepository;
import com.vssoft.vspace.service.ProductService;
import com.vssoft.vspace.service.dto.ProductDTO;
import com.vssoft.vspace.web.rest.errors.BadRequestAlertException;
import com.vssoft.vspace.web.rest.errors.ElasticsearchExceptionMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductResource {
    private final ProductRepository productRepository;
    private static final String ENTITY_NAME = "product";

    @Value("${vspace.clientApp.name}")
    private String applicationName;

    private final ProductService productService;

    /**
     * {@code POST  /products} : Create a new product.
     *
     * @param productDTO the productDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDTO, or with status {@code 400 (Bad Request)} if the article has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to save Product : {}", productDTO);

        if (productDTO.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
        }

        final var result = productService.save(productDTO);
        return ResponseEntity
                .created(URI.create("/api/products/%s".formatted(result.getId())))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /products/:id} : Updates an existing article.
     *
     * @param id the id of the productDTO to save.
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable(value = "id", required = false) final String id,
            @Valid @RequestBody ProductDTO productDTO
    ) {
        log.debug("REST request to update Product : {}, {}", id, productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        final var result = productService.update(productDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productDTO.getId()))
                .body(result);
    }

    /**
     * {@code PATCH  /products/:id} : Partial updates given fields of an existing article, field will ignore if it is null
     *
     * @param id the id of the productDTO to save.
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleDTO couldn't be updated.
     */
    @PatchMapping(value = "/articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductDTO> partialUpdateProduct(
            @PathVariable(value = "id", required = false) final String id,
            @NotNull @RequestBody ProductDTO productDTO
    ) {
        log.debug("REST request to partial update Product partially : {}, {}", id, productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        final var result = productService.partialUpdate(productDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productDTO.getId())
        );
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of products");
        final var page = productService.findAll(pageable);
        final var headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /products/:id} : get the "id" product.
     *
     * @param id the id of the productDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getArticle(@PathVariable String id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    /**
     * {@code SEARCH  /_search/products?query=:query} : search for the product corresponding
     * to the query.
     *
     * @param query the query of the product search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/products")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam String query,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of Products for query {}", query);
        try {
            Page<ProductDTO> page = productService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
