package com.turboic.cloud.repository;
import com.turboic.cloud.domain.UserInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author liebe
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoModel, String> {
}