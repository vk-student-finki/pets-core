package dev.aucta.handgrenades.repositories.specifications;

import dev.aucta.handgrenades.models.Grenade;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GrenadeSpecification implements Specification<Grenade> {

    private List<SearchCriteria> list;

    public GrenadeSpecification(){
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria){
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Grenade> root, CriteriaQuery<?> query, CriteriaBuilder builder){
        return new SpecificationHelper<Grenade>().resolvePredicate(list, root, query, builder);
    }
}
