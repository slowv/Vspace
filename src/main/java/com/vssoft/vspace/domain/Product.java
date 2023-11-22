package com.vssoft.vspace.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Entity
@Table(name = "products")
@Document(indexName = "products")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends AbstractAuditingEntity<String> {

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    @Field(type = FieldType.Text)
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "content")
    @Field(type = FieldType.Text)
    private String content;
}
