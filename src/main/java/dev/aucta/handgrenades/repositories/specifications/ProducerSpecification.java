package dev.aucta.handgrenades.repositories.specifications;

import dev.aucta.handgrenades.models.Grenade;
import dev.aucta.handgrenades.models.Producer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProducerSpecification implements Specification<Producer> {
    private List<SearchCriteria> list;

    public ProducerSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Producer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return new SpecificationHelper<Producer>().resolvePredicate(list,root,query,builder);
    }
}
