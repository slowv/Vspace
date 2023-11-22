package com.vssoft.vspace.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.vssoft.vspace.domain.Product;
import com.vssoft.vspace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, String>, ProductSearchRepositoryInternal {}

interface ProductSearchRepositoryInternal {
    Page<Product> search(String query, Pageable pageable);

    Page<Product> search(Query query);

    @Async
    void index(Product entity);

    @Async
    void deleteFromIndexById(String id);
}

@RequiredArgsConstructor
class ProductSearchRepositoryInternalImpl implements ProductSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ProductRepository repository;

    @Override
    public Page<Product> search(String query, Pageable pageable) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return search(nativeQuery.setPageable(pageable));
    }

    @Override
    public Page<Product> search(Query query) {
        SearchHits<Product> searchHits = elasticsearchTemplate.search(query, Product.class);
        List<Product> hits = searchHits.map(SearchHit::getContent).stream().toList();
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(Product entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndexById(String id) {
        elasticsearchTemplate.delete(id, Product.class);
    }
}
