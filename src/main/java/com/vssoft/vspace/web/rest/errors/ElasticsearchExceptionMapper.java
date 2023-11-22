package com.vssoft.vspace.web.rest.errors;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.ErrorCause;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;

import java.util.List;

public class ElasticsearchExceptionMapper {

    private ElasticsearchExceptionMapper() {}

    public static RuntimeException mapException(RuntimeException originalException) {
        RuntimeException e = originalException;
        if (e.getCause() instanceof UncategorizedElasticsearchException uncategorizedElasticsearchException) {
            e = uncategorizedElasticsearchException;
        }
        if (e.getCause() instanceof ElasticsearchException esException) {
            List<ErrorCause> rootCause = esException.response().error().rootCause();
            if (!rootCause.isEmpty()) {
                String reason = rootCause.get(0).reason();
                if (reason != null && reason.startsWith("Failed to parse query [")) {
                    return new QuerySyntaxException();
                }
            }
        }

        return originalException;
    }
}
