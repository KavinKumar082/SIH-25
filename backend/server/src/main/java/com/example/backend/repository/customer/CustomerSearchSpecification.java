package com.example.backend.repository.customer;

import com.example.backend.domain.entity.customer.AccountAddress;
import com.example.backend.domain.entity.customer.Customer;
import com.example.backend.domain.entity.customer.CustomerAccount;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSearchSpecification {

    public static Specification<Customer> search(
            String searchTerm,
            String city
    ) {
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            query.distinct(true);

            Predicate predicate = cb.conjunction();

            // ---------- SEARCH TERM ----------
            if (searchTerm != null && !searchTerm.isBlank()) {

                Predicate searchPredicate;

                if (searchTerm.matches("\\d+")) {
                    // numeric customerId
                    searchPredicate = cb.equal(
                            root.get("customerId"),
                            Long.valueOf(searchTerm)
                    );
                } else {
                    String likeTerm = "%" + searchTerm.toLowerCase() + "%";

                    // subquery for city match
                    Subquery<Long> citySubquery = query.subquery(Long.class);
                    Root<AccountAddress> addressRoot = citySubquery.from(AccountAddress.class);
                    Join<AccountAddress, CustomerAccount> accountJoin =
                            addressRoot.join("account");

                    citySubquery.select(
                            accountJoin.get("customer").get("customerId")
                    ).where(
                            cb.like(cb.lower(addressRoot.get("city")), likeTerm)
                    );

                    searchPredicate = cb.or(
                            cb.like(cb.lower(root.get("customerName")), likeTerm),
                            root.get("customerId").in(citySubquery)
                    );
                }

                predicate = cb.and(predicate, searchPredicate);
            }

            // ---------- CITY FILTER ----------
            if (city != null && !city.isBlank()) {

                Subquery<Long> cityFilterSubquery = query.subquery(Long.class);
                Root<AccountAddress> addressRoot = cityFilterSubquery.from(AccountAddress.class);
                Join<AccountAddress, CustomerAccount> accountJoin =
                        addressRoot.join("account");

                cityFilterSubquery.select(
                        accountJoin.get("customer").get("customerId")
                ).where(
                        cb.like(
                                cb.lower(addressRoot.get("city")),
                                "%" + city.toLowerCase() + "%"
                        )
                );

                predicate = cb.and(
                        predicate,
                        root.get("customerId").in(cityFilterSubquery)
                );
            }

            return predicate;
        };
    }
}
