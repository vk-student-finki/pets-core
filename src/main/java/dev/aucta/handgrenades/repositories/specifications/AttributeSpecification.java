package dev.aucta.handgrenades.repositories.specifications;

import dev.aucta.handgrenades.models.Attribute;
import dev.aucta.handgrenades.models.Country;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class AttributeSpecification implements Specification<Attribute> {
    private List<SearchCriteria> list;

    public AttributeSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Attribute> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return new SpecificationHelper<Attribute>().resolvePredicate(list,root,query,builder);
    }
}