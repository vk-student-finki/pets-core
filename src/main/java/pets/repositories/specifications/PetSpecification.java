package pets.repositories.specifications;

import pets.models.Pet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PetSpecification implements Specification<Pet> {

    private List<SearchCriteria> list;

    public PetSpecification(){
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria){
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder builder){
        return new SpecificationHelper<Pet>().resolvePredicate(list, root, query, builder);
    }




}
