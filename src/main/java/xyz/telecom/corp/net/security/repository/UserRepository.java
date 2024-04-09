package xyz.telecom.corp.net.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.telecom.corp.net.security.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Boolean existsByCode(Integer code);
    //@Query(name = "update",value="UPDATE public.users SET address=:ads, code=cod, email=em, login=lin, name=n, password=pwd, phone_number=phnum, age=age WHERE id:ids",nativeQuery=true)
    //; UPDATE user_role SET rol_id = :num	where usuario_id = :ids;
    //Users updateUser(@Param("ids")Integer id,@Param("ads")Integer cod,@Param("em")String email,@Param("lin")String login,@Param("n")String name,@Param("pwd")String password,@Param("phnum")String phoneNumber,@Param("age")Short age);
    
    //@Query(name = "update",value="UPDATE users SET address= :ads, code= :cod, email= :em, login= :lin, name= :n, password= :pwd, phone_number= :phnum, age= :age WHERE id= :ids",nativeQuery=false)
    //Users updateUser(@Param("ids")Integer id,@Param("ads")Integer cod,@Param("em")String email,@Param("lin")String login,@Param("n")String name,@Param("pwd")String password,@Param("phnum")String phoneNumber,@Param("age")Short age);

   }
