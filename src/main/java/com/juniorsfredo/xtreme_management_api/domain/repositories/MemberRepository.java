package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
